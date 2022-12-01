package com.dida.reggie.mapper;

import com.dida.reggie.common.AutoFill;
import com.dida.reggie.common.AutoFillConstant;
import com.dida.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    Employee selectEmployee(@Param("username") String username, @Param("password") String password);

    //根据用户名查询用户，用于登录
    Employee selectByUsername(String username);

    //添加
    @AutoFill(type = AutoFillConstant.INSERT)
    int save(Employee employee);

    //总记录数
    Integer selectCounts(String name);

    //分页查询
    List<Employee> selectByPage(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

    //分页条件查询
    List<Employee> selectByPageAndCondition(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("name") String name);

    //根据ID进行修改
    @AutoFill(type = AutoFillConstant.UPDATE)
    Integer updateById(Employee employee);

    //根据ID查询员工信息
    Employee selectById(Long id);
}
