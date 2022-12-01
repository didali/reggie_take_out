package com.dida.reggie.service.impl;

import com.dida.reggie.dto.DishDto;
import com.dida.reggie.entity.Dish;
import com.dida.reggie.entity.DishFlavor;
import com.dida.reggie.mapper.DishMapper;
import com.dida.reggie.service.DishFlavorService;
import com.dida.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorService dishFlavorService;

    //根据category_id查询是否有菜品
    @Override
    public List<Dish> selectByCategoryId(Dish dish) {
        log.info("查询categoryId为：{} 对应的菜品", dish);
        return dishMapper.selectByCategoryId(dish);
    }

    //新增菜品，同时保存对应的口味数据，需要操作两张表：dish、dish_flavor
    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //保存基本信息到菜品表
        dishMapper.save(dishDto);

        //获取菜品id
        Long id = dishMapper.selectIdByName(dishDto.getName());
        log.info("dishID:{}", id);

        //保存菜品口味数据到菜品口味表dish_flavor
        for (DishFlavor flavor : dishDto.getFlavors()) {
            flavor.setDishId(id);
            dishFlavorService.save(flavor);
        }
    }

    //分页查询菜品
    @Override
    public List<Dish> selectByPage(int pageNo, int pageSize) {
        return dishMapper.selectByPage(pageNo, pageSize);
    }

    //分页条件查询
    @Override
    public List<Dish> selectByPageAndCondition(int pageNo, int pageSize, String name) {
        return dishMapper.selectByPageAndCondition(pageNo, pageSize, name);
    }

    //获取总记录数
    @Override
    public Integer selectCounts(String name) {
        return dishMapper.selectCounts(name);
    }

    //根据id查询菜品信息和对应的口味信息
    @Override
    public DishDto selectByIdWithFlavor(Long id) {
        //查询菜品基本信息
        Dish dish = dishMapper.selectById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);
        //查询当前菜品对应的口味信息
        List<DishFlavor> dishFlavors = dishFlavorService.selectByDishId(id);

        dishDto.setFlavors(dishFlavors);

        return dishDto;
    }

    //根据id修改
    @Override
    public Integer updateById(DishDto dishDto) {
        return dishMapper.updateById(dishDto);
    }

    //更新菜品信息,同时更新口味信息
    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish表
        this.updateById(dishDto);

        //更新dish_flavor表
        //先清理当前菜品对应口味数据
        Long id = dishDto.getId();
        dishFlavorService.deleteByDishId(id);
        //添加当前提交过来的新菜品
        //保存菜品口味数据到菜品口味表dish_flavor
        for (DishFlavor flavor : dishDto.getFlavors()) {
            flavor.setDishId(id);
            dishFlavorService.save(flavor);
        }
    }
}
