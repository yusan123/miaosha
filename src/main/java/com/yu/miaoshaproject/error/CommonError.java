package com.yu.miaoshaproject.error;

/**
 * Created by Administrator on 2019/6/22.
 */
public interface CommonError {
    int getErrCode();
    String getErrMsg();
    CommonError setErrMsg(String errMsg);
}
