package com.dida.reggie.controller;

import com.dida.reggie.common.Result;
import com.dida.reggie.dto.SetmealDto;
import com.dida.reggie.entity.Category;
import com.dida.reggie.entity.Page;
import com.dida.reggie.entity.Setmeal;
import com.dida.reggie.service.CategoryService;
import com.dida.reggie.service.SetmealDishService;
import com.dida.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增套餐
     *
     * @param setmealDto
     * @return
     */
    @PostMapping
    @CacheEvict(value = "setmealCache", allEntries = true) //allEntries = true表示我们要清理setmealCache下面所有的缓存
    public Result<String> saveSetmeal(@RequestBody SetmealDto setmealDto) {
        log.info("{}", setmealDto);
        setmealService.saveWithDish(setmealDto);
        return Result.success("添加套餐成功");
    }


    /**
     * 套餐信息分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize, String name) {
        //Page<Setmeal> setmealPage = new Page<>();
        Page<SetmealDto> setmealDtoPage = new Page<>();

        //根据name的值进行对name进行模糊查询处理
        if (name != null) {
            name = "%" + name + "%";
        } else {
            name = "%%";
        }
        //对setmeal表进行查询
        List<Setmeal> setmeals = setmealService.selectByPage(page, pageSize, name);
        Integer count = setmealService.count(name);

        List<SetmealDto> setmealDtos = new ArrayList<>();
        for (Setmeal s : setmeals) {
            SetmealDto setmealDto = new SetmealDto();
            //拷贝
            BeanUtils.copyProperties(s, setmealDto);
            //获取分类id
            Long categoryId = s.getCategoryId();
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            //封装
            if (category != null) {
                //获取分类名
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            //添加进list集合
            setmealDtos.add(setmealDto);
        }
        //将内容封装到setmealDtoPage中
        setmealDtoPage.setTotal(count);
        setmealDtoPage.setRecords(setmealDtos);

        return Result.success(setmealDtoPage);
    }

    /**
     * 删除套餐
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    @CacheEvict(value = "setmealCache", allEntries = true) //allEntries = true表示我们要清理setmealCache下面所有的缓存
    public Result<String> delete(@RequestParam List<Long> ids) {
        setmealService.deleteByIds(ids);
        return Result.success("删除成功");
    }

    //查询在售套餐
    @GetMapping("/list")
    @Cacheable(value = "setmealCache", key = "#setmeal.categoryId + '_' +#setmeal.status")
    public Result<List> list(Setmeal setmeal) {
        log.info(setmeal.toString());
        List<Setmeal> setmealsByStatus = setmealService.getSetmealsByStatus(setmeal);

        return Result.success(setmealsByStatus);
    }
}
