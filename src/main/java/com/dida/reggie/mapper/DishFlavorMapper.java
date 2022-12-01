package com.dida.reggie.mapper;

import com.dida.reggie.common.AutoFill;
import com.dida.reggie.common.AutoFillConstant;
import com.dida.reggie.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface DishFlavorMapper {

    //插入数据
    @AutoFill(type = AutoFillConstant.INSERT)
    Integer save(DishFlavor flavors);

    //根据dish_id查询口味信息
    List<DishFlavor> selectByDishId(Long dishId);

    //根据dish_id删除对应信息
    Integer deleteByDishId(Long dishId);
}
