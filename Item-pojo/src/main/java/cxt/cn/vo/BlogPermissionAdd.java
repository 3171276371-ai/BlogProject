package cxt.cn.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("BlogPermissionAdd :: 权限添加")
public class BlogPermissionAdd {
    /**
     * 父级权限ID 父级权限ID
     */
    @NotNull(message = "父权限ID不能为空")
    @JsonSerialize(using= ToStringSerializer.class)
    @ApiModelProperty("父权限ID")
    private Long parentId;

    /**
     * 权限名称 权限中文名称
     */
    @NotBlank(message = "权限中文名称不能为空")
    @ApiModelProperty("权限中文名称")
    private String permissionName;

    /**
     * 权限编码 权限编码
     */
    @NotBlank(message = "权限编码不能为空")
    @ApiModelProperty("权限编码")
    private String value;

    /**
     * 权限类型 权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）
     */
    @NotNull(message = "权限类型不能为空 0->目录；1->菜单；2->按钮")
    @ApiModelProperty("权限类型不能为空 0->目录；1->菜单；2->按钮")
    private Integer type;

    /**
     * 元数据
     */
    @ApiModelProperty("元数据")
    private String meta;

    /**
     * 前端路由路径
     */
    @ApiModelProperty("前端路由路径")
    private String path;

    /**
     * 路由名称
     */
    @ApiModelProperty("路由名称")
    private String name;

    /**
     * 组件路径
     */
    @ApiModelProperty("组件路径")
    private String component;

    /**
     * 重定向路径
     */
    @ApiModelProperty("重定向路径")
    private String redirect;
}
