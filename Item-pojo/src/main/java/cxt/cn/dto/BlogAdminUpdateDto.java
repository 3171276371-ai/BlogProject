package cxt.cn.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Auther: ChengXiaotian
 * @Date: 2020/7/8 09:05
 * @Description:
 */
@Data
public class BlogAdminUpdateDto {

    @ApiModelProperty("姓名")
    @NotBlank(message = "姓名不能为空")
    private String  name;

    private String phone;

    private Integer sex;

    private String introduce;
}
