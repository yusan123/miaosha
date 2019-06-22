package com.yu.miaoshaproject.error;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Administrator on 2019/6/22.
 */
@Data
public class BussinessException extends Exception implements CommonError {

    private CommonError commonError;
    private EmBussinessError emBussinessError;

    public BussinessException(CommonError commonError){
        this.commonError = commonError;
    }
//    public BussinessException(EmBussinessError emBussinessError){
//        this.emBussinessError = emBussinessError;
//    }

    public BussinessException(CommonError commonError,String errMsg){
        this.commonError = commonError;
        this.commonError.setErrMsg(errMsg);
    }

    @Override
    public int getErrCode() {
        return commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        commonError.setErrMsg(errMsg);
        return commonError;

    }
}
