package cxt.cn.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@ApiModel("SysRoleUpdateDto :: 角色添加")
public class BlogRoleUpdateDto {

    /**
     * 角色中文名称 角色中文名称
     */
    @ApiModelProperty("角色中文名")
    @NotBlank(message = "角色名称不为空")
    private String name;

    /**
     * 权限码 权限编码
     */
    @ApiModelProperty("角色中文名称")
    @NotBlank(message = "角色名称不为空")
    private List<String> authData;

}