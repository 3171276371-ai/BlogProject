package cxt.cn.controller;


import cxt.cn.bo.AdminSaveDetails;
import cxt.cn.dto.BlogAdminLoginDto;
import cxt.cn.dto.BlogAdminRegisterDto;
import cxt.cn.service.IBlogAdminService;
import cxt.cn.service.IBlogPermissionService;
import cxt.cn.service.RedisService;
import cxt.cn.utils.CommonResult;
import cxt.cn.vo.RedisUserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 管理员 前端控制器
 * </p>
 *
 * @author chenxiaotian
 * @since 2020-06-26
 */
@RestController
@RequestMapping("/blogAdmin")
@Api(tags="系统 管理员接口")
public class BlogAdminController {



    @Autowired
    private RedisService redisService;
    @Autowired
    private IBlogAdminService adminService;
    @Autowired
    private AdminSaveDetails saveDetails;
    @Autowired
    private IBlogPermissionService permissionService;

    /**
     * 用户登录
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("管理员登陆")
    public CommonResult login(@RequestBody @Validated BlogAdminLoginDto cmsLogin){
        RedisUserInfo redisUserInfo = adminService.login(cmsLogin.getAccount(), cmsLogin.getPassword());
        if (redisUserInfo.getToken() == null) {
            return CommonResult.failed("登陆失败，或存在密码不正确");
        }
        redisUserInfo.setPermissionList(null);
        return CommonResult.success(redisUserInfo);
    }

    /**
     * 用户登出
     */
    @DeleteMapping("/logout")
    @ApiOperation("管理员登出")
    public CommonResult logout() {
        redisService.remove(saveDetails.getSysAdmin().getAccount());
        return CommonResult.success("登出成功");
    }

    /**
     * 用户注册
     * @param registerDto
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("管理员注册")
    public CommonResult register(@RequestBody @Validated BlogAdminRegisterDto registerDto) {
        Boolean register = adminService.register(registerDto);
        if(register) {
            return CommonResult.success("注册成功");
        }
        return CommonResult.failed("注册失败，该账户已存在");
    }

}

