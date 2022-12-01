package com.dida.reggie.mapper;

import com.dida.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    //根据电话查找用户
    User getUserByPhone(String phone);

    //新增用户
    void save(User user);
}
