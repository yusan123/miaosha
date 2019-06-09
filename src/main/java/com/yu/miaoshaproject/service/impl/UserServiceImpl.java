package com.yu.miaoshaproject.service.impl;

import com.yu.miaoshaproject.dao.UserDOMapper;
import com.yu.miaoshaproject.dao.UserPasswordDOMapper;
import com.yu.miaoshaproject.dataobject.UserDO;
import com.yu.miaoshaproject.dataobject.UserPasswordDO;
import com.yu.miaoshaproject.service.UserService;
import com.yu.miaoshaproject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2019/6/9.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;
    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if (userDO ==null){
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel);
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(id);
        if (userPasswordDO != null){
            userModel.setEncrptPassword(userPasswordDO.getEncryptPassword());
        }
        return userModel;
    }
}
