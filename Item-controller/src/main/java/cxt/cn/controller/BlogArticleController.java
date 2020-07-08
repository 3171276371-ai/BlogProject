package cxt.cn.controller;


import cxt.cn.entity.BlogArticle;
import cxt.cn.service.IBlogArticleClassService;
import cxt.cn.service.IBlogArticleService;
import cxt.cn.utils.CommonResult;
import cxt.cn.vo.BlogArticleClassOut;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenxiaotian
 * @since 2020-07-07
 */
@RestController
@RequestMapping("/blogArticle")
@Api(tags="系统 文章接口")
public class BlogArticleController {
    @Autowired
    private IBlogArticleService blogArticleClassService;

    @ApiOperation("查询文章导航栏搜索")
    @GetMapping("/ActiveClassAllData")
    public CommonResult ActiveClassAllData (@RequestParam(value = "art_id")Long id,
                                            @RequestParam(value = "cate_id",required = false)Long classId,
                                            @RequestParam(value = "article_name",required = false)String name) {

        List<BlogArticle> artClass = blogArticleClassService.ActiveClassAllData(id,classId,name);
        return CommonResult.success(artClass);
    }



}

   