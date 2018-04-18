package com.mmall.common;

public class Const {

    public static final String CURRENT_USER = "current_user";

    public static final String EMAIL = "email";

    public static final String USERNAME = "username";

    public interface Role{
        int ROLE_ADMIN = 0;   //普通用户
        int ROLE_CUSTOMER = 1;       //管理员
    }
}
