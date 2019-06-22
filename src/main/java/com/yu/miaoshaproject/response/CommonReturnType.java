package com.yu.miaoshaproject.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Administrator on 2019/6/22.
 */
@Data
@AllArgsConstructor
public class CommonReturnType {

    //表明请求的处理结果"success"或者"fail"
    private String status;
    //具体的数据
    //如果status=success，则data为前段需要的数据
    //status=fail，则data为通用的错误码格式
    private Object data;

    public static CommonReturnType create(Object result,String status){
        return new CommonReturnType(status,result);
    }

    public static CommonReturnType create(Object result){
        return create(result,"success");
    }

}
