package com.dida.reggie.common.aop;

import com.dida.reggie.common.BaseContext;
import com.dida.reggie.common.AutoFill;
import com.dida.reggie.common.AutoFillConstant;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自动填充的相关逻辑的切面类
 *      统一为公共字段赋值
 */
@Component
@Slf4j
@Aspect
public class AutoFillAspect {

    /**
     * 切入点
     */
    @Pointcut("execution(* com.dida.reggie.mapper.*.*(..)) && @annotation(com.dida.reggie.common.AutoFill)")
    public void autoFillPointCut() {
    }

    /**
     * 通知 自动填充公共字段
     */
    @Before("autoFillPointCut()")
    public void autoFillAdvice(JoinPoint joinPoint) throws Throwable {
        log.info("公共字段自动填充");
        //获得方法签名对象
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获得方法上的注解
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        //获得注解中 type 的值
        String type = autoFill.type();

        //获取当前目标方法的参数
        Object[] args = joinPoint.getArgs();
        //数值满足下面的条件时，说明没有参数直接结束
        if (args == null || args.length == 0) {
            return;
        }

        //实体对象
        Object entity = args[0];
        //HttpServletRequest request = (HttpServletRequest)args[1];

        //准备赋值的数据
        LocalDateTime time = LocalDateTime.now();
        //Long empId = (Long) request.getSession().getAttribute("employee");
        Long empId = BaseContext.getCurrentId();
        if (type.equals(AutoFillConstant.INSERT)) {
            //执行insert
            try {
                //获得set方法对象：Method
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                //通过反射调用目标对象的方法
                setCreateTime.invoke(entity, time);
                setUpdateTime.invoke(entity, time);
                setCreateUser.invoke(entity, empId);
                setUpdateUser.invoke(entity, empId);
            } catch (Exception e) {
                log.error("公共字段填充失败：{}",e.getMessage());
            }
        } else if (type.equals(AutoFillConstant.UPDATE)) {
            //执行update
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                setUpdateTime.invoke(entity, time);
                setUpdateUser.invoke(entity, empId);
            } catch (Exception e) {
                log.error("公共字段填充失败：{}",e.getMessage());
            }
        }
    }
}
