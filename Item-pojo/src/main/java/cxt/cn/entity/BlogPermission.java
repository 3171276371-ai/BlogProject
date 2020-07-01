package cxt.cn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import cxt.cn.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * cms_permission 
 * </p>
 *
 * @author chenxiaotian
 * @since 2020-06-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BlogPermission extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 父级权限ID 父级权限ID
     */
    private Long parentId;

    /**
     * 权限名称 权限中文名称
     */
    private String permissionName;

    /**
     * 权限编码 权限编码
     */
    private String value;

    /**
     * 权限类型 权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）
     */
    private Integer type;

    /**
     * 前端路由路径 前端路由路径
     */
    private String path;

    /**
     * 元数据
     */
    private String meta;

    /**
     * 路由名称
     */
    private String name;

    /**
     * 组件路由
     */
    private String component;

    /**
     * 重定向路由
     */
    private String redirect;

    /**
     * 是否被禁用
     */
    private Boolean ban;

    /**
     * 菜单排序
     */
    private Integer sort;


}
