package cxt.cn.service.impl;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cxt.cn.bo.AdminArticleClass;
import cxt.cn.dto.BlogArticleClassDto;
import cxt.cn.entity.BlogArticleClass;
import cxt.cn.mapper.BlogArticleClassMapper;
import cxt.cn.service.IBlogArticleClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cxt.cn.vo.BlogArticleClassOut;
import io.lettuce.core.output.SocketAddressOutput;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenxiaotian
 * @since 2020-07-06
 */
@Service
public class BlogArticleClassServiceImpl extends ServiceImpl<BlogArticleClassMapper, BlogArticleClass> implements IBlogArticleClassService {

    @Override
    public List<BlogArticleClassOut> getArtClass() {
        List<BlogArticleClassOut> blogArticleClassOut = new ArrayList<>();
        List<BlogArticleClass> list = this.list();
        for (BlogArticleClass blogArticleClass : list) {
            AdminArticleClass adminArticleClass = JSONUtil.toBean(blogArticleClass.getDetshare(), AdminArticleClass.class);

            BlogArticleClassOut blogArticleClassOut1 = new BlogArticleClassOut();
            blogArticleClassOut1.setDetshare(adminArticleClass.getDetshare());
            BeanUtils.copyProperties(blogArticleClass,blogArticleClassOut1);
            blogArticleClassOut.add(blogArticleClassOut1);
        }
        return blogArticleClassOut;

    }

    @Override
    public boolean AddClassAllData(BlogArticleClassOut blogArticleClassOut) {
        List<BlogArticleClassDto> detshare = blogArticleClassOut.getDetshare();
        BlogArticleClass blogArticleClass = new BlogArticleClass();
        Map<String,List<BlogArticleClassDto>> map = new HashMap<>();
        map.put("detshare", detshare);
        JSON parse = JSONUtil.parse(map);
        blogArticleClass.setDetshare(parse.toString());
        blogArticleClass.setName(blogArticleClassOut.getName());
        blogArticleClass.setId(blogArticleClass.getId());
        boolean save = this.save(blogArticleClass);
        return save;

    }


}
