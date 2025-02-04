package com.yunque.system.organize.controller.admin;

import com.yunque.common.core.utils.core.ObjectUtil;
import com.yunque.common.core.utils.core.StrUtil;
import com.yunque.common.core.utils.file.FileTypeUtil;
import com.yunque.common.core.utils.file.MimeTypeUtil;
import com.yunque.common.core.web.result.AjaxResult;
import com.yunque.common.core.web.result.R;
import com.yunque.common.core.web.validate.V_CUS;
import com.yunque.common.log.annotation.Log;
import com.yunque.common.log.enums.BusinessType;
import com.yunque.common.security.annotation.AdminAuth;
import com.yunque.common.security.service.TokenUserService;
import com.yunque.common.security.utils.SecurityUserUtils;
import com.yunque.common.web.entity.controller.BasisController;
import com.yunque.file.api.domain.SysFile;
import com.yunque.file.api.feign.RemoteFileService;
import com.yunque.system.api.model.LoginUser;
import com.yunque.system.api.organize.domain.dto.SysUserDto;
import com.yunque.system.organize.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

/**
 * 系统服务 | 组织模块 | 个人信息管理 | 管理端 业务处理
 *
 * @author xueyi
 */
@AdminAuth
@RestController
@RequestMapping("/admin/user/profile")
public class ASysProfileController extends BasisController {

    @Autowired
    private TokenUserService tokenService;

    @Autowired
    private RemoteFileService remoteFileService;

    @Autowired
    private ISysUserService userService;

    /**
     * 个人信息
     */
    @GetMapping
    public AjaxResult profile() {
        SysUserDto user = SecurityUserUtils.getUser();
        userService.userDesensitized(user);
        return success(user);
    }

    /**
     * 修改用户
     */
    @PutMapping
    @Log(title = "个人信息管理 - 基本信息修改", businessType = BusinessType.UPDATE)
    public AjaxResult editProfile(@Validated({V_CUS.class}) @RequestBody SysUserDto user) {
        LoginUser loginUser = SecurityUserUtils.getLoginUser();
        if (userService.updateUserProfile(loginUser.getUserId(), user.getNickName(), user.getSex(), user.getProfile()) > 0) {
            // 更新缓存用户信息
            loginUser.getUser().setNickName(user.getNickName());
            loginUser.getUser().setSex(user.getSex());
            loginUser.getUser().setProfile(user.getProfile());
            tokenService.setLoginUser(loginUser);
            return success();
        }
        return error("修改个人信息异常，请联系管理员！");
    }

    /**
     * 修改账号
     */
    @PutMapping("/userName")
    @Log(title = "个人信息管理 - 修改账号", businessType = BusinessType.UPDATE)
    public AjaxResult editUserName(String userName) {
        LoginUser loginUser = SecurityUserUtils.getLoginUser();
        if (StrUtil.isEmpty(userName)) return error("不能设置为空账号！");
        else if (StrUtil.equals(userName, loginUser.getUser().getUserName()))
            return error("该账号为当前使用账号，无需更换！");
        else if (userService.checkUserNameUnique(loginUser.getUserId(), userName))
            return error("该账号已被使用！");
        if (userService.updateUserName(loginUser.getUserId(), userName) > 0) {
            // 更新缓存
            loginUser.getUser().setUserName(userName);
            tokenService.setLoginUser(loginUser);
            return success();
        }
        return error("更绑邮箱异常，请联系管理员！");
    }

