package com.dida.reggie.service.impl;

import com.dida.reggie.common.AutoFill;
import com.dida.reggie.common.AutoFillConstant;
import com.dida.reggie.entity.Employee;
import com.dida.reggie.mapper.EmployeeMapper;
import com.dida.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee selectEmployee(String username, String password) {
        return employeeMapper.selectEmployee(username, password);
    }


    @Override
    //登录
    public Employee selectByUsername(String username) {
        return employeeMapper.selectByUsername(username);
    }

    //新增员工
    public int save(Employee employee) {
        return employeeMapper.save(employee);
    }

    @Override
    //分页查询
    public List<Employee> selectByPage(int currentPage, int pageSize) {
        /*开始索引
        int pageNo = (currentPage - 1) * pageSize;

        List<Employee> employees = employeeMapper.selectByPage(pageNo, pageSize);
        int counts = employeeMapper.selectCounts(null);
        //封装
        Page<Employee> page =new Page<>();
        page.setTotal(counts);
        page.setRecords(employees);
        return page;*/

        return employeeMapper.selectByPage(currentPage, pageSize);
    }


    @Override
    //分页条件查询
    public List<Employee> selectByPageAndCondition(int currentPage, int pageSize, String name) {
        /*//开始索引
        int pageNo = (currentPage - 1) * pageSize;
        //处理一下模糊表达式
        String name1 = "%" + name + "%";
        List<Employee> employees = employeeMapper.selectByPageAndCondition(pageNo, pageSize, name1);
        int counts = employeeMapper.selectCounts(name1);

        //封装
        Page<Employee> page =new Page<>();
        page.setTotal(counts);
        page.setRecords(employees);
        return page*/

        return employeeMapper.selectByPageAndCondition(currentPage, pageSize, name);
    }


    @Override
    //查询总条目数
    public Integer selectCounts(String name) {
        return employeeMapper.selectCounts(name);
    }

    @Override
    //根据Id进行修改
    public Integer updateById(Employee employee) {
        return employeeMapper.updateById(employee);
    }

    @Override
    public Employee selectById(Long id) {
        return employeeMapper.selectById(id);
    }
}
