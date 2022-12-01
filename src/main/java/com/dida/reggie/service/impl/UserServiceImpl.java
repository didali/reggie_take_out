package com.dida.reggie.service.impl;

import com.dida.reggie.entity.User;
import com.dida.reggie.mapper.UserMapper;
import com.dida.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByPhone(String phone) {
        return userMapper.getUserByPhone(phone);
    }

    @Override
    public void save(User user) {
        userMapper.save(user);
    }
}
