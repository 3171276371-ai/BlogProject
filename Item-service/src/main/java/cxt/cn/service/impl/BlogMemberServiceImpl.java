package cxt.cn.service.impl;

import cxt.cn.entity.BlogMember;
import cxt.cn.mapper.BlogMemberMapper;
import cxt.cn.service.IBlogMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 成员表 服务实现类
 * </p>
 *
 * @author chenxiaotian
 * @since 2020-07-01
 */
@Service
public class BlogMemberServiceImpl extends ServiceImpl<BlogMemberMapper, BlogMember> implements IBlogMemberService {

}
