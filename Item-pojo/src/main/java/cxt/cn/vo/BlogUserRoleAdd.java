package cxt.cn.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Auther: ChengXiaotian
 * @Date: 2020/7/4 15:54
 * @Description:
 */
@Data
@ApiModel("BlogUserRoleAdd :: 用户角色添加")
public class BlogUserRoleAdd {

    @NotNull(message = "用户ID不能为空")
    @JsonSerialize(using= ToStringSerializer.class)
    @ApiModelProperty("用户ID")
    private Long id;

    @ApiModelProperty("角色码")
    @NotNull(message = "角色码不能为空")
    private List<String> RoleList;

    /**
     * 是否马上生效
     */
    @ApiModelProperty("是否马上生效")
    private Boolean ifFast = false;
}
