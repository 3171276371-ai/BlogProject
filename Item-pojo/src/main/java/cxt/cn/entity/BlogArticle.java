package cxt.cn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import cxt.cn.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author chenxiaotian
 * @since 2020-07-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BlogArticle extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 文章标题
     */
    private String name;
    /**
     * 分类名称
     */
    private Long classId;

    /**
     * 围观次数
     */
    private Integer browseCount;

    /**
     * 评论
     */
    private Integer commentCount;

    /**
     * 喜欢数
     */
    private Integer likeCount;

    /**
     * 收藏数
     */
    private Integer collectCount;

    /**
     * 分类名称
     */
    private String cateName;

    /**
     * 文章描述
     */
    private String description;

    /**
     * 文章图片
     */
    private String image;


}
