package com.dida.reggie.service.impl;

import com.dida.reggie.entity.DishFlavor;
import com.dida.reggie.mapper.DishFlavorMapper;
import com.dida.reggie.service.DishFlavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishFlavorServiceImpl implements DishFlavorService {
    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    //插入数据
    public Integer save(DishFlavor flavors) {
        return dishFlavorMapper.save(flavors);
    }

    //根据dishId查询信息
    @Override
    public List<DishFlavor> selectByDishId(Long dishId) {
        return dishFlavorMapper.selectByDishId(dishId);
    }

    //根据dishId删除信息
    @Override
    public Integer deleteByDishId(Long dishId) {
        return dishFlavorMapper.deleteByDishId(dishId);
    }
}
