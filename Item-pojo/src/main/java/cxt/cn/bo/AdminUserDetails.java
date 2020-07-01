package cxt.cn.bo;


import cxt.cn.entity.BlogAdmin;
import cxt.cn.entity.BlogPermission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:chenxiaotian
 * @Description:
 * @Date: CREATED IN 11:19 上午 2020/5/16
 */
public class AdminUserDetails implements UserDetails {
    private BlogAdmin cmsUser;
    private List<BlogPermission> permissionList;

    public AdminUserDetails(BlogAdmin cmsUser, List<BlogPermission> permissionList) {
        this.cmsUser = cmsUser;
        this.permissionList = permissionList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 返回当前用户的权限
        return permissionList.stream()
                .filter(permission -> permission.getValue() != null)
                .map(permission -> new SimpleGrantedAuthority(permission.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return cmsUser.getPassword();
    }

    @Override
    public String getUsername() {
        return cmsUser.getAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return cmsUser.getIsEnable();
    }
}
