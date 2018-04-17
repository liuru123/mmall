package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServiceResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.server.ServerCloneException;
import java.util.UUID;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServiceResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if(resultCount == 0){
            return ServiceResponse.createByErrorMessage("用户名不存在");
        }

        //todo 密码登录使用md5
        String MD5password = MD5Util.MD5EncodeUTF8(password);
        User user = userMapper.selectLogin(username,MD5password);
        if(user == null){
            return ServiceResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServiceResponse.createBySuccessMessageData("登录成功",user);
    }

    public ServiceResponse<String> register(User user){

        ServiceResponse<String> validResponse = this.checkValid(user.getUsername(),Const.USERNAME);
        if(!validResponse.isSuccess()){
            return validResponse;
        }

        validResponse = this.checkValid(user.getEmail(),Const.EMAIL);
        if(!validResponse.isSuccess()){
            return validResponse;
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);
        //md5加密
        user.setPassword(MD5Util.MD5EncodeUTF8(user.getPassword()));

        int result = userMapper.insert(user);
        if(result == 0){
            return ServiceResponse.createByErrorMessage("注册失败");
        }
        return ServiceResponse.createBySuccessMessage("注册成功");
    }

    @Override
    public ServiceResponse<String> checkValid(String str, String type) {
        if(StringUtils.isNotBlank(type)){
            //开始校验
            if(Const.USERNAME.equals(type)){
                int result = userMapper.checkUsername(str);
                if(result > 0){
                    return ServiceResponse.createByErrorMessage("用户名已存在");
                }
            }
            if(Const.EMAIL.equals(type)){
                int result = userMapper.checkEmail(type);
                if(result > 0){
                    return ServiceResponse.createByErrorMessage("email已存在");
                }
            }
        }else{
            return ServiceResponse.createByErrorMessage("参数错误");
        }
        return ServiceResponse.createBySuccessMessage("校验成功");
    }

    public ServiceResponse<String> selectQuestion(String username){
        ServiceResponse<String> serviceResponse = this.checkValid(username,Const.USERNAME);
        if(serviceResponse.isSuccess()){
            return ServiceResponse.createByErrorMessage("用户名不存在");
        }
        String question = userMapper.getQuestionByUsername(username);
        if(StringUtils.isNotBlank(question)){
            return ServiceResponse.createBySuccessMessageData("获取密码问题",question);
        }else{
            return ServiceResponse.createByErrorMessage("密码提示问题为空");
        }
    }

    public ServiceResponse<String> checkAnswer(String username,String question,String answer){
        int result = userMapper.checkAnswer(username,question,answer);
        if(result > 0){
            //说明这个问题答案是正确的，并且是这个用户的
            String forgetToken = UUID.randomUUID().toString();
            //将token存在本地guava缓存
            TokenCache.setKey("token_"+username,forgetToken);
            return ServiceResponse.createBySuccessMessageData("问题答案正确",forgetToken);
        }
        return ServiceResponse.createByErrorMessage("问题答案错误");
    }
}
