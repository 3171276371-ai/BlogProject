package cxt.cn.config;

import cn.hutool.core.thread.threadlocal.NamedThreadLocal;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cxt.cn.entity.BlogAuditLog;
import cxt.cn.mapper.BlogAuditLogMapper;
import cxt.cn.utils.JwtUtils;
import cxt.cn.utils.StudioUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

@Component
@Aspect
@Slf4j
public class LogAop {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAop.class);

    private static final ThreadLocal<Date> BEGIN_TIME_THREAD_LOCAL = new NamedThreadLocal<>("thread local date");

    private static final ThreadLocal<BlogAuditLog> CURRENT_ACCOUNT = new NamedThreadLocal<>("thread local account");

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtUtils jwtTokenUtil;
    @Autowired
    private BlogAuditLogMapper blogAuditLogMapper;

    @Autowired
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 前置通知
     */
    @Pointcut("execution(public * cxt.cn.controller.*.*(..))")
    public void cut() {
    }

    @Before(value = "execution(public * cxt.cn.controller.*.*(..))")
    public void before(JoinPoint joinPoint) {

        BlogAuditLog cmsOptionLog = new BlogAuditLog();
        LOGGER.info("日志切面前置通知");
        Date beginTime = new Date();
        BEGIN_TIME_THREAD_LOCAL.set(beginTime);
        // 读取请求头的信息再获取用户名
        String authHeader = request.getHeader(this.tokenHeader);
        String username = "";
        if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
            username = jwtTokenUtil.getUserNameFromToken(authHeader.substring(this.tokenHead.length()));
        }
        cmsOptionLog.setUsername(username);
        CURRENT_ACCOUNT.set(cmsOptionLog);
    }


    @After(value = "execution(public * cxt.cn.controller.*.*(..))")
    public void afterAdvice(JoinPoint joinPoint) {
        // 日志类型
        Integer logType = 10;
        // 请求者IP
        String address = StudioUtils.getIpAddr(request);
        // 请求的uri
        String requestUri = request.getRequestURI().toString();
        // 请求的方法类型（post, get, put, delete, ...）
        String reqMethod = request.getMethod();
        // 获取请求的参数
        String paramsStr = "";
        if (RequestMethod.POST.name().equals(reqMethod) ||
                RequestMethod.PUT.name().equals(reqMethod) ||
                RequestMethod.DELETE.name().equals(reqMethod)) {
            Object[] objects = joinPoint.getArgs();
            paramsStr = argsArrayToString(objects);
        } else {
            Map<?, ?> map = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            paramsStr = JSONObject.toJSONString(map);
        }
        // 获取swagger 注解中的参数
        String title = "";
        Signature signature = joinPoint.getSignature();
        Method method = ((MethodSignature) signature).getMethod();
        if (method.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation log = method.getAnnotation(ApiOperation.class);
            title = log.value();
        }
        // 打印JVM信息
        long beginTime = BEGIN_TIME_THREAD_LOCAL.get().getTime();
        long endTime = System.currentTimeMillis();
        BlogAuditLog cmsOptionLog = CURRENT_ACCOUNT.get();
        cmsOptionLog.setUrl(requestUri);
        cmsOptionLog.setAddress(address);
        cmsOptionLog.setLogType(logType);
        cmsOptionLog.setReqMethod(reqMethod);
        cmsOptionLog.setReqParam(paramsStr);
        cmsOptionLog.setTimeout(Long.toString(endTime - beginTime));
        cmsOptionLog.setTitle(title);
    }

    @AfterReturning(value = "execution(public * cxt.cn.controller.*.*(..))", returning = "res")
    public void afterReturn(Object res) {
        BlogAuditLog cmsOptionLog = CURRENT_ACCOUNT.get();
        String jsonString = JSON.toJSONString(res);
        if(jsonString.length() < 8191) {
            cmsOptionLog.setResParam(jsonString);
        }
        cmsOptionLog.setResParam(jsonString);
        threadPoolTaskExecutor.execute(new SaveLogThread(cmsOptionLog, blogAuditLogMapper));
    }

    @AfterThrowing(value = "execution(public * cxt.cn.controller.*.*(..))", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        BlogAuditLog cmsOptionLog = CURRENT_ACCOUNT.get();
        LOGGER.info("进入日志切面异常通知!!");
        LOGGER.info("异常信息:" + e.getMessage());
        LOGGER.info("==========保存日志=========");
        cmsOptionLog.setLogType(20);
        cmsOptionLog.setException(e.toString());
        threadPoolTaskExecutor.execute(new SaveLogThread(cmsOptionLog,blogAuditLogMapper));
    }

    /**
     * 请求参数拼装
     *
     * @param paramsArray
     * @return 请求参数
     */
    private String argsArrayToString(Object[] paramsArray) {
        if (paramsArray == null || paramsArray.length <= 0) {
            return "";
        }
        StringBuilder params = new StringBuilder();
        for (Object object : paramsArray) {
            if (object instanceof HttpServletRequest ||
                    object instanceof HttpServletResponse ||
                    object instanceof MultipartFile) {
                continue;
            }
            String jsonObj = JSONObject.toJSONString(object);
            if (!StringUtils.isEmpty(jsonObj)) {
                params.append(jsonObj).append(" ");
            }
        }
        return params.toString().trim();
    }

    /**
     * 保存日志
     */
    private static class SaveLogThread implements Runnable {

        private BlogAuditLog sysAuditLog;
        private BlogAuditLogMapper sysAuditLogMapper;

        SaveLogThread(BlogAuditLog sysAuditLog, BlogAuditLogMapper sysAuditLogMapper) {
            super();
            this.sysAuditLog = sysAuditLog;
            this.sysAuditLogMapper = sysAuditLogMapper;
        }

        @Override
        public void run() {
            try {
                sysAuditLogMapper.insert(sysAuditLog);
                CURRENT_ACCOUNT.remove();
                BEGIN_TIME_THREAD_LOCAL.remove();
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("更新日志出错", e);
            }
        }
    }
}