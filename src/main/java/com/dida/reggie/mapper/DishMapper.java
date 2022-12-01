package com.dida.reggie.mapper;

import com.dida.reggie.common.AutoFill;
import com.dida.reggie.common.AutoFillConstant;
import com.dida.reggie.dto.DishDto;
import com.dida.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DishMapper {

    //根据category_id查询信息
    List<Dish> selectByCategoryId(Dish dish);

    //保存
    @AutoFill(type = AutoFillConstant.INSERT)
    void save(DishDto dishDto);

    //根据名称查询ID
    Long selectIdByName(String name);

    //分页查询
    List<Dish> selectByPage(@Param("pageNo") int pageNo,@Param("pageSize") int pageSize);

    //分页条件查询
    List<Dish> selectByPageAndCondition(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("name") String name);

    //获取总记录数
    Integer selectCounts(String name);

    //根据id查询信息
    Dish selectById(Long id);

    //根据id修改数据
    @AutoFill(type = AutoFillConstant.UPDATE)
    Integer updateById(DishDto dishDto);
}
