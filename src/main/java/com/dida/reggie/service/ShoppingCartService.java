package com.dida.reggie.service;

import com.dida.reggie.entity.ShoppingCart;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ShoppingCartService {

    //添加进购物车
    Integer add(ShoppingCart shoppingCart);
}
