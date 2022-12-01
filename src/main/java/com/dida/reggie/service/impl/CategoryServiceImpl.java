package com.dida.reggie.service.impl;

import com.dida.reggie.common.CustomException;
import com.dida.reggie.entity.Category;
import com.dida.reggie.entity.Dish;
import com.dida.reggie.mapper.CategoryMapper;
import com.dida.reggie.service.CategoryService;
import com.dida.reggie.service.DishService;
import com.dida.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    //新增分类
    @Override
    public Integer save(Category category) {
        return categoryMapper.save(category);
    }

    //分页查询
    @Override
    public List<Category> selectByPage(int page, int pageSize) {
        return categoryMapper.selectByPage(page, pageSize);
    }

    //获取总记录数
    @Override
    public Integer getCounts() {
        return categoryMapper.getCounts();
    }

    //根据id删除分类，删除之前先判断当前分类是否已经关联了相应的菜品或者套餐
    @Override
    public Boolean deleteById(Long id) {
        //查询当前分类是否已经关联了菜品，如果已经关联，抛出一个业务异常
        Dish dish = new Dish();
        dish.setCategoryId(id);
        List<Dish> dishList = dishService.selectByCategoryId(dish);
        if (dishList != null) {
            //已经关联菜品，抛出一个业务异常
            throw new CustomException("当前分类项关联了菜品，不能删除");
        }

        //查询当前分类是否已经关联了套餐，如果已经关联，抛出一个业务异常
        int count2 = setmealService.selectByCategoryId(id);
        if (count2 > 0) {
            //已经关联了套餐，抛出一个业务异常
            throw new CustomException("当前分类项关联了菜品，不能删除");
        }

        //正常删除分类
        return categoryMapper.deleteById(id);
    }

    //修改分类信息
    @Override
    public Integer update(Category category) {
        return categoryMapper.update(category);
    }

    //根据条件查询分类数据
    @Override
    public List<Category> getList(Category category) {
        return categoryMapper.getList(category);
    }

    @Override
    public Category getById(Long id) {
        return categoryMapper.selectById(id);
    }
}
