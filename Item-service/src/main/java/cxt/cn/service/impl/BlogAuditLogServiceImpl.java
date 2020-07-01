package cxt.cn.service.impl;

import cxt.cn.entity.BlogAuditLog;
import cxt.cn.mapper.BlogAuditLogMapper;
import cxt.cn.service.IBlogAuditLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 访问日志信息  服务实现类
 * </p>
 *
 * @author chenxiaotian
 * @since 2020-07-01
 */
@Service
public class BlogAuditLogServiceImpl extends ServiceImpl<BlogAuditLogMapper, BlogAuditLog> implements IBlogAuditLogService {

}
