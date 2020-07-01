package cxt.cn.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * <p>
 * 成员表
 * </p>
 *
 * @author chenxiaotian
 * @since 2020-07-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BlogMember extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 性别 0->默认，1->男，2->女，3->保密
     */
    private Boolean sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 头像
     */
    private String picture;

    /**
     * 是否通过0->默认，1->在职，2->离职
     */
    private Boolean status;

    /**
     * 是否显示0 ->不显示，1->显示
     */
    private Boolean isShow;

    /**
     * 密码
     */
    private String password;


}
