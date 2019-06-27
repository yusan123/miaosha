package com.yu.miaoshaproject.error;

import com.yu.miaoshaproject.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.*;

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
        }else if (e instanceof ConstraintViolationException){
            data.put("error",EmBussinessError.PARAMETER_VALIDATION_ERROR.getErrCode());
            //对异常msg处理
            String message = e.getMessage();
            String[] errorNum = message.split(",");
            System.out.println("有"+errorNum.length+"个校验错误！");
            List<String> errorList = Arrays.asList(errorNum);
            StringBuilder newErrMsg = new StringBuilder();
            errorList.forEach(err ->{
                //newErrMsg.append(",");
                newErrMsg.append(err.split(":")[1]);
            });
            data.put("errMsg",newErrMsg);
        }else{
            data.put("errCode",EmBussinessError.OTHER_ERROR.getErrCode());
            data.put("errMsg",e.getMessage());
        }
        return CommonReturnType.create(data,"fail");
    }
}
