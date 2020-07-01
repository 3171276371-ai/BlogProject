package cxt.cn.bo;

import lombok.Data;

import java.util.List;

/**
 * @Author:chenxiaotian
 * @Description:权限列表
 * @Date: 10:02 上午 2020/5/16
 */
@Data
public class AdminAuthData {
    private List<String> authList;
    private List<String> roleList;
}