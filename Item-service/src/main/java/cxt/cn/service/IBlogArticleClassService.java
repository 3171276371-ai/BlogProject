package cxt.cn.service;

import cxt.cn.entity.BlogArticleClass;
import com.baomidou.mybatisplus.extension.service.IService;
import cxt.cn.vo.BlogArticleClassOut;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenxiaotian
 * @since 2020-07-06
 */
public interface IBlogArticleClassService extends IService<BlogArticleClass> {

    List<BlogArticleClassOut> getArtClass();

    boolean AddClassAllData(BlogArticleClassOut blogArticleClassOut);
}
