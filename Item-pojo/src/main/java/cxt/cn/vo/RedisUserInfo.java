package cxt.cn.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import cxt.cn.entity.BlogPermission;
import lombok.Data;

import java.util.List;

/**
 * @Author:chenxiaotian
 * @Description:redis实体类
 * @Date: 11:16 上午 2020/5/16
 */
@Data
public class RedisUserInfo {

    /**
     * 用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 用户number
     */
    private String staffNumber;

    /**
     * 用户name
     */
    private String staffName;

    /**
     * 用户电话号码
     */
    private String phone;


    /**
     * 用户简介
     */
    private String introduce;

    /**
     * 用户性别
     */
    private Integer sex;

    /**
     * token
     */
    private String token;

    /**
     * 权限列表
     */
    private List<BlogPermission> permissionList;


}
