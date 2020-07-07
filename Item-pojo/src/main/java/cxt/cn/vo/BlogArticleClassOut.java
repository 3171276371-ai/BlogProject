package cxt.cn.vo;

import cxt.cn.dto.BlogArticleClassDto;
import cxt.cn.entity.BaseEntity;
import cxt.cn.entity.BlogArticleClass;
import lombok.Data;

import java.util.List;

/**
 * @Auther: ChengXiaotian
 * @Date: 2020/7/6 18:10
 * @Description:
 */
@Data
public class BlogArticleClassOut extends BaseEntity {


    private static final long serialVersionUID=1L;



    /**
     * 分类名称
     */
    private String name;

    /**
     * 子分类
     */
    private List<BlogArticleClassDto> detshare;
}
