package com.yu.miaoshaproject.controller;

import com.yu.miaoshaproject.dao.UserDOMapper;
import com.yu.miaoshaproject.dataobject.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2019/6/9.
 */
@RestController
public class HelloController {

    @Autowired
    private UserDOMapper userDOMapper;

    @RequestMapping("/")
    public String hello(){
        UserDO userDO = userDOMapper.selectByPrimaryKey(4444);
        if(userDO !=null){
            return userDO.getName();
        }
        return "没有查询到数据，请检查数据库配置";
    }
}
