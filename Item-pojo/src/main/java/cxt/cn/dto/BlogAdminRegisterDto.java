package cxt.cn.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <h3>Software_development_studio</h3>
 * <p>注册dto</p>
 *
 * @author : zhengyue
 * @date : 2020-05-18 13:40
 **/
@Data
@ApiModel("SysAdminRegisterDto :: 管理员注册")
public class BlogAdminRegisterDto {
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
    private String password;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    @NotBlank(message = "姓名不能为空")
    private  String name;

    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private Integer sex;
}
