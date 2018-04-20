package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServiceResponse;
import com.mmall.pojo.Category;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin/category/")
public class CategoryManageController {


    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;
    //增加品类管理

    /**
     * 添加商品品类
     *
     * @param session
     * @param categoryName
     * @param parentId
     * @return
     */
    @RequestMapping(value = "add_category.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") Integer parentId) {
        //判断用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请先登录");
        }
        //判断用户是否是管理员
        ServiceResponse response = iUserService.checkAdmin(user);
        if (response.isSuccess()) {
            //是管理员,进行商品添加
            return iCategoryService.addCategory(categoryName, parentId);
        }
        //不是管理员，无权限操作
        return ServiceResponse.createByErrorMessage("无权限，不能进行此操作");
    }


    /**
     * 更新商品品类名称
     *
     * @param session
     * @param categoryName
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "update_category.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> updateCategory(HttpSession session, String categoryName, int categoryId) {
        //判断用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请先登录");
        }
        //判断用户是否是管理员
        ServiceResponse response = iUserService.checkAdmin(user);
        if (response.isSuccess()) {
            //是管理员,进行商品添加
            return iCategoryService.updateCategoryName(categoryName, categoryId);
        }
        return ServiceResponse.createByErrorMessage("无权限做此操作");
    }


    /**
     * 获取当前分类平级的子节点下的分类
     *
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "get_category_list.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<List<Category>> getCategoryList(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        //判断用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请先登录");
        }
        //判断用户是否是管理员
        ServiceResponse response = iUserService.checkAdmin(user);
        if (response.isSuccess()) {
            //是管理员,进行商品查询
            return iCategoryService.getChildCategoryList(categoryId);
        }
        return ServiceResponse.createByErrorMessage("无权限做此操作");
    }

    /**
     * 深度查询分类的节点id及子节点id
     * @param session
     * @param categoryId
     * @return1
     */
    @RequestMapping(value = "get_category_deep_list.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<List<Integer>> getCategoryDeepList(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {

        //判断用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请先登录");
        }
        //判断用户是否是管理员
        ServiceResponse response = iUserService.checkAdmin(user);
        if(response.isSuccess()){
            return iCategoryService.getChildDeepCategoryList(categoryId);
        }
        return ServiceResponse.createByErrorMessage("无权限做此操作");
    }
}
