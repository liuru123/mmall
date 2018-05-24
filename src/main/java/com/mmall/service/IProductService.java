package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServiceResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;

public interface IProductService {
    public ServiceResponse saveOrUpdateProduct(Product product);
    public ServiceResponse<String> updateStatusSale(Integer productId,Integer status);
    public ServiceResponse<ProductDetailVo> manageProductDetail(Integer productId);
    public ServiceResponse<PageInfo> getProductList(Integer pageNum, Integer pageSize);
    public ServiceResponse<PageInfo> productSearch(Integer productId,String productName,Integer pageNum,Integer pageSize);
    public ServiceResponse<ProductDetailVo> getProductDetail(Integer productId);
    public ServiceResponse<PageInfo> getProductByKeywordCategory(String keyword,Integer categoryId,
                                                                 Integer pageNum,Integer pageSize,
                                                                 String orderBy);
}
