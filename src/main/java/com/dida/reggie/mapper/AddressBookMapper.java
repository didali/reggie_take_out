package com.dida.reggie.mapper;

import com.dida.reggie.common.AutoFill;
import com.dida.reggie.common.AutoFillConstant;
import com.dida.reggie.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    //新增地址
    @AutoFill(type = AutoFillConstant.INSERT)
    Integer save(AddressBook addressBook);

    //查询指定用户的所有地址
    List<AddressBook> getByUserId(Long userId);

    //根据id获取
    AddressBook getById(Long id);

    //修改地址信息
    @AutoFill(type = AutoFillConstant.UPDATE)
    void updateById(AddressBook addressBook);

    //清空默认地址
    @AutoFill(type = AutoFillConstant.UPDATE)
    void updateDefaultByUserId(AddressBook addressBook);

    //设置默认地址
    @AutoFill(type = AutoFillConstant.UPDATE)
    void updateDefaultById(AddressBook addressBook);

    //查询默认地址
    AddressBook getDefault(Long userId);
}
