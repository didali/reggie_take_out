package com.dida.reggie.service;

import com.dida.reggie.entity.AddressBook;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface AddressBookService {

    //新增地址
    void save(AddressBook addressBook);

    //查询指定用户的所有地址
    List<AddressBook> getAddrById(Long userId);

    //根据id获取
    AddressBook getById(Long id);

    //设置默认地址
    void updateDefault(AddressBook addressBook);

    //查询默认地址
    AddressBook getDefault(Long userId);

    //修改地址信息
    void updateById(AddressBook addressBook);
}
