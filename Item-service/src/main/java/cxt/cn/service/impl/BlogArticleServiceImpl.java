package cxt.cn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cxt.cn.entity.BlogArticle;
import cxt.cn.mapper.BlogArticleMapper;
import cxt.cn.service.IBlogArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenxiaotian
 * @since 2020-07-07
 */
@Service
public class BlogArticleServiceImpl extends ServiceImpl<BlogArticleMapper, BlogArticle> implements IBlogArticleService {


    @Autowired
    private BlogArticleMapper blogArticleMapper;

    @Override
    public List<BlogArticle> ActiveClassAllData(Long id, Long classId, String name) {
        List<BlogArticle> blogArticles = blogArticleMapper.selectList(new QueryWrapper<BlogArticle>().lambda()
                .eq(BlogArticle::getId, id).or().eq(BlogArticle::getClassId, classId).or().like(BlogArticle::getName, name));
        return blogArticles;

    }



}
