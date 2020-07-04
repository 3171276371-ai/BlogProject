package cxt.cn.service;

import cxt.cn.dto.BlogRoleAddDto;
import cxt.cn.dto.BlogRoleUpdateDto;
import cxt.cn.entity.BlogRole;
import com.baomidou.mybatisplus.extension.service.IService;
import cxt.cn.vo.BlogUserPermissionAdd;
import cxt.cn.vo.BlogUserRoleAdd;

import java.util.List;

/**
 * <p>
 * cms_role  服务类
 * </p>
 *
 * @author chenxiaotian
 * @since 2020-06-26
 */
public interface IBlogRoleService extends IService<BlogRole> {

    List<BlogRole> getRole(String account);

    boolean addRole(BlogRoleAddDto sysRoleAddDto);

    boolean deleteRole(String account);

    boolean updateRole(BlogRoleUpdateDto sysRoleUpdateDto);

    boolean userAddRole(BlogUserRoleAdd roleAdd);
}
