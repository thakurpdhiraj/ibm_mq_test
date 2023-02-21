package com.dhitha.mqdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Dhiraj
 */
@Aspect
@Component
@Slf4j
@Order(1)
public class MDCConfig {

  @Around("listenerClass()")
  public Object aroundAnnotatedClass(ProceedingJoinPoint joinPoint) throws Throwable {
    try {
      Object[] args = joinPoint.getArgs();
      Object messageId = args[2];
      MDC.put("messageId", messageId.toString());
      return joinPoint.proceed();
    } catch (Throwable t) {
      log.error("Exception : ", t);
      throw t;
    } finally {
      MDC.clear();
    }
  }

  @Pointcut("@within(ListenerClass)")
  private void listenerClass() {}
}
