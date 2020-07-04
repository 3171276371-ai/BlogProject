package cxt.cn.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cxt.cn.bo.AdminAuthData;
import cxt.cn.bo.AdminSaveDetails;
import cxt.cn.dto.BlogRoleAddDto;
import cxt.cn.dto.BlogRoleUpdateDto;
import cxt.cn.entity.BlogAdmin;
import cxt.cn.entity.BlogRole;
import cxt.cn.mapper.BlogAdminMapper;
import cxt.cn.mapper.BlogRoleMapper;
import cxt.cn.service.IBlogRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cxt.cn.service.RedisService;
import cxt.cn.vo.BlogUserRoleAdd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * cms_role  服务实现类
 * </p>
 *
 * @author chenxiaotian
 * @since 2020-06-26
 */
@Service
public class BlogRoleServiceImpl extends ServiceImpl<BlogRoleMapper, BlogRole> implements IBlogRoleService {

    @Autowired
    private BlogRoleMapper roleMapper;

    @Autowired
    private BlogAdminMapper adminMapper;

    @Autowired
    private AdminSaveDetails adminSaveDetails;

    @Autowired
    private RedisService redisService;



    @Override
    public List<BlogRole> getRole(String account) {
        QueryWrapper<BlogAdmin> queryWrapper = new QueryWrapper();
        BlogAdmin BlogAdmin = adminMapper.selectOne(queryWrapper.lambda().eq(cxt.cn.entity.BlogAdmin::getAccount, account));
        System.out.println(BlogAdmin);
        AdminAuthData userAuthData = cn.hutool.json.JSONUtil.toBean(BlogAdmin.getAuthData(), AdminAuthData.class);
        List<String> roleList = userAuthData.getRoleList();
        if (roleList.size() != 0) {
            return roleMapper.selectList(new QueryWrapper<BlogRole>().lambda()
                    .in(BlogRole::getId, roleList));
        }
        return new ArrayList<>();


    }

    @Override
    public boolean addRole(BlogRoleAddDto BlogRoleAddDto) {
        BlogRole BlogRole1 = roleMapper.selectOne(new QueryWrapper<BlogRole>().lambda().eq(BlogRole::getName, BlogRoleAddDto.getName()));
        if (ObjectUtil.isNull(BlogRole1)){
            BlogRole BlogRole = new BlogRole();
            BeanUtil.copyProperties(BlogRoleAddDto,BlogRole);
            AdminAuthData adminAuthData = new AdminAuthData();
            List<String> collect = BlogRoleAddDto.getAuthData().stream().distinct().collect(Collectors.toList());
            adminAuthData.setAuthList(collect);
            BlogRole.setAuthData(JSONUtil.toJsonStr(adminAuthData));
            BlogRole.setCreateId(adminSaveDetails.getSysAdmin().getId());
            int insert = roleMapper.insert(BlogRole);
            return insert==1?true:false;
        }
        return false;
    }

    @Override
    public boolean updateRole(BlogRoleUpdateDto BlogRoleUpdateDto) {
        BlogRole BlogRole = roleMapper.selectOne(new QueryWrapper<BlogRole>().lambda().eq(cxt.cn.entity.BlogRole::getName, BlogRoleUpdateDto.getName()));
        if (ObjectUtil.isNotNull(BlogRole)){
            AdminAuthData adminAuthData = new AdminAuthData();
            List<String> collect = BlogRoleUpdateDto.getAuthData().stream().distinct().collect(Collectors.toList());
            adminAuthData.setAuthList(collect);
            BlogRole.setAuthData(JSONUtil.toJsonStr(adminAuthData));
            BlogRole.setUpdateId(adminSaveDetails.getSysAdmin().getId());
            int update = roleMapper.updateById(BlogRole);
            return update==1?true:false;
        }
        return false;
    }

    private void clearToken(boolean ifFast, Long id) {
        // 判断是否马上登出
        BlogAdmin cmsUser = adminMapper.selectOne(new QueryWrapper<BlogAdmin>()
                .lambda()
                .select(BlogAdmin::getAccount)
                .eq(BlogAdmin::getId, id));
        if (ifFast && cmsUser != null) {
            redisService.remove(cmsUser.getAccount());
        } else if (!ifFast && cmsUser != null) {
            redisService.expire(cmsUser.getAccount(), 60 * 60);
        }
    }
    @Override
    public boolean userAddRole(BlogUserRoleAdd roleAdd) {
        BlogAdmin sysAdmin =  adminMapper.selectById(roleAdd.getId());
        if (sysAdmin == null) {
            return false;
        }
        // 拿出原本的角色 + 权限 字符串
        String authStr = sysAdmin.getAuthData();
        // 把字符串转对象
        AdminAuthData userAuthData = JSONUtil.toBean(authStr, AdminAuthData.class);
        // 把新的权限列表设置到对象中
        userAuthData.setRoleList(roleAdd.getRoleList());
        // 把对象转化成json设置到对象中
        sysAdmin.setAuthData(JSONUtil.toJsonStr(userAuthData));
        // 清除token
        if (adminMapper.updateById(sysAdmin) == 1) {
           clearToken(roleAdd.getIfFast(),roleAdd.getId());
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteRole(String account) {
        BlogRole BlogRole = roleMapper.selectOne(new QueryWrapper<BlogRole>().lambda().eq(cxt.cn.entity.BlogRole::getName, account));
        if (ObjectUtil.isNotNull(BlogRole)){
            int detele = roleMapper.deleteById(BlogRole.getId());
            return detele==1?true:false;
        }
        return false;
    }
}
