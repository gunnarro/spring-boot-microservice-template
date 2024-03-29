package org.gunnarro.microservice.mymicroservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.util.StopWatch.TaskInfo;
import org.springframework.web.client.HttpClientErrorException;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.util.StopWatch.TaskInfo;
import org.springframework.web.client.HttpClientErrorException;

/**
 * TODO Remove unused profiling paths
 * Aspect used for performance logging.
 *
 * @see <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop">spring doc core</a>
 *
 */
@Aspect
@Component
@Slf4j
public class MethodProfilerAspect {

    @Around("execution(* org.gunnarro.microservice.todoservice.adapter.impl.*.*(..))")
    public Object profileAdapters(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return profileMethodeCall(proceedingJoinPoint);
    }

    @Around("execution(* org.gunnarro.microservice.todoservice.rest.RestClient.*(..))")
    public Object profileRestClient(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return profileMethodeCall(proceedingJoinPoint);
    }

    @Around("execution(* org.gunnarro.microservice.todoservice.service.impl.*.*(..))")
    public Object profileServices(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return profileMethodeCall(proceedingJoinPoint);
    }

    @Around("execution(* org.gunnarro.microservice.todoservice.endpoint.*.*(..))")
    public Object profileEndpoint(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return profileMethodeCall(proceedingJoinPoint);
    }

    private Object profileMethodeCall(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start(proceedingJoinPoint.toShortString());
        String exceptionMsg = null;
        try {
            // execute the profiled method
            return proceedingJoinPoint.proceed();
        } catch (RuntimeException exception) {
            exceptionMsg = getExceptionErrorMsg(exception);
            throw exception;
        } finally {
            stopWatch.stop();
            TaskInfo taskInfo = stopWatch.lastTaskInfo();
            String taskName = taskInfo.getTaskName().replace("execution(", "").replace("(..))", "");
            String msg = String.format("%s", exceptionMsg != null ? "exception=\"" + exceptionMsg + "\"" : "");
            log.info("method=\"{}\" time=\"{} ms\" {}", taskName, taskInfo.getTimeMillis(), msg);
        }
    }

    private String getExceptionErrorMsg(RuntimeException exception) {
        if (exception instanceof HttpClientErrorException httpClientErrorException) {
            return String.format("%s, %s", httpClientErrorException.getStatusCode().value(), httpClientErrorException.getResponseBodyAsString());
        } else {
            return exception.getMessage();
        }
    }
}
