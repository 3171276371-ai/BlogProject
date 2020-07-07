package cxt.cn.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 许轩浩
 * @date 2020/05/15
 */
//根据数据库修改
@Data
public class BaseEntity {
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(hidden = true)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createId;

    @ApiModelProperty(hidden = true)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long updateId;

    /**
     * 创建时间
     */
    @JSONField(format="yyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(hidden = true)
    private Date createTime;

    /**
     * 更新时间
     */
    @JSONField(format="yyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(hidden = true)
    private Date updateTime;

    /**
     * 逻辑删除
     */
    @TableLogic
    @ApiModelProperty(hidden = true)
    private Boolean isDel;
}
