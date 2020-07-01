package cxt.cn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cxt.cn.entity.BlogPermission;
import cxt.cn.vo.BlogUserPermissionAdd;

import java.util.List;

/**
 * <p>
 * cms_permission  服务类
 * </p>
 *
 * @author chenxiaotian
 * @since 2020-06-26
 */
public interface IBlogPermissionService extends IService<BlogPermission> {

    List<BlogPermission> getPermissionList(String account);

    boolean userAddPermission(BlogUserPermissionAdd permissionAdd);
}
