package cxt.cn.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author:chenxiaotian
 * @Description:
 * @Date: 7:50 下午 2020/5/16
 */
@Data
@ApiModel("BlogAdminLoginDto :: 管理员登录")
public class BlogAdminLoginDto {

    /**
     * 帐号
     */
    @ApiModelProperty("帐号")
    @NotBlank(message = "帐号不能为空")
    private String account;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}