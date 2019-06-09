package com.yu.miaoshaproject.controller;

import com.yu.miaoshaproject.controller.VO.UserVO;
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
    public UserVO getUser(@RequestParam("id") Integer id){
        //根据id查询一个用户
        UserModel userModel = userService.getUserById(id);
        UserVO userVO = new UserVO();
        if(userModel != null){
            BeanUtils.copyProperties(userModel,userVO);
        }
        return userVO;
    }
}
