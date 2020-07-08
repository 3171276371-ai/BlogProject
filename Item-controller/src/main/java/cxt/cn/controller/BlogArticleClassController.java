package cxt.cn.controller;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import cxt.cn.bo.AdminArticleClass;
import cxt.cn.dto.BlogArticleClassDto;
import cxt.cn.entity.BlogArticle;
import cxt.cn.entity.BlogArticleClass;
import cxt.cn.service.IBlogArticleClassService;
import cxt.cn.service.impl.BlogArticleClassServiceImpl;
import cxt.cn.utils.CommonResult;
import cxt.cn.vo.BlogArticleClassOut;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author chenxiaotian
 * @since 2020-07-06
 */
@RestController
@RequestMapping("/blogArticleClass")
@Api(tags="系统 文章分类接口")
public class BlogArticleClassController {


    @Autowired
    private IBlogArticleClassService blogArticleClassService;

    @ApiOperation("查询文章分类")
    @GetMapping("/ArtClassData")
    public CommonResult getArtClass() {
        List<BlogArticleClassOut> artClass = blogArticleClassService.getArtClass();
        return CommonResult.success(artClass);
    }
    @ApiOperation("添加文章分类")
    @PostMapping("/AddClassAllData")
    @PreAuthorize("hasAnyAuthority('article:add')")
    public CommonResult AddClassAllData (@RequestBody BlogArticleClassOut blogArticleClassOut) {
        boolean save = blogArticleClassService.AddClassAllData(blogArticleClassOut);
        if (save){
        return CommonResult.success("添加成功");
        }
        return CommonResult.failed("添加失败");
    }
}

