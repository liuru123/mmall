package com.mmall.service;

import com.mmall.common.ServiceResponse;

import java.util.Map;

public interface IOrderService {
    public ServiceResponse pay(Integer userId, Long orderNo, String path);
    public ServiceResponse aliCallback(Map<String,String> params);
    public ServiceResponse queryOrderStatus(Integer userId,Long orderNo);
    public ServiceResponse createOrder(Integer userId,Integer shippingId);
    public ServiceResponse cancel(Integer userId,Long orderNo);
    public ServiceResponse getOrderCartProduct(Integer userId);
    public ServiceResponse getOrderDetail(Integer userId,Long orderNo);
    public ServiceResponse getOrderList(Integer userId,Integer pageNum,Integer pageSize);

    public ServiceResponse manageList(Integer pageNum,Integer pageSize);
    public ServiceResponse manageDetail(Long orderNo);
    public ServiceResponse manageSearch(Long orderNo,Integer pageNum,Integer pageSize);
    public ServiceResponse manageSendGoods(Long orderNo);

}

