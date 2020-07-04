package cxt.cn.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Auther: ChengXiaotian
 * @Date: 2020/7/2 09:24
 * @Description:
 */
@Data
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


}
