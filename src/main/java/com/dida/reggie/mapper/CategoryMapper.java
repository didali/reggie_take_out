package com.dida.reggie.mapper;

import com.dida.reggie.common.AutoFill;
import com.dida.reggie.common.AutoFillConstant;
import com.dida.reggie.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {

    //新增分类
    @AutoFill(type = AutoFillConstant.INSERT)
    Integer save(Category category);

    //分页查询
    List<Category> selectByPage(@Param("page") int page, @Param("pageSize") int pageSize);

    //获取总记录数
    Integer getCounts();

    //根据id删除
    Boolean deleteById(Long id);

    //修改分类信息
    @AutoFill(type = AutoFillConstant.UPDATE)
    Integer update(Category category);

    //根据条件来查询分类数据
    List<Category> getList(Category category);

    //根据id查询分类对象
    Category selectById(Long id);
}
