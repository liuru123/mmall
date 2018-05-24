package com.mmall.service;

import com.mmall.common.ServiceResponse;
import com.mmall.vo.CartVo;

public interface ICartService {
    public ServiceResponse<CartVo> add(Integer userId, Integer productId, Integer count);
    public ServiceResponse<CartVo> update(Integer userId,Integer productId,Integer count);
    public ServiceResponse<CartVo> deleteProduct(Integer userId,String productIds);
    public ServiceResponse<CartVo> list(Integer userId);
    public ServiceResponse<CartVo> selectOrUnSelectAll(Integer userId,Integer productId,Integer checked);
    public ServiceResponse<Integer> getCartProductCount(Integer userId);
}
