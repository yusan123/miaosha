package com.yu.miaoshaproject.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.yu.miaoshaproject.dao.UserDOMapper;
import com.yu.miaoshaproject.dao.UserPasswordDOMapper;
import com.yu.miaoshaproject.dataobject.UserDO;
import com.yu.miaoshaproject.dataobject.UserPasswordDO;
import com.yu.miaoshaproject.error.BussinessException;
import com.yu.miaoshaproject.error.EmBussinessError;
import com.yu.miaoshaproject.service.UserService;
import com.yu.miaoshaproject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(id);
        return convertFromDataObjectToModel(userDO, userPasswordDO);
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BussinessException {
        if(userModel == null||
            StringUtils.isEmpty(userModel.getName()) ||
            userModel.getAge()==null ||
            userModel.getGender() == null ||
            StringUtils.isEmpty(userModel.getEncrptPassword())){
            throw new BussinessException(EmBussinessError.PARAMETER_VALIDATION_ERROR);
        }


        //通过数据领域对象转为DO对象
        UserDO userDO = convertToUserDO(userModel);
        userDOMapper.insertSelective(userDO);
        //插入数据库后可以从插入的DO对象中拿到model的id作为password的外键userid
        userModel.setId(userDO.getId());
        UserPasswordDO userPasswordDO = converToUserPasswordDO(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);
    }

    private UserPasswordDO converToUserPasswordDO(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        //userModel的id值肯定是个空
        userPasswordDO.setUserId(userModel.getId());
        userPasswordDO.setEncryptPassword(userModel.getEncrptPassword());
        return userPasswordDO;
    }

    private UserDO convertToUserDO(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel,userDO);
        return userDO;
    }

    private UserModel convertFromDataObjectToModel(UserDO userDO, UserPasswordDO userPasswordDO) {
        if (userDO == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO, userModel);
        if (userPasswordDO != null) {
            userModel.setEncrptPassword(userPasswordDO.getEncryptPassword());
        }
        return userModel;
    }
}
