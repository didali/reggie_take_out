package com.dida.reggie.service;

import com.dida.reggie.dto.DishDto;
import com.dida.reggie.entity.Dish;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DishService {

    //根据category_id查询是否有菜品
    List<Dish> selectByCategoryId(Dish dish);

    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish、dish_flavor
    void saveWithFlavor(DishDto dishDto);

    //分页查询菜品
    List<Dish> selectByPage(int pageNo, int pageSize);

    //分页条件查询
    List<Dish> selectByPageAndCondition(int pageNo, int pageSize, String name);

    //获取总记录数
    Integer selectCounts(String name);

    //根据id查询菜品信息和对应的口味信息
    DishDto selectByIdWithFlavor(Long id);

    //根据id修改
    Integer updateById(DishDto dishDto);

    //更新菜品信息,同时更新口味信息
    void updateWithFlavor(DishDto dishDto);


}
