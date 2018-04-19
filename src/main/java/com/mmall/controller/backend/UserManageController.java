package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ServiceResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/*这里的manager好像是被默认解析成了其他的路径，所以访问请求
* 会被拦截*/
@Controller
@RequestMapping("/admin/user/")
public class UserManageController {

    @Autowired
    private IUserService iUserService;

    /**
     *管理员登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<User> login(String username, String password, HttpSession session){
        ServiceResponse<User> response  = iUserService.login(username,password);
        if(response.isSuccess()){
            User user = response.getData();
            if(user.getRole().equals(Const.Role.ROLE_ADMIN)){
                //如果是管理员，则存在session中
                session.setAttribute(Const.CURRENT_USER,user);
                return response;
            }else {
                return ServiceResponse.createByErrorMessage("不是管理员，不能登录");
            }
        }
        return response;
    }
}
