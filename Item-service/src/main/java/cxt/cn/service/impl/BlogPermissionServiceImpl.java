package cxt.cn.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cxt.cn.bo.AdminAuthData;
import cxt.cn.entity.BlogAdmin;
import cxt.cn.entity.BlogPermission;
import cxt.cn.entity.BlogRole;
import cxt.cn.mapper.BlogAdminMapper;
import cxt.cn.mapper.BlogPermissionMapper;
import cxt.cn.mapper.BlogRoleMapper;
import cxt.cn.service.IBlogPermissionService;
import cxt.cn.service.RedisService;
import cxt.cn.vo.BlogUserPermissionAdd;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * cms_permission  服务实现类
 * </p>
 *
 * @author chenxiaotian
 * @since 2020-06-26
 */
@Service
@Slf4j
public class BlogPermissionServiceImpl extends ServiceImpl<BlogPermissionMapper, BlogPermission> implements IBlogPermissionService {

    @Autowired
    private BlogPermissionMapper permissionMapper;


    @Autowired
    private BlogRoleMapper roleMapper;
    @Autowired
    private BlogAdminMapper adminMapper;
    @Autowired
    private RedisService redisService;
    private static final Logger LOGGER = LoggerFactory.getLogger(BlogAdmin.class);
    @Override
    public List<BlogPermission> getPermissionList(String account) {
        // 查询用户权限值
        BlogAdmin cmsAdmin = adminMapper.selectOne(new QueryWrapper<BlogAdmin>()
                .lambda()
                .eq(BlogAdmin::getAccount, account));
        if (cmsAdmin == null) {
            return new ArrayList<>();
        }
        try {
            // 字符串生成对象
            AdminAuthData userAuthData = cn.hutool.json.JSONUtil.toBean(cmsAdmin.getAuthData(), AdminAuthData.class);
            // 权限列表
            List<String> authList = userAuthData.getAuthList();
            // 角色列表
            List<String> roleList = userAuthData.getRoleList();
            // 查询角色 拿到角色表中的权限值
            System.out.println(roleList.toString());
            if (roleList.size() != 0) {
                List<BlogRole> authRoleList = roleMapper.selectList(new QueryWrapper<BlogRole>().lambda()
                        .select(BlogRole::getAuthData)
                        .in(BlogRole::getId, roleList));
                // 拿到角色中的权限值并保存
                for (BlogRole userRole : authRoleList) {
                    AdminAuthData roleAuth = cn.hutool.json.JSONUtil.toBean(userRole.getAuthData(), AdminAuthData.class);
                    if (roleAuth.getAuthList() != null) {
                        authList.addAll(roleAuth.getAuthList());
                    }
                }
            }
            // 去重
            authList = authList.stream().distinct().collect(Collectors.toList());
            // 查出权限列表
            if (authList.size() != 0) {
                return permissionMapper.selectList(
                        new QueryWrapper<BlogPermission>().lambda()
                                .in(BlogPermission::getValue, authList));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ArrayList<>();
    }


    private void clearToken(boolean ifFast, Long id) {
        // 判断是否马上登出
        BlogAdmin cmsUser = adminMapper.selectOne(new QueryWrapper<BlogAdmin>()
                .lambda()
                .select(BlogAdmin::getAccount)
                .eq(BlogAdmin::getId, id));
        if (ifFast && cmsUser != null) {
            redisService.remove(cmsUser.getAccount());
        } else if (!ifFast && cmsUser != null) {
            redisService.expire(cmsUser.getAccount(), 60 * 60);
        }
    }

    @Override
    public boolean userAddPermission(BlogUserPermissionAdd permissionAdd) {
        BlogAdmin sysAdmin =  adminMapper.selectById(permissionAdd.getId());
        if (sysAdmin == null) {
            return false;
        }
        // 拿出原本的角色 + 权限 字符串
        String authStr = sysAdmin.getAuthData();
        // 把字符串转对象
        AdminAuthData userAuthData = JSONUtil.toBean(authStr, AdminAuthData.class);
        // 把新的权限列表设置到对象中
        userAuthData.setAuthList(permissionAdd.getAuthList());
        // 把对象转化成json设置到对象中
        sysAdmin.setAuthData(JSONUtil.toJsonStr(userAuthData));
        // 清除token
        if (adminMapper.updateById(sysAdmin) == 1) {
            clearToken(permissionAdd.getIfFast(), permissionAdd.getId());
            return true;
        }
        return false;
    }

}
