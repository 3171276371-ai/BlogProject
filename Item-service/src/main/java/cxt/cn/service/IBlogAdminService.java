package cxt.cn.service;

import cxt.cn.dto.BlogAdminRegisterDto;
import cxt.cn.entity.BlogAdmin;
import com.baomidou.mybatisplus.extension.service.IService;
import cxt.cn.vo.RedisUserInfo;

/**
 * <p>
 * 管理员 服务类
 * </p>
 *
 * @author chenxiaotian
 * @since 2020-06-26
 */
public interface IBlogAdminService extends IService<BlogAdmin> {

    RedisUserInfo login(String account, String password);

    Boolean register(BlogAdminRegisterDto registerDto);
}
