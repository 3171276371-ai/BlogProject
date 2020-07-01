package cxt.cn.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <h3>Software_development_studio</h3>
 * <p></p>
 *
 * @author : zhengyue
 * @date : 2020-06-08 05:50
 **/
@Component
public class ImageSuffix {
    @Value("${web.images-suffix}")
    List<String> suffix = new ArrayList<>();

    public boolean isImages(String fileName){
        System.out.println(suffix);
        //获取后缀名
        String str = FileNameUtil.getSuffix(fileName).toUpperCase();
        //校验后缀名
        if (suffix.contains(str)){
            return true;
        }
        return false;
    }
}
