package cxt.cn.bo;

import cxt.cn.dto.BlogArticleClassDto;
import cxt.cn.entity.BlogArticleClass;
import lombok.Data;

import java.util.List;

/**
 * @Auther: ChengXiaotian
 * @Date: 2020/7/6 16:42
 * @Description:
 */
@Data
public class AdminArticleClass {
    private List<BlogArticleClassDto> detshare;
}
