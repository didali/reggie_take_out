package com.dida.reggie.controller;


import com.dida.reggie.common.Result;
import com.dida.reggie.entity.Category;
import com.dida.reggie.entity.Page;
import com.dida.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param category
     * @return
     */
    @PostMapping
    public Result<String> save(@RequestBody Category category) {
        log.info("category:{}", category);
        categoryService.save(category);
        log.info("category:{}", category);
        return Result.success("新增分类成功");
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize) {
        log.info("page:{}, pageSize:{}", page, pageSize);
        Page<Category> pageInfo = new Page<>();
        //处理分页查询开始索引
        int pageNo = (page - 1) * pageSize;
        //进行查询
        List<Category> categoryList = categoryService.selectByPage(pageNo, pageSize);
        int counts = categoryService.getCounts();

        //封装
        pageInfo.setRecords(categoryList);
        pageInfo.setTotal(counts);

        return Result.success(pageInfo);
    }

    /**
     * 根据 ID 删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    public Result<String> deleteCategory(Long id) {
        log.info("删除分类，id为：{}", id);

        categoryService.deleteById(id);

        return Result.success("分类信息删除成功");
    }

    /**
     * 修改分类信息
     * @param category
     * @return
     */
    @PutMapping
    public Result<String> updateCategory(@RequestBody Category category) {
        log.info(category.toString());
        categoryService.update(category);
        log.info(category.toString());
        return Result.success("分类信息修改成功");
    }

    /**
     * 根据条件来查询我们的分类数据
     * @param category
     * @return
     */
    @GetMapping("/list")
    public Result<List<Category>> list(Category category) {
        log.info(category.toString());
        log.info("根据条件来查询我们的分类数据");

        List<Category> list = categoryService.getList(category);

        return Result.success(list);
    }
}
