package cxt.cn.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel("BlogPermissionBanRestart :: 权限删除")
public class BlogPermissionBanRestart {

    @ApiModelProperty("权限ID")
    @NotEmpty(message = "权限ID不能为空")
    private List<Long> permissionId;
}