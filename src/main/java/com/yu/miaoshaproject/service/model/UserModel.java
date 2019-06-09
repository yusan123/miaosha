package com.yu.miaoshaproject.service.model;

import lombok.Data;

/**
 * Created by Administrator on 2019/6/9.
 */
@Data
public class UserModel {

    private Integer id;

    private String name;

    private Byte gender;

    private Integer age;

    private String telphone;

    private String registerMode;

    private String thirdPartyId;

    private String encrptPassword;

}
