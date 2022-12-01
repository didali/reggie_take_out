package com.dida.reggie.service;

import com.dida.reggie.entity.SetmealDish;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface SetmealDishService {

    //新增套餐菜品关系
    Integer save(SetmealDish setmealDish);

    //根据setmealIds删除套餐关联菜品
    void deleteBySetmealIds(Long setmealIds);

    //根据套餐id查询套餐信息
    List<SetmealDish> getBySetmealId(String setmealId);
}
