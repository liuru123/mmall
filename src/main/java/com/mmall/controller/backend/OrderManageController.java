package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServiceResponse;
import com.mmall.pojo.User;
import com.mmall.service.IOrderService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/order/")
public class OrderManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IOrderService iOrderService;


    /**
     * 管理员查看订单列表
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("order_list.do")
    @ResponseBody
    public ServiceResponse orderList(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        //判断用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请先登录");
        }
        //判断用户是否是管理员
        ServiceResponse response = iUserService.checkAdmin(user);
        if (response.isSuccess()) {
            //管理员查看订单列表
            return iOrderService.manageList(pageNum,pageSize);
        }
        return ServiceResponse.createByErrorMessage("无权限做此操作");
    }


    /**
     * 管理员查看订单详情
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping("order_detail.do")
    @ResponseBody
    public ServiceResponse orderDetail(HttpSession session, Long orderNo){
        //判断用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请先登录");
        }
        //判断用户是否是管理员
        ServiceResponse response = iUserService.checkAdmin(user);
        if (response.isSuccess()) {
            //管理员查看订单列表
            return iOrderService.manageDetail(orderNo);
        }
        return ServiceResponse.createByErrorMessage("无权限做此操作");
    }


    /**
     * 管理员进行搜索订单
     * @param session
     * @param orderNo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("order_search.do")
    @ResponseBody
    public ServiceResponse orderSearch(HttpSession session,Long orderNo,@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                       @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        //判断用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请先登录");
        }
        //判断用户是否是管理员
        ServiceResponse response = iUserService.checkAdmin(user);
        if (response.isSuccess()) {
            //管理员搜索订单
            return iOrderService.manageSearch(orderNo,pageNum,pageSize);
        }
        return ServiceResponse.createByErrorMessage("无权限做此操作");
    }


    @RequestMapping("order_send_goods.do")
    @ResponseBody
    public ServiceResponse orderSendGoods(HttpSession session,Long orderNo){
        //判断用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请先登录");
        }
        //判断用户是否是管理员
        ServiceResponse response = iUserService.checkAdmin(user);
        if (response.isSuccess()) {
            //管理员发货
            return iOrderService.manageSendGoods(orderNo);
        }
        return ServiceResponse.createByErrorMessage("无权限做此操作");
    }
}
