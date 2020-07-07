package cxt.cn;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cxt.cn.bo.AdminArticleClass;
import cxt.cn.bo.AdminAuthData;
import cxt.cn.entity.BlogAdmin;
import cxt.cn.entity.BlogArticleClass;
import cxt.cn.service.IBlogArticleClassService;
import cxt.cn.utils.CommonResult;
import cxt.cn.utils.JsonUtils;
import cxt.cn.vo.BlogArticleClassOut;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: ChengXiaotian
 * @Date: 2020/7/6 16:44
 * @Description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItemApplication.class)
public class test {
    @Autowired
    private IBlogArticleClassService blogArticleClassService;


    @Test
    public void test2(){
        List<BlogArticleClassOut> blogArticleClassOut = new ArrayList<>();
        List<BlogArticleClass> list = blogArticleClassService.list();
        for (BlogArticleClass blogArticleClass : list) {
            AdminArticleClass adminArticleClass = JSONUtil.toBean(blogArticleClass.getDetshare(), AdminArticleClass.class);

            BlogArticleClassOut blogArticleClassOut1 = new BlogArticleClassOut();
            blogArticleClassOut1.setDetshare(adminArticleClass.getDetshare());
            BeanUtils.copyProperties(blogArticleClass,blogArticleClassOut1);
            blogArticleClassOut.add(blogArticleClassOut1);
        }


    }

}

