package com.dida.reggie.service;


import com.dida.reggie.entity.Category;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CategoryService {

    //新增分类
    Integer save(Category category);

    //分页查询
    List<Category> selectByPage(int page, int pageSize);

    //获取总记录数
    Integer getCounts();

    //根据id删除
    Boolean deleteById(Long id);


    Integer update(Category category);

    //根据条件查询分类数据
    List<Category> getList(Category category);

    //根据id查询分类对象
    Category getById(Long id);
}
