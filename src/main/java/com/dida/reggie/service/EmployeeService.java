package com.dida.reggie.service;

import com.dida.reggie.entity.Employee;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface EmployeeService {

    Employee selectEmployee(String username, String password);

    //登录
    Employee selectByUsername(String username);

    //新增员工
    int save(Employee employee);

    //分页查询
    List<Employee> selectByPage(int currentPage, int pageSize);

    //分页条件查询
    List<Employee> selectByPageAndCondition(int currentPage, int pageSize, String name);

    //查询总条目数
    Integer selectCounts(String name);

    //根据ID进行修改
    Integer updateById(Employee employee);

    //根据ID查询员工信息
    Employee selectById(Long id);
}
