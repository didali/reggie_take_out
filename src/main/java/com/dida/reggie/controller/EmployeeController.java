package com.dida.reggie.controller;

import com.dida.reggie.common.Result;
import com.dida.reggie.entity.Employee;
import com.dida.reggie.entity.Page;
import com.dida.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     *
     * @param request  登录成功之后我们需要把 employee 对象存到 session一份，这样就可以随时获取当前登录用户了
     * @param employee
     * @return
     */
    @PostMapping("login")
    public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        //1.将页面提交的代码进行md5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2.根据页面提交的用户名 username 查询数据库
        Employee emp = employeeService.selectByUsername(employee.getUsername());

        //3.如果没有查询到则返回登录失败结果
        if (emp == null) {
            return Result.error("登录失败，用户名不存在");
        }

        //4.密码比对，如果不一致则返回失败结果
        if (!emp.getPassword().equals(password)) {
            return Result.error("登录失败");
        }

        //5.查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (emp.getStatus() == 0) {
            return Result.error("账号已禁用");
        }

        //6.登录成功，将员工 id 存入 Session 并返回登录成功结果
        request.getSession().setAttribute("employee", emp.getId());
        return Result.success(emp);
    }

    /**
     * 员工退出
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        //1. 清理Session中的用户 id,防止他人盗用session中的信息
        request.getSession().removeAttribute("employee");

        //2. 返回结果
        return Result.success("退出成功");
    }

    /**
     * 新增员工
     *
     * @param employee
     * @return
     */
    @PostMapping
    public Result<String> save(@RequestBody Employee employee, HttpServletRequest request) {
        log.info("新增员工，员工信息：{}", employee.toString());

        //设置初始密码是 123456 但是需要进行 md5 加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setStatus(1);
/*
        //设置账号创建更新时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        //获得当前登录用户的 ID
        Long empId = (Long) request.getSession().getAttribute("employee");

        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);*/

        employeeService.save(employee);
        return Result.success("新增员工成功");
    }

    /**
     * 员工信息分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize, String name) {
        log.info("page = {}, pageSize = {}, name = {}", page, pageSize, name);
        Page<Employee> pageInfo = new Page<>();
        //处理分页查询开始索引
        int pageNo = (page - 1) * pageSize;
        List<Employee> employees;
        //
        int counts;
        if (name == null) { //进行分页查询
            employees = employeeService.selectByPage(pageNo, pageSize);
            counts = employeeService.selectCounts("%%");
        } else { //进行分页条件查询
            //处理一下模糊表达式
            String name1 = "%" + name + "%";
            employees = employeeService.selectByPageAndCondition(pageNo, pageSize, name1);
            counts = employeeService.selectCounts(name1);
        }
        //封装
        pageInfo.setTotal(counts);
        pageInfo.setRecords(employees);
        //返回
        return Result.success(pageInfo);
    }

    /**
     * 修改员工
     *
     * @param employee
     * @return
     */
    @PutMapping
    public Result<String> updateStatus(@RequestBody Employee employee, HttpServletRequest request) {
        log.info(employee.toString());

        //设置修改时间和用户
        /*employee.setUpdateTime(LocalDateTime.now());
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setUpdateUser(empId);*/

        employeeService.updateById(employee);
        log.info(employee.toString());
        return Result.success("员工信息修改成功");
    }

    /**
     * 根据id查询信息,回显在修改信息页面中
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id) {
        log.info("根据id查询员工信息");
        Employee employee = employeeService.selectById(id);
        if (employee != null) {
            return Result.success(employee);
        }
        return Result.error("没有查询到对应员工信息");
    }
}