    /**
     * 更绑邮箱
     */
    @PutMapping("/email")
    @Log(title = "个人信息管理 - 更绑邮箱", businessType = BusinessType.UPDATE)
    public AjaxResult editEmail(String email) {
        LoginUser loginUser = SecurityUserUtils.getLoginUser();
        if (StrUtil.isEmpty(email)) return error("不能设置为空邮箱！");
        else if (StrUtil.equals(email, loginUser.getUser().getEmail()))
            return error("该邮箱为当前使用邮箱，无需更换！");
        else if (userService.checkEmailUnique(loginUser.getUserId(), email)) return error("该邮箱已被使用！");
        if (userService.updateEmail(loginUser.getUserId(), email) > 0) {
            // 更新缓存
            loginUser.getUser().setEmail(email);
            tokenService.setLoginUser(loginUser);
            return success();
        }
        return error("更绑邮箱异常，请联系管理员！");
    }

    /**
     * 更绑手机号
     */
    @PutMapping("/phone")
    @Log(title = "个人信息管理 - 更绑手机号", businessType = BusinessType.UPDATE)
    public AjaxResult editPhone(String phone) {
        LoginUser loginUser = SecurityUserUtils.getLoginUser();
        if (StrUtil.isEmpty(phone)) return error("不能设置为空手机号！");
        else if (StrUtil.equals(phone, loginUser.getUser().getPhone()))
            return error("该邮箱为当前使用手机号，无需更换！");
        else if (userService.checkPhoneUnique(loginUser.getUserId(), phone))
            return error("该手机号已被使用！");
        if (userService.updatePhone(loginUser.getUserId(), phone) > 0) {
            // 更新缓存
            loginUser.getUser().setPhone(phone);
            tokenService.setLoginUser(loginUser);
            return success();
        }
        return error("更绑手机号异常，请联系管理员！");
    }

    /**
     * 修改密码
     */
    @PutMapping("/password")
    @Log(title = "个人信息管理 - 重置密码", businessType = BusinessType.UPDATE)
    public AjaxResult editPassword(String oldPassword, String newPassword) {
        LoginUser loginUser = SecurityUserUtils.getLoginUser();
        String password = loginUser.getUser().getPassword();
        if (!SecurityUserUtils.matchesPassword(oldPassword, password)) return error("修改失败，旧密码错误！");
        if (SecurityUserUtils.matchesPassword(newPassword, password)) return error("新旧密码不能相同！");
        if (userService.resetUserPassword(loginUser.getUserId(), SecurityUserUtils.encryptPassword(newPassword)) > 0) {
            // 更新缓存用户密码
            loginUser.getUser().setPassword(SecurityUserUtils.encryptPassword(newPassword));
            tokenService.setLoginUser(loginUser);
            return success();
        }
        return error("修改密码异常，请联系管理员！");
    }

    /**
     * 头像上传
     */
    @PostMapping("/avatar")
    @Log(title = "个人信息管理 - 修改头像", businessType = BusinessType.UPDATE)
    public AjaxResult editAvatar(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            LoginUser loginUser = SecurityUserUtils.getLoginUser();
            String extension = FileTypeUtil.getExtension(file);
            if (!StrUtil.equalsAnyIgnoreCase(extension, MimeTypeUtil.IMAGE_EXTENSION)) {
                return error("文件格式不正确，请上传" + Arrays.toString(MimeTypeUtil.IMAGE_EXTENSION) + "格式");
            }
            R<SysFile> fileResult = remoteFileService.upload(file);
            if (ObjectUtil.isNull(fileResult) || ObjectUtil.isNull(fileResult.getData()))
                return error("文件服务异常，请联系管理员！");
            String url = fileResult.getData().getUrl();
            if (userService.updateUserAvatar(SecurityUserUtils.getUserId(), url) > 0) {
                String oldAvatarUrl = loginUser.getUser().getAvatar();
                if (StrUtil.isNotEmpty(oldAvatarUrl)) {
                    remoteFileService.delete(oldAvatarUrl);
                }
                AjaxResult ajax = success();
                ajax.put(AjaxResult.URL_TAG, url);
                // 更新缓存 - 用户头像
                loginUser.getUser().setAvatar(url);
                tokenService.setLoginUser(loginUser);
                return ajax;
            }
        }
        return error("上传图片异常，请联系管理员！");
    }
}
