package cxt.cn.service;

import cxt.cn.entity.BlogArticle;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenxiaotian
 * @since 2020-07-07
 */
public interface IBlogArticleService extends IService<BlogArticle> {


    List<BlogArticle> ActiveClassAllData(Long id, Long classId, String name);



}
