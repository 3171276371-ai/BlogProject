package cxt.cn.utils;

import java.util.UUID;

/**
 * @Author: xiaxinlin
 * @Date: 2019/12/9 20:28
 */
public class UUIDUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
