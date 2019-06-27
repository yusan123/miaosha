package com.yu.miaoshaproject.controller;

import com.alibaba.druid.util.StringUtils;
import com.yu.miaoshaproject.controller.VO.UserVO;
import com.yu.miaoshaproject.error.BussinessException;
import com.yu.miaoshaproject.error.EmBussinessError;
import com.yu.miaoshaproject.response.CommonReturnType;
import com.yu.miaoshaproject.service.UserService;
import com.yu.miaoshaproject.service.model.UserModel;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import sun.security.provider.MD5;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Administrator on 2019/6/9.
 */

//使用参数校验，这个注解必须要加
@Validated
@Controller
@RequestMapping("/user")
@CrossOrigin(allowedHeaders = "*",allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private HttpServletResponse httpServletResponse;


    public final String PHONE_REG = "1[3,5,7,8]\\d{9}";

    @RequestMapping("/get/{id}")
    @ResponseBody
    public CommonReturnType getUser(//@NotNull //校验数字不能使用notempty
                                    @PositiveOrZero //正数或0  @negativeOrZero 负数或0
                                        @PathVariable("id") Integer id) throws BussinessException {
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

    @PostMapping("/login")
    @ResponseBody
    public CommonReturnType login(@RequestParam @Pattern(regexp = PHONE_REG,message = "手机号格式不正确") String telphone,
                                  @RequestParam @NotBlank(message = "密码不能为空") String password) throws BussinessException {
        String encryptPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        UserModel userModel = userService.login(telphone,encryptPassword);
        //将登录的用户信息存入session中
        httpServletRequest.getSession().setAttribute(String.valueOf(userModel.getId()),userModel);
        return CommonReturnType.create(convertToViewObject(userModel));
    }

    @PostMapping(value = "/getopt")
    @ResponseBody
    public Object getOpt(@Pattern(regexp = PHONE_REG,message = "手机号格式不正确")@RequestParam String telphone) throws IOException {
        //生成验证码
        Random random = new Random();
        int code = random.nextInt(89999);
        code += 10000;
        //System.out.println(code);
        //将验证码与用户手机号关联起来
        httpServletRequest.getSession().setAttribute(telphone,String.valueOf(code));
        //将本次验证码通过短信平台发送到用户手机上
        System.out.println(String.format("手机号%s的验证码为%d",telphone,code));
        //httpServletResponse.sendRedirect("/user/showRegister");
        return CommonReturnType.create(null);
    }

    @GetMapping("/showRegister/{telphone}")
    public String showRegister(@PathVariable @Pattern(regexp = PHONE_REG) String telphone,Model model){
        model.addAttribute("telphone",telphone);
        return "register";
    }
    //用户注册
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType register(@NotBlank(message = "姓名不能为空") @RequestParam String name,
                                     @NotNull(message = "性别不能为空") @RequestParam Byte gender,
                                     @Min(value = 0L,message = "年龄不能小于0")
                                         @Max(value = 150L,message = "最大年龄不能超过150")
                                         @RequestParam Integer age,
                                     @Pattern(regexp = PHONE_REG,message = "手机号格式不正确") @RequestParam String telphone,
                                     @NotBlank(message = "密码不能为空") @RequestParam String password,
                                     @NotBlank(message = "验证码不能为空") @RequestParam String userCode) throws BussinessException {

        //验证验证码是否正确
//        if(StringUtils.isEmpty(name)
//                || StringUtils.isEmpty(telphone)
//                || StringUtils.isEmpty(password)
//                || StringUtils.isEmpty(userCode)
//                || gender ==null
//                || age == null){
//            throw new BussinessException(EmBussinessError.PARAMETER_VALIDATION_ERROR);
//        }
        String code = (String)httpServletRequest.getSession().getAttribute(telphone);
        if(!code.equals(userCode)){
            throw new BussinessException(EmBussinessError.PARAMETER_VALIDATION_ERROR,"验证码错误");
        }

        UserModel userModel = new UserModel();
        userModel.setAge(age);
        userModel.setTelphone(telphone);
        userModel.setGender(gender);
        userModel.setName(name);
        userModel.setRegisterMode("byphone");
        String md5passwd = DigestUtils.md5DigestAsHex(password.getBytes());
        System.out.println(md5passwd);
        userModel.setEncrptPassword(md5passwd);
        userModel.setThirdPartyId("11111");

        //调用服务存储进数据库
        userService.register(userModel);
        return CommonReturnType.create(convertToViewObject(userModel));
    }

    private UserModel convertToUserModel(UserVO userVO){
        if(userVO == null){
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userVO,userModel);
        return userModel;
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
