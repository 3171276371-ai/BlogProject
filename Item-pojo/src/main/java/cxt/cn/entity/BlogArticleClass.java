package cxt.cn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import cxt.cn.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author chenxiaotian
 * @since 2020-07-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BlogArticleClass extends BaseEntity {

    private static final long serialVersionUID=1L;



    /**
     * 分类名称
     */
    private String name;

    /**
     * 子分类
     */
    private String detshare;


}
