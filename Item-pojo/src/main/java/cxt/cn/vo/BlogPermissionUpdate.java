package cxt.cn.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("BlogPermissionUpdate :: 权限更新")
public class BlogPermissionUpdate {

    private static final long serialVersionUID = 1L;

    /**
     * 权限ID
     */
    @NotNull(message = "权限ID不能为空")
    @ApiModelProperty("权限ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**
     * 父级权限ID 父级权限ID
     */
    @ApiModelProperty("父级权限ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    /**
     * 权限名称 权限中文名称
     */
    @ApiModelProperty("权限名称")
    private String permissionName;

    /**
     * 权限编码 权限编码
     */
    @ApiModelProperty("权限编码")
    private String value;

    /**
     * 权限类型 权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）
     */
    @ApiModelProperty("权限类型 权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）")
    private Integer type;

    /**
     * 前端路由路径 前端路由路径
     */
    @ApiModelProperty("前端路由路径")
    private String path;

    /**
     * 元数据
     */
    @ApiModelProperty("元数据")
    private String meta;

    /**
     * 路由名称
     */
    @ApiModelProperty("路由名称")
    private String name;

    /**
     * 组件路由
     */
    @ApiModelProperty("组件路由")
    private String component;

    /**
     * 重定向路由
     */
    @ApiModelProperty("重定向路由")
    private String redirect;

    /**
     * 是否被禁用
     */
    @ApiModelProperty("是否被禁用")
    private Boolean ban;

    /**
     * 菜单排序
     */
    @ApiModelProperty("菜单排序")
    private Integer sort;
}
