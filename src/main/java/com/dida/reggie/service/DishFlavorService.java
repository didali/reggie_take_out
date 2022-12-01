package com.dida.reggie.service;

import com.dida.reggie.entity.DishFlavor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public interface DishFlavorService {

    //插入数据
    Integer save(DishFlavor flavors);

    //根据dishId查询信息
    List<DishFlavor> selectByDishId(Long dishId);

    //根据dishId删除信息
    Integer deleteByDishId(Long dishId);
}
