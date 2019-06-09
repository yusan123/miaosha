package com.yu.miaoshaproject.service;

import com.yu.miaoshaproject.service.model.UserModel;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2019/6/9.
 */
public interface UserService {

    //通过用户id查询用户

    UserModel getUserById(Integer id);
}
