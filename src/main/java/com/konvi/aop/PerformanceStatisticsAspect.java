package com.konvi.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect

@Component

@Slf4j

public class PerformanceStatisticsAspect
{
    /**
     * 作为环绕通知:
      方法需要返回一个Object类型的返回值
      方法需要一个参数org.aspectj.lang.ProceedingJoinPoint
      需要抛出一个Throwable的异常
      在环绕通知中永远不要对业务进行try...catch, 实在想try...catch的话记得一定要把异常再抛出去
     */

    // @Around()
    public Object timer(ProceedingJoinPoint joinPoint) throws Throwable
    {
        System.out.println("timer begin...");

        long startTime = System.currentTimeMillis();

        Signature signature = joinPoint.getSignature();

        String methodName = signature.getName();

        Object returnValue = null;

        returnValue = joinPoint.proceed(); //调用执行具体业务方法

        long endTime = System.currentTimeMillis();

        System.out.println("timer end...");

        System.out.println(methodName + "cost:" + (endTime - startTime) + "毫秒");

        return returnValue;
    }

}
