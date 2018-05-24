package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServiceResponse;
import com.mmall.dao.ShippingMapper;
import com.mmall.pojo.Shipping;
import com.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {


    @Autowired
    private ShippingMapper shippingMapper;

    public ServiceResponse add(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int rowCount = shippingMapper.insert(shipping);
        if (rowCount > 0) {
            Map result = Maps.newHashMap();
            result.put("shippingId", shipping.getId());
            return ServiceResponse.createBySuccessMessageData("新建地址成功", result);
        }
        return ServiceResponse.createByErrorMessage("新建地址失败");
    }

    /**
     * 如果只传一个shippingId，就会有横向越权的安全漏洞出现
     * 因为，没有和用户关联起来
     * @param shippingId
     * @return
     */
    public ServiceResponse delete(Integer userId,Integer shippingId){
        int deleteCount = shippingMapper.deleteByShippingIdUserId(userId,shippingId);
        if(deleteCount > 0){
            return ServiceResponse.createBySuccessMessage("删除地址成功");
        }
        return ServiceResponse.createByErrorMessage("删除地址失败");
    }

    public ServiceResponse update(Integer userId,Shipping shipping){
        shipping.setUserId(userId);
        int updateCount = shippingMapper.updateByShippingIdUserId(shipping);
        if(updateCount > 0){
            return ServiceResponse.createBySuccessMessage("更新地址成功");
        }
        return ServiceResponse.createByErrorMessage("更新地址失败");
    }

    public ServiceResponse<Shipping> select(Integer userId,Integer shippingId){
        Shipping shipping = shippingMapper.selectByShippingIdUserId(userId,shippingId);
        if(shipping == null){
            return ServiceResponse.createByErrorMessage("查找不到对应的收货地址信息");
        }
        return ServiceResponse.createBySuccessMessageData("您所查询的地址信息",shipping);
    }

    public ServiceResponse<PageInfo> list(Integer userId, Integer pageNum, Integer pageSize){
        if(userId == null){
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServiceResponse.createBySuccessMessageData("分页查询地址列表",pageInfo);
    }
}
