package com.example.springCase.aop;

import com.example.springCase.annotation.OperateLog;
import com.example.springCase.bean.dto.LogInsertDTO;
import com.example.springCase.service.LogService;
import com.example.springCase.util.JwtUtil;
import com.rjhy.base.bean.dto.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;


import java.lang.reflect.Method;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;

/**
 * @author tao.wu
 * @date 2022/4/20
 * 借鉴https://blog.csdn.net/qq_42570879/article/details/108830089
 */
@Aspect
@Component
@Slf4j
public class OperateLogAspect {
    @Autowired
    private LogService logService;

    @Pointcut("@annotation(com.example.springCase.annotation.OperateLog)")
    public void operateLogPoinCut() {
    }

    @AfterReturning(returning = "result", value = "operateLogPoinCut()")
    public void saveOperLog(JoinPoint joinPoint, Object result) throws Throwable {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(requestAttributes)){
            return;
        }
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        if (Objects.isNull(request)){
            return;
        }
        try {
            //将返回值转换成apiResult集合
            ApiResult apiResult = (ApiResult) result;

            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取切入点所在的方法
            Method method = signature.getMethod();
            //获取操作
            OperateLog annotation = method.getAnnotation(OperateLog.class);
            String ipAddress = "";
            String username = "";
            String content = "";
            if (annotation != null) {
                /**
                 *  可以这样提前在控制器设置属性HttpServletRequest request
                 *   request.setAttribute("username", "fdfd");
                 *    request.getAttribute("username")
                 */

                // 获取ip
                ipAddress = request.getRemoteAddr();
                // 用户名
                String authorization = (String) request.getHeader("Authorization");
                username = JwtUtil.getUserName(authorization);
                // 内容
                content = annotation.content();

                log.info("OperateLogAspect log content:{}, ip:{}, resultMessage:{}",content, ipAddress, apiResult.getMessage() );

                LogInsertDTO logInsertDTO = new LogInsertDTO();
                logInsertDTO.setAddress(ipAddress);
                logInsertDTO.setUsername(username);
                logInsertDTO.setContent(content);
                logService.insertLog(logInsertDTO);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
