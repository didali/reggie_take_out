package com.dida.reggie.service.impl;

import com.dida.reggie.common.BaseContext;
import com.dida.reggie.entity.ShoppingCart;
import com.dida.reggie.mapper.ShoppingCartMapper;
import com.dida.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class ShoppingCartControllerImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    //添加进购物车
    @Override
    public Integer add(ShoppingCart shoppingCart) {
        //设置用户id和创建时间以及个数
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
        shoppingCart.setCreateTime(LocalDateTime.now());
        shoppingCart.setNumber(1);

        log.info(shoppingCart.toString());

        //查询当前菜品是否在购物车中
        if (shoppingCartMapper.getShopping(shoppingCart) != null) {
            //在的话，就在原来的基础上加1
            return shoppingCartMapper.updateNumber(shoppingCart);
        }
        //不存在则添加进购物车
        return shoppingCartMapper.add(shoppingCart);
    }
}
