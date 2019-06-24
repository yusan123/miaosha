package com.yu.miaoshaproject.controller;

import com.yu.miaoshaproject.dao.UserDOMapper;
import com.yu.miaoshaproject.dataobject.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2019/6/9.
 */
@Controller
public class IndexController {

    @GetMapping("/getoptpage")
    public String hello(){
//        UserDO userDO = userDOMapper.selectByPrimaryKey(4444);
//        if(userDO !=null){
//            return userDO.getName();
//        }
        return "getopt";
    }
    @GetMapping("/")
    public String login(){
        return "login";
    }
}
