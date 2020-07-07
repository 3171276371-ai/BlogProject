package cxt.cn.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Auther: ChengXiaotian
 * @Date: 2020/7/7 20:00
 * @Description:
 */
@Data
@ApiModel("BlogArticleClassDto :: 分类子类")
public class BlogArticleClassDto {


    private Long id;
    /**
     * 分类名称
     */
    private String name;

    /**
     * 子分类
     */
    private Long pid;
}
