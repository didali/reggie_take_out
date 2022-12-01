package com.dida.reggie.service;

import com.dida.reggie.entity.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {

    //根据电话查找用户
    User getUserByPhone(String phone);

    //新增用户
    void save(User user);
}
