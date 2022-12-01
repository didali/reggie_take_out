package com.dida.reggie.mapper;

import com.dida.reggie.common.AutoFill;
import com.dida.reggie.common.AutoFillConstant;
import com.dida.reggie.dto.SetmealDto;
import com.dida.reggie.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    //新增套餐菜品关系
    @AutoFill(type = AutoFillConstant.INSERT)
    Integer save(SetmealDish setmealDish);

    //删除套餐
    void deleteBySetmealIds(String setmealIds);

    //根据套餐id查询套餐信息
    List<SetmealDish> getBySetmealId(String setmealId);
}
