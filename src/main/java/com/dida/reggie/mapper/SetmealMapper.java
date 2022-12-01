package com.dida.reggie.mapper;

import com.dida.reggie.common.AutoFill;
import com.dida.reggie.common.AutoFillConstant;
import com.dida.reggie.dto.SetmealDto;
import com.dida.reggie.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface SetmealMapper {

    //根据category_id查询是否有套餐
    Integer selectByCategoryId(Long categoryId);

    //新增套餐
    @AutoFill(type = AutoFillConstant.INSERT)
    Integer save(SetmealDto setmealDto);

    //根据套餐名称查询套餐id
    Long selectByName(String name);

    //查询总记录数
    Integer count(String name);

    //分页查询
    List<Setmeal> selectByPage(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("name") String name);

    //根据id删除套餐
    void deleteByIds(Long id);

    //根据套餐id查询套餐信息
    Setmeal selectById(Long id);

    //查询在售套餐信息
    List<Setmeal> getByStatus(Setmeal setmeal);
}
