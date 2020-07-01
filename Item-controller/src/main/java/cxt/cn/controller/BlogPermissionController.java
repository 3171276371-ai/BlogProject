package cxt.cn.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cxt.cn.entity.BlogPermission;
import cxt.cn.service.IBlogPermissionService;
import cxt.cn.utils.CommonResult;
import cxt.cn.vo.BlogPermissionAdd;
import cxt.cn.vo.BlogPermissionBanRestart;
import cxt.cn.vo.BlogPermissionUpdate;
import cxt.cn.vo.BlogUserPermissionAdd;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/**
 * <p>
 * cms_permission  前端控制器
 * </p>
 *
 * @author chenxiaotian
 * @since 2020-06-26
 */
@RestController
@RequestMapping("/blogPermission")
@Api(tags="系统 权限接口")
public class BlogPermissionController {

    @Autowired
    private IBlogPermissionService permissionService;

    /**
     * 查询用户权限列表
     * @param account
     * @return
     */
    @GetMapping("/{account}")
    @ApiOperation("查询用户权限user:permission:get")
    public CommonResult getPermission(@PathVariable String account){
        return CommonResult.success( "查询成功",permissionService.getPermissionList(account));
    }

    /**
     * 更新或添加用户权限
     * @param permissionAdd
     * @return
     */
    @ApiOperation("更新用户权限用户端user:permission:update")
    @PutMapping("/add/permission")
    public CommonResult addPermission(@Validated @RequestBody BlogUserPermissionAdd permissionAdd) {
        // 去重
        permissionAdd.setAuthList(permissionAdd.getAuthList().stream().distinct().collect(Collectors.toList()));
        if (permissionService.userAddPermission(permissionAdd)) {
            return CommonResult.success("添加成功");
        }
        return CommonResult.success("添加失败");
    }

    /**
     * 查询用户的权限
     * @param pageSize
     * @param pageNum
     * @param name
     * @return
     */
    @ApiOperation("查询权限permission:list")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('permission:list')")
    public CommonResult list(
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "name", defaultValue = "") String name
    ) {
        IPage<BlogPermission> page = new Page<>(pageNum, pageSize);
        QueryWrapper<BlogPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(BlogPermission::getPermissionName, name).orderByDesc(BlogPermission::getCreateTime);
        System.out.println(permissionService.page(page, queryWrapper).getRecords().toString());
        return CommonResult.success(permissionService.page(page, queryWrapper));
    }

    /**
     * 删除权限
     * @param permissionDelete
     * @return
     */
    @ApiOperation("删除权限permission:delete")
    @PostMapping("/delete")
    @PreAuthorize("hasAnyAuthority('permission:delete')")
    public CommonResult delete(@RequestBody @Validated BlogPermissionBanRestart permissionDelete){
        if (permissionService.removeByIds(permissionDelete.getPermissionId())) {
            return CommonResult.success("删除成功");
        } else {
            return CommonResult.failed("权限不存在");
        }
    }

    /**
     * 查询所有权限不分页
     * @return
     */
    @ApiOperation("查询所有权限,平面结构permission:all")
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('permission:all')")
    public CommonResult all() {
        QueryWrapper<BlogPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByAsc(BlogPermission::getCreateTime);
        return CommonResult.success(permissionService.list(queryWrapper));
    }

    /**
     * 更新用户权限
     * @param permissionUpdate
     * @return
     */
    @ApiOperation("更新权限，permission:update")
    @PutMapping()
    @PreAuthorize("hasAnyAuthority('permission:update')")
    public CommonResult update(@RequestBody @Validated BlogPermissionUpdate permissionUpdate){
        BlogPermission sysPermission = new BlogPermission();
        BeanUtils.copyProperties(permissionUpdate, sysPermission);
        if (permissionService.updateById(sysPermission)) {
            return CommonResult.success("更新成功");
        } else {
            return CommonResult.failed("更新失败");
        }
    }

    /**
     * 添加权限
     * @param permissionAdd
     * @return
     */
    @ApiOperation("添加权限permission:add")
    @PostMapping()
    @PreAuthorize("hasAnyAuthority('permission:add')")
    public CommonResult addPermission(@RequestBody @Validated BlogPermissionAdd permissionAdd) {
        System.out.println(permissionAdd);
        if (permissionService.getOne(new QueryWrapper<BlogPermission>().lambda()
                .eq(BlogPermission::getName, permissionAdd.getPermissionName())) != null) {
            return CommonResult.failed("权限名称重复");
        }
        BlogPermission cmsPermission = new BlogPermission();
        BeanUtils.copyProperties(permissionAdd, cmsPermission);
        if (permissionService.save(cmsPermission)) {
            return CommonResult.success("添加成功");
        }
        return CommonResult.failed("服务器异常");
    }
}

