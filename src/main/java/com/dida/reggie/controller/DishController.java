package com.dida.reggie.controller;

import com.dida.reggie.common.Result;
import com.dida.reggie.dto.DishDto;
import com.dida.reggie.entity.Category;
import com.dida.reggie.entity.Dish;
import com.dida.reggie.entity.DishFlavor;
import com.dida.reggie.entity.Page;
import com.dida.reggie.service.CategoryService;
import com.dida.reggie.service.DishFlavorService;
import com.dida.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增菜品
     *
     * @param dishDto
     * @return
     */
    @PostMapping
    public Result<String> save(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());

        dishService.saveWithFlavor(dishDto);

        //清理所有菜品的缓存数据

        //获取所有的以dish_为开头的key
        //Set keys = redisTemplate.keys("dish_*");
        //redisTemplate.delete(keys);

        //清理某个分类下面的菜品缓存数据
        String key = "dish_" + dishDto.getCategoryId() + "_1";
        redisTemplate.delete(key);

        return Result.success("新增菜品成功");
    }

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result<Page> selectByPage(int page, int pageSize, String name) {
        log.info("page:{}, pageSize:{}, name:{}", page, pageSize, name);
        Page<Dish> pageInfo = new Page<>();
        Page<DishDto> dishDtoPage = new Page<>();
        //处理分页请求开始索引
        int pageNo = (page - 1) * pageSize;

        List<Dish> dishList;
        int counts;
        if (name == null) { //分页查询
            dishList = dishService.selectByPage(pageNo, pageSize);
            counts = dishService.selectCounts("%%");
        } else { //分页条件查询
            name = "%" + name + "%";
            dishList = dishService.selectByPageAndCondition(pageNo, pageSize, name);
            counts = dishService.selectCounts(name);
        }

        //进行封装
        pageInfo.setTotal(counts);

        //对象拷贝,并忽略 records属性
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");
        List<DishDto> list = dishList.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            //拷贝
            BeanUtils.copyProperties(item, dishDto);
            //获取分类id
            Long categoryId = item.getCategoryId();
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            //封装
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());

        //封装
        dishDtoPage.setRecords(list);

        return Result.success(dishDtoPage);
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<DishDto> selectById(@PathVariable Long id) {
        DishDto dishDto = dishService.selectByIdWithFlavor(id);

        return Result.success(dishDto);
    }

    /**
     * 修改菜品
     *
     * @param dishDto
     * @return
     */
    @PutMapping
    public Result<String> update(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());

        dishService.updateWithFlavor(dishDto);

        //清理所有菜品的缓存数据

        //获取所有的以dish_为开头的key
        //Set keys = redisTemplate.keys("dish_*");
        //redisTemplate.delete(keys);

        //清理某个分类下面的菜品缓存数据
        String key = "dish_" + dishDto.getCategoryId() + "_1";
        redisTemplate.delete(key);
        return Result.success("修改菜品成功");
    }

    /**
     * 根据categoryId查询菜品
     *
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public Result<List<DishDto>> list(Dish dish) {
        List<DishDto> dishDtoList = null;

        //动态构造key
        String key = "dish_" + dish.getCategoryId() + "_" + dish.getStatus();

        //先从Redis中获取缓存数据，如果存在直接返回，如果不存在则查询数据库，同时将查询到的菜品数据缓存到redis
        dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);

        if (dishDtoList != null) {
            return Result.success(dishDtoList);
        }

        log.info(dish.toString());
        dishDtoList = new ArrayList<>();
        List<Dish> dishList = dishService.selectByCategoryId(dish);

        for (Dish d : dishList) {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(d, dishDto);
            //查询口味数据
            List<DishFlavor> dishFlavors = dishFlavorService.selectByDishId(d.getId());
            dishDto.setFlavors(dishFlavors);
            //添加进集合
            dishDtoList.add(dishDto);
        }

        redisTemplate.opsForValue().set(key, dishDtoList, 60, TimeUnit.MINUTES);
        return Result.success(dishDtoList);
    }
}
