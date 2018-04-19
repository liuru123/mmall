package com.mmall.service;

import com.mmall.common.ServiceResponse;
import com.mmall.pojo.User;

public interface IUserService {

    public ServiceResponse<User> login(String username, String password);

    public ServiceResponse<String> register(User user);

    public ServiceResponse<String> checkValid(String str, String type);

    public ServiceResponse<String> selectQuestion(String username);

    public ServiceResponse<String> checkAnswer(String username, String question, String answer);

    public ServiceResponse<String> forgetRestPass(String username, String passNew, String forgetToken);

    public ServiceResponse<String> restPassword(String passOld,String passNew,User user);

    public ServiceResponse<User> update_user_info(User user);

    public ServiceResponse<User> get_user_information(int UserId);

    public ServiceResponse<String> checkAdmin(User user);
}
