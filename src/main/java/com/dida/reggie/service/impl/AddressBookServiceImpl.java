package com.dida.reggie.service.impl;

import com.dida.reggie.entity.AddressBook;
import com.dida.reggie.mapper.AddressBookMapper;
import com.dida.reggie.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    //新增地址
    @Override
    public void save(AddressBook addressBook) {
        addressBookMapper.save(addressBook);
    }

    //查询指定用户的所有地址
    @Override
    public List<AddressBook> getAddrById(Long userId) {
        return addressBookMapper.getByUserId(userId);
    }

    //根据id获取
    @Override
    public AddressBook getById(Long id) {
        return addressBookMapper.getById(id);
    }

    //设置默认地址
    @Override
    public void updateDefault(AddressBook addressBook) {
        //先将默认地址清空
        addressBookMapper.updateDefaultByUserId(addressBook);

        //再设置新的默认地址
        addressBookMapper.updateDefaultById(addressBook);
    }

    //查询默认地址
    @Override
    public AddressBook getDefault(Long userId) {
        return addressBookMapper.getDefault(userId);
    }

    @Override
    public void updateById(AddressBook addressBook) {
        addressBookMapper.updateById(addressBook);
    }
}
