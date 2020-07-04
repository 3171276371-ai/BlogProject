package cxt.cn.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cxt.cn.bo.AdminSaveDetails;
import cxt.cn.dto.BlogAdminRegisterDto;
import cxt.cn.entity.BlogAdmin;
import cxt.cn.entity.BlogPermission;
import cxt.cn.mapper.BlogAdminMapper;
import cxt.cn.mapper.BlogPermissionMapper;
import cxt.cn.mapper.BlogRoleMapper;
import cxt.cn.service.IBlogAdminService;
import cxt.cn.service.IBlogPermissionService;
import cxt.cn.service.RedisService;
import cxt.cn.utils.JwtUtils;
import cxt.cn.vo.RedisUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 管理员 服务实现类
 * </p>
 *
 * @author chenxiaotian
 * @since 2020-06-26
 */
@Service
@Slf4j
public class BlogAdminServiceImpl extends ServiceImpl<BlogAdminMapper, BlogAdmin> implements IBlogAdminService {

    @Autowired
    private BlogAdminMapper blogAdminMapper;

    @Autowired
    private BlogRoleMapper roleMapper;
    @Autowired
    private BlogPermissionMapper permissionMapper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RedisService redisService;
    @Autowired
    private AdminSaveDetails saveDetails;
    @Autowired
    private IBlogPermissionService permissionService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.expiration}")
    private Long redisTime;

    @Override
    public RedisUserInfo login(String account, String password) {
        String token = null;
        RedisUserInfo redisUserInfo = new RedisUserInfo();
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(account);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            token = jwtUtils.generateToken(userDetails);

            // 获取用户所携带的路由信息
            List<BlogPermission> cmsPermissionList = permissionService.getPermissionList(account);
            redisUserInfo.setStaffNumber(account);
            //查询用户信息
            BlogAdmin user = blogAdminMapper.selectOne(new QueryWrapper<BlogAdmin>().lambda().eq(BlogAdmin::getAccount, account));

            redisUserInfo.setIntroduce(user.getIntroduce());
            redisUserInfo.setSex(user.getSex());
            redisUserInfo.setPhone(user.getPhone());
            redisUserInfo.setId(user.getId());
            redisUserInfo.setStaffName(user.getName());
            redisUserInfo.setPermissionList(cmsPermissionList);
            redisUserInfo.setToken(tokenHead + token);
            redisService.set(account, JSON.toJSONString(redisUserInfo));
            redisService.expire(account, redisTime);

        } catch (Exception e) {
            log.warn("登陆异常:{}", e.getMessage());
        }
        return redisUserInfo;
    }

    @Override
    public Boolean register(BlogAdminRegisterDto registerDto) {
        if(StringUtils.isEmpty(registerDto.getPassword())){
            registerDto.setPassword("000000");
        }
        BlogAdmin aminInfo = blogAdminMapper.selectOne(new QueryWrapper<BlogAdmin>().lambda()
                .eq(BlogAdmin::getAccount, registerDto.getAccount()));
        if(!ObjectUtil.isNotNull(aminInfo)) {
            BlogAdmin sysAdmin = new BlogAdmin();
            BeanUtils.copyProperties(registerDto, sysAdmin);
            sysAdmin.setPassword(passwordEncoder.encode(sysAdmin.getPassword()));
            //默认头像地址
            sysAdmin.setAvatar("");
            int insert = blogAdminMapper.insert(sysAdmin);
            if(insert == 1){
                return true;
            } else {
                return false;
            }
        }
        return false;
    }



}
