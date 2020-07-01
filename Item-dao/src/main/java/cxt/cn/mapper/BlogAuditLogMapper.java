package cxt.cn.mapper;

import cxt.cn.entity.BlogAuditLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 访问日志信息  Mapper 接口
 * </p>
 *
 * @author chenxiaotian
 * @since 2020-07-01
 */
@Mapper
public interface BlogAuditLogMapper extends BaseMapper<BlogAuditLog> {

}
