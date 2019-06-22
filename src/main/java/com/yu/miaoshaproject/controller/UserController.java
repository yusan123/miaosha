package com.yu.miaoshaproject.controller;

import com.yu.miaoshaproject.controller.VO.UserVO;
import com.yu.miaoshaproject.error.BussinessException;
import com.yu.miaoshaproject.error.EmBussinessError;
import com.yu.miaoshaproject.response.CommonReturnType;
import com.yu.miaoshaproject.service.UserService;
import com.yu.miaoshaproject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2019/6/9.
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/get")
    public CommonReturnType getUser(@RequestParam("id") Integer id) throws BussinessException {
        //调用服务根据id查询一个用户
        UserModel userModel = userService.getUserById(id);
        //转换成vo对象
        if (id == 999){
            System.out.println(1/0);
        }
        if (userModel == null){
            throw new BussinessException(EmBussinessError.USER_NOT_EXIST);
        }
        UserVO userVO = convertToViewObject(userModel);
        //返回通用的格式
        return CommonReturnType.create(userVO);
    }

    private UserVO convertToViewObject(UserModel userModel) {
        if (userModel == null){
            return null;
        }
        UserVO userVO = new UserVO();
        if(userModel != null){
            BeanUtils.copyProperties(userModel,userVO);
        }
        return userVO;
    }
}
