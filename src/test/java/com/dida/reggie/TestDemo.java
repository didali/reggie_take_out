package com.dida.reggie;

import com.dida.reggie.entity.Employee;
import com.dida.reggie.mapper.EmployeeMapper;
import com.dida.reggie.service.DishService;
import com.dida.reggie.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestDemo {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private DishService dishService;

    @Test
    public void test1() {
        Employee employee = employeeService.selectByUsername("admin");
        System.out.println(employee);
    }


}
