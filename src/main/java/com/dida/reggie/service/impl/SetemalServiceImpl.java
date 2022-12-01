package com.dida.reggie.service.impl;

import com.dida.reggie.common.CustomException;
import com.dida.reggie.dto.SetmealDto;
import com.dida.reggie.entity.Setmeal;
import com.dida.reggie.entity.SetmealDish;
import com.dida.reggie.mapper.SetmealMapper;
import com.dida.reggie.service.DishService;
import com.dida.reggie.service.SetmealDishService;
import com.dida.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SetemalServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishService setmealDishService;

    //根据categoryId查询信息
    @Override
    public Integer selectByCategoryId(Long categoryId) {
        log.info("查询categoryId为：{} 对应的套餐数", categoryId);
        return setmealMapper.selectByCategoryId(categoryId);
    }

    //新增套餐，同时插入套餐对应的菜品信息
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        //新增套餐信息
        setmealMapper.save(setmealDto);

        //新增套餐绑定菜品信息
        //获取套餐id和菜品id
        Long setmealId = setmealMapper.selectByName(setmealDto.getName());

        for (SetmealDish setmealDish : setmealDto.getSetmealDishes()) {
            setmealDish.setSetmealId(setmealId);
            setmealDishService.save(setmealDish);
        }
    }

    //获取记录数
    @Override
    public Integer count(String name) {
        return setmealMapper.count(name);
    }

    //分页查询
    @Override
    public List<Setmeal> selectByPage(int page, int pageSize, String name) {
        int pageNo = (page - 1) * pageSize;
        List<Setmeal> setmeals = setmealMapper.selectByPage(pageNo, pageSize, name);
        return setmeals;
    }

    //删除套餐
    @Override
    @Transactional
    public void deleteByIds(List<Long> ids) {
        log.info("ids:{}", ids);
        for (Long id: ids) {
            //判断该数据能否删除
            Setmeal setmeal = setmealMapper.selectById(id);
            //判断售卖状态
            if (setmeal.getStatus() > 0) {
                throw new CustomException("套餐正在售卖中，无法删除");
            } else {
                //删除套餐数据
                setmealMapper.deleteByIds(id);
                //删除套餐关联菜品数据
                setmealDishService.deleteBySetmealIds(id);
            }
        }
    }

    //查询在售套餐信息
    @Override
    public List<Setmeal> getSetmealsByStatus(Setmeal setmeal) {
        return setmealMapper.getByStatus(setmeal);
    }
}
