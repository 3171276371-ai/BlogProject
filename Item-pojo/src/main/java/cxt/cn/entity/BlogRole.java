package cxt.cn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import cxt.cn.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * cms_role 
 * </p>
 *
 * @author chenxiaotian
 * @since 2020-06-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BlogRole extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 角色中文名称 角色中文名称
     */
    private String name;

    /**
     * 权限码 权限编码
     */
    private String authData;

    /**
     * 角色说明
     */
    private String remark;


}
