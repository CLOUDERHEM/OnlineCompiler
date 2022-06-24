package com.lc.compiler.util.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lc.compiler.model.RequestLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * 使用aop对接口调用进行日志记录
 * profile=dev 才进行调用
 *
 * @author Aaron Yeung
 * @date 4/9/2022 10:37 PM
 */
@Slf4j
@Aspect
@Component
@Profile("dev")
public class ApiLogImpl {

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Pointcut("@annotation(com.lc.compiler.util.log.ApiLog)")
    public void requestLog() {
    }

    @Before("requestLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();

        String methodDescription = getAspectLogDescription(joinPoint);

        StringBuilder sb = new StringBuilder();
        for (Object arg : joinPoint.getArgs()) {
            // 有些obj不能序列化
            if (arg instanceof HttpServletRequest
                    || arg instanceof HttpServletResponse
                    || arg instanceof MultipartFile
                    || arg instanceof HttpSession) {
                continue;
            }
            sb.append(objectMapper.writeValueAsString(arg));
        }

        RequestLog webLog = RequestLog.builder()
                .url(request.getRequestURL().toString())
                .description(methodDescription)
                .httpMethod(request.getMethod())
                .classMethod(String.format("%s.%s", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName()))
                .ip(request.getRemoteAddr())
                .requestArgs(sb.toString()).build();

        log.info("========================================== Start ==========================================");
        log.info("URL            : {}", webLog.getUrl());
        log.info("Description    : {}", webLog.getDescription());
        log.info("HTTP Method    : {}", webLog.getHttpMethod());
        log.info("Class Method   : {}", webLog.getClassMethod());
        log.info("IP             : {}", webLog.getIp());
        log.info("Request Args   : {}", webLog.getRequestArgs());
    }


    @After("requestLog()")
    public void doAfter() {

    }


    @Around("requestLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();

        log.info("Response       : {}", new ObjectMapper().writeValueAsString(result));
        log.info("Time-Consuming : {} ms", System.currentTimeMillis() - startTime);

        log.info("=========================================== End ===========================================" + LINE_SEPARATOR);
        return result;
    }

    public String getAspectLogDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class<?> targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        StringBuilder description = new StringBuilder("");
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class<?>[] clazz = method.getParameterTypes();
                if (clazz.length == arguments.length) {
                    description.append(method.getAnnotation(ApiLog.class).value());
                    break;
                }
            }
        }
        return description.toString();
    }
}
