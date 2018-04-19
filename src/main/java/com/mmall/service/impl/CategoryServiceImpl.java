package com.mmall.service.impl;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.ServiceResponse;
import com.mmall.dao.CategoryMapper;

import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;

    public ServiceResponse<String> addCategory(String categoryName, Integer parentId) {
        if (StringUtils.isBlank(categoryName) || parentId == null) {
            return ServiceResponse.createByErrorMessage("添加商品品类参数错误");
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);

        int insertCount = categoryMapper.insert(category);
        if (insertCount > 0) {
            return ServiceResponse.createBySuccessMessage("添加商品种类成功");
        }
        return ServiceResponse.createByErrorMessage("添加商品种类失败");
    }

    public ServiceResponse<String> updateCategoryName(String categoryName, Integer categoryId) {
        if (StringUtils.isBlank(categoryName) || categoryId == null) {
            return ServiceResponse.createByErrorMessage("添加商品品类参数错误");
        }

        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int updateCount = categoryMapper.insertSelective(category);
        if (updateCount > 0) {
            return ServiceResponse.createBySuccessMessage("更新商品名称成功");
        }
        return ServiceResponse.createByErrorMessage("更新商品名称失败");
    }

    public ServiceResponse<List<Category>> getChildCategoryList(Integer categoryId) {
        List<Category> categoryList = categoryMapper.getChildCategoryList(categoryId);
        if (CollectionUtils.isEmpty(categoryList)) {
            logger.info("未找到该分类下的子分类");
        }
        return ServiceResponse.createBySuccessData(categoryList);
    }


    /**
     * 递归查询本节点的id及孩子节点的id
     * @param categoryId
     * @return
     */
    public ServiceResponse<List<Integer>> getChildDeepCategoryList(Integer categoryId) {
        Set<Category> categorySet = Sets.newHashSet();
        getChildDeep(categorySet, categoryId);

        List<Integer> categoryIdlist = Lists.newArrayList();
        if(categoryId != null) {
            for (Category categoryItem : categorySet) {
                categoryIdlist.add(categoryItem.getId());
            }
        }
            return ServiceResponse.createBySuccessData(categoryIdlist);
    }

    /**
     * 递归遍历子节点
     * @param categorySet
     * @param categoryId
     * @return
     */
    public Set<Category> getChildDeep(Set<Category> categorySet, Integer categoryId) {
        //根据id获取他的对象
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null) {
            categorySet.add(category);
        }
        //查找子节点，递归算法一定要有一个退出条件
        List<Category> categoryList = categoryMapper.getChildCategoryList(categoryId);
        for (Category categoryItem : categoryList) {
            if (categoryItem != null) {
                getChildDeep(categorySet, categoryItem.getId());
            }
        }
        return categorySet;
    }
}
