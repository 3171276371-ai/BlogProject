package cxt.cn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 管理员
 * </p>
 *
 * @author chenxiaotian
 * @since 2020-06-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BlogAdmin extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 用户名
     */
    private String account;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别 0->男，1->女，2->保密
     */
    private Integer sex;

    /**
     * 电话 
     */
    private String phone;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 启停状态 1->启用 0->禁用
     */
    private Boolean isEnable;

    /**
     * 权限集合
     */
    private String authData;


    private String introduce;






}
