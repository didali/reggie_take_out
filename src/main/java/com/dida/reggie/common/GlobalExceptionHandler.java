package com.dida.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
//@ControllerAdvice通知 annotations指定拦截哪些 controller
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 异常处理方法
     * 当抛出SQLIntegrityConstraintViolationException异常的时候，就会被拦截
     *
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        log.error(ex.getMessage());

        if (ex.getMessage().contains("Duplicate entry")) {
            //将消息根据空格进行分隔拿到数组对象
            String[] split = ex.getMessage().split(" ");
            String msg = "用户" + split[2] + "已存在";
            return Result.error(msg);
        }
        return Result.error("未知错误");
    }


    /**
     * 异常处理方法
     * 当抛出CustomException异常的时候，就会被拦截
     *
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public Result<String> exceptionHandler(CustomException ex) {
        log.error(ex.getMessage());

        return Result.error(ex.getMessage());
    }
}
