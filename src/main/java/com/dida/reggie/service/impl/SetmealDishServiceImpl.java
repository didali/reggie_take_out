package com.dida.reggie.service.impl;

import com.dida.reggie.entity.SetmealDish;
import com.dida.reggie.mapper.SetmealDishMapper;
import com.dida.reggie.service.SetmealDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SetmealDishServiceImpl implements SetmealDishService {

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    //新增套餐菜品关系
    @Override
    public Integer save(SetmealDish setmealDish) {
        return setmealDishMapper.save(setmealDish);
    }

    @Override
    public void deleteBySetmealIds(Long setmealIds) {
        String id = setmealIds.toString();
        setmealDishMapper.deleteBySetmealIds(id);
    }

    //根据套餐id查询套餐信息
    @Override
    public List<SetmealDish> getBySetmealId(String setmealId) {
        return setmealDishMapper.getBySetmealId(setmealId);
    }
}
