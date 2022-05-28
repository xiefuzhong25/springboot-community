package com.xiefuzhong.community.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 方面组件
 */
//@Component
//@Aspect
public class AtestAspect {

    @Pointcut("execution(* com.xiefuzhong.community.service.*.*(..))")
    public void pointcut() {
            //该方法定义切点
    }

    @Before("pointcut()")
    public void before() {
        System.out.println("before");
    }

    @After("pointcut()")
    public void after() {
        System.out.println("after");
    }

    @AfterReturning("pointcut()")
    public void afterRetuning() {
        System.out.println("afterRetuning");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        System.out.println("afterThrowing");
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around before");
        //调用目标原始对象的方法
        Object obj = joinPoint.proceed();
        System.out.println("around after");
        return obj;
    }

}
