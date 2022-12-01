package com.dida.reggie.mapper;

import com.dida.reggie.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingCartMapper {

    //添加进购物车
    Integer add(ShoppingCart shoppingCart);

    //查询购物车中是否存在某个商品
    ShoppingCart getShopping(ShoppingCart shoppingCart);

    //将购物车中该商品数目加1
    Integer updateNumber(ShoppingCart shoppingCart);
}
