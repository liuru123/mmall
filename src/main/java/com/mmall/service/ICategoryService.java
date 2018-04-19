package com.mmall.service;

import com.mmall.common.ServiceResponse;
import com.mmall.pojo.Category;

import java.util.List;


public interface ICategoryService {

    public ServiceResponse<String> addCategory(String categoryName, Integer parentId);

    public ServiceResponse<String> updateCategoryName(String categoryName,Integer categoryId);

    public ServiceResponse<List<Category>> getChildCategoryList(Integer categoryId);

    public ServiceResponse<List<Integer>> getChildDeepCategoryList(Integer categoryId);
}

