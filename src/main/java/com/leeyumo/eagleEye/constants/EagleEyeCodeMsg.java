package com.leeyumo.eagleEye.constants;

import com.leeyumo.common.constants.BaseEnum;

import java.util.Optional;

public enum EagleEyeCodeMsg implements BaseEnum<EagleEyeCodeMsg> {
    forceLoginOut(70000001,"您的账号在其他设备登录"),
    hasUserOnline(70000002,"当前已有用户在登录"),
    tokenInvalid(70000003,"登录已经过期，请重新登录"),
    tokenIllegal(70000004,"无效令牌"),
    methodNotSupport(70000005,"请求的方法不支持"),
    passwordIncorrect(70000006,"用户名或者密码错误"),
    accessDenied(70000007,"没有操作该功能的权限"),
    verifyCodeIncorrect(70000008,"验证码不正确"),
    accountNotExist(70000010,"账户不存在"),
    paramLack(70000009,"请求参数有误"),
    dataInvalid(70000010,"数据格式不正确"),
    requestFail(70000011,"请求失败"),
    AuthFailure(70000012,"认证失败");

    private Integer code;
    private String msg;
    EagleEyeCodeMsg(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }


    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.msg;
    }

    public static Optional<EagleEyeCodeMsg> of(Integer code) {
        return Optional.ofNullable(BaseEnum.parseByCode(EagleEyeCodeMsg.class,code));
    }

    public String getMsg() {
        return msg;
    }
}
