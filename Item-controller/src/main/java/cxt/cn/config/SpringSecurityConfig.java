package cxt.cn.config;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cxt.cn.bo.AdminSaveDetails;
import cxt.cn.bo.AdminUserDetails;
import cxt.cn.entity.BlogAdmin;
import cxt.cn.entity.BlogPermission;
import cxt.cn.service.IBlogAdminService;
import cxt.cn.service.RedisService;
import cxt.cn.utils.RestAuthenticationEntryPoint;
import cxt.cn.utils.RestfulAccessDeniedHandler;
import cxt.cn.vo.RedisUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:chenxiaotian
 * @Description:
 * @Date: CREATED IN 10:12 上午 2020/5/15
 */
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private IBlogAdminService iCmsUserService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
//                .successForwardUrl("/index")
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers(
                // 用来获取api-docs的URI
                // 安全选项
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/v2/**",
                "/webjars/**",
                "/druid/**",
                "/test/**",
                "/images/**"
        )
                .permitAll()
                .antMatchers(
                        "/blogAdmin/login", "/blogAdmin/register","/blogArticleClass/**","/blogArticle/**"
                )
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                .anyRequest()
                .authenticated()
                 .and()
                // 自定义 jwt过滤器
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        //添加自定义未授权和未登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**
         * 指定用户认证时，默认从哪里获取认证用户信息
         */
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }
    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> {
           BlogAdmin user = iCmsUserService.getOne(new QueryWrapper<BlogAdmin>().lambda().eq(
                   BlogAdmin::getAccount, username
            ));
            iCmsUserService.getOne(new QueryWrapper<BlogAdmin>().lambda().eq(
                   BlogAdmin::getAccount, username
            ));
            if (user != null) {
                try {
                    RedisUserInfo redisUserInfo = JSON.parseObject(redisService.get(username), RedisUserInfo.class);
                    // 从redis拿权限
                    if (redisUserInfo == null) {
                        redisUserInfo = new RedisUserInfo();
                    }
                    List<BlogPermission> permissionList = redisUserInfo.getPermissionList();
                    if (permissionList == null) {
                        permissionList = new ArrayList<>();
                    }
                    System.out.println(JSON.toJSONString(permissionList));
                    userSaveDetails().setSysAdmin(user);
                    return new AdminUserDetails(user, permissionList);
                } catch (Exception e) {
                    throw new UsernameNotFoundException(e.getMessage());
                }
            }
            throw new UsernameNotFoundException("用户名或密码错误");
        };
    }

    @Bean
    public AdminSaveDetails userSaveDetails(){
        return new AdminSaveDetails();
    }
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

}

