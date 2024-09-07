package com.xueyi.auth.form;

import com.alibaba.fastjson2.JSONObject;
import com.xueyi.system.api.organize.domain.dto.SysDeptDto;
import com.xueyi.system.api.organize.domain.dto.SysPostDto;
import com.xueyi.system.api.organize.domain.dto.SysUserDto;
import lombok.Data;

/**
 * 用户注册对象
 *
 * @author xueyi
 */
@Data
public class RegisterBody {

    /** 部门信息 */
    private SysDeptDto dept;

    /** 岗位信息 */
    private SysPostDto post;

    /** 用户信息 */
    private SysUserDto user;

    /** 构造租户注册对象 */
    public JSONObject buildJson(){
        JSONObject json = new JSONObject();
        json.put("dept", getDept());
        json.put("post", getPost());
        json.put("user", getUser());
        return json;
    }
}
