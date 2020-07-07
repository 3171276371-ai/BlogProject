package cxt.cn.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cxt.cn.dto.BlogRoleAddDto;
import cxt.cn.dto.BlogRoleUpdateDto;
import cxt.cn.entity.BlogPermission;
import cxt.cn.entity.BlogRole;
import cxt.cn.service.IBlogRoleService;
import cxt.cn.utils.CommonResult;
import cxt.cn.vo.BlogUserPermissionAdd;
import cxt.cn.vo.BlogUserRoleAdd;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * cms_role  前端控制器
 * </p>
 *
 * @author chenxiaotian
 * @since 2020-06-26
 */
@RestController
@RequestMapping("/blogRole")
@Api(tags = "系统 角色接口")
public class BlogRoleController {
    @Autowired
    private IBlogRoleService blogRoleService;


    @ApiOperation("查询角色信息user:role:get")
    @GetMapping("/{account}")
    public CommonResult getRole(@PathVariable String account){
        List<BlogRole> role = blogRoleService.getRole(account);
        if (role.isEmpty()){
            return CommonResult.failed("没有查询到角色");
        }
        return CommonResult.success("查询成功",role);
    }

    @ApiOperation("新增角色信息user:role:post")
    @PostMapping("/addRole")
    public CommonResult addRole(@RequestBody BlogRoleAddDto sysRoleAddDto){
        boolean result = blogRoleService.addRole(sysRoleAddDto);
        if (result){
            return CommonResult.success("新增角色成功");
        }else {
            return CommonResult.failed("新增失败");
        }
    }

    @ApiOperation("更新角色权限user:role:put")
    @PutMapping("/UpdateRole")
    public CommonResult updateRole(@RequestBody BlogRoleUpdateDto sysRoleUpdateDto){
        boolean result = blogRoleService.updateRole(sysRoleUpdateDto);
        if (result){
            return CommonResult.success("更新角色权限成功");
        }else {
            return CommonResult.failed("更新角色权限失败");
        }
    }

    @DeleteMapping("/{account}")
    @ApiOperation("删除角色")
    public CommonResult deletesRole(@PathVariable String account){
        boolean result = blogRoleService.deleteRole(account);
        if (result){
            return CommonResult.success("删除角色成功");
        }else {
            return CommonResult.failed("删除角色失败");
        }
    }



    @ApiOperation("更新用户角色用户端user:role:update")
    @PostMapping("/add/role")
    public CommonResult addRole(@Validated @RequestBody BlogUserRoleAdd roleAdd) {
        // 去重
        roleAdd.setRoleList(roleAdd.getRoleList().stream().distinct().collect(Collectors.toList()));
        if (blogRoleService.userAddRole(roleAdd)) {
            return CommonResult.success("添加成功");
        }
        return CommonResult.success("添加失败");
    }


    @ApiOperation("查询角色列表:role:list")
    @GetMapping("/getRolelist")
    public CommonResult getRoleList( @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "name", defaultValue = "") String name){
        IPage<BlogRole> page = new Page<>(pageNum, pageSize);
        return  CommonResult.success(blogRoleService.page(page,new QueryWrapper<BlogRole>()
                .lambda().like(BlogRole::getName,name).orderByDesc(BlogRole::getCreateTime)));

    }
}

