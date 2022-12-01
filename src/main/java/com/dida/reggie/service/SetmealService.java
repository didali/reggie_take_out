package com.dida.reggie.service;

import com.dida.reggie.dto.SetmealDto;
import com.dida.reggie.entity.Setmeal;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface SetmealService {

    //根据category_id查询是否有菜品
    Integer selectByCategoryId(Long categoryId);

    //新增套餐，同时插入套餐对应的菜品信息
    void saveWithDish(SetmealDto setmealDto);

    //查询记录数
    Integer count(String name);

    //分页查询
    List<Setmeal> selectByPage(int pageNo, int pageSize, String name);

    //删除套餐
    void deleteByIds(List<Long> ids);

    //查询套餐信息
    List<Setmeal> getSetmealsByStatus(Setmeal setmeal);
}
