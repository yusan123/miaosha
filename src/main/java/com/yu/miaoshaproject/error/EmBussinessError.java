package com.yu.miaoshaproject.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2019/6/22.
 */
@Getter
@AllArgsConstructor
public enum EmBussinessError implements CommonError{
    //1开头通用错误类型
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    OTHER_ERROR(19999,"其他异常"),


    //2开头的是用户相关错误
    USER_NOT_EXIST(20001,"用户信息不存在"),
    LOGIN_FAILED(20002,"登录失败，账号或者密码错误")
    ;
    private int errCode;
    private String errMsg;


    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
