package com.yu.miaoshaproject.error;

import com.yu.miaoshaproject.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * Created by Administrator on 2019/6/22.
 */
@RestControllerAdvice
public class MSExceptionHandler{
    @ExceptionHandler
    @ResponseStatus(HttpStatus.OK)
    public CommonReturnType msExHandler(Exception e){
        HashMap<String,Object> data = new HashMap<>();
        if (e instanceof BussinessException){
            BussinessException bussinessException = (BussinessException) e;
            data.put("errCode",bussinessException.getErrCode());
            data.put("errMsg",bussinessException.getErrMsg());
        }else{
            data.put("errCode",EmBussinessError.OTHER_ERROR.getErrCode());
            data.put("errMsg",EmBussinessError.OTHER_ERROR.getErrMsg());
        }
        return CommonReturnType.create(data,"fail");
    }
}
