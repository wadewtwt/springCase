package com.example.springCase.auth;

import com.alibaba.fastjson.JSON;
import com.example.springCase.annotation.IgnoreAuth;
import com.example.springCase.bean.constant.error.ErrorEnum;
import com.example.springCase.bean.constant.RedisConstant;
import com.example.springCase.exception.BizException;
import com.example.springCase.util.JwtUtil;

import com.example.springCase.util.RedisUtil;
import com.rjhy.base.bean.dto.ApiResult;
import com.rjhy.base.error.ServiceException;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tao.wu
 * @date 2022/4/16
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求头的参数
        String token = request.getHeader("Authorization");
        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //当前的方法是否包含Login注解
        if (method.isAnnotationPresent(IgnoreAuth.class)){
            return true;
        }
        // 增加这个，防止登录时抛异常再次以exception进入
        if ("error".equals(method.getName())){
            return true;
        }
//        Login login = method.getAnnotation(Login.class);
//        if (!Objects.isNull(login) && !login.required()){
//            return false;
//        }
        //验证token

        try{
            if (!checkToken(token)){
                //token有误
                ApiResult res = new ApiResult();
                res.setCode(-1);
                res.setMessage("请重新登录");
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().append(JSON.toJSONString(res));
                return false;
            }
        }catch (Exception e){
            ApiResult res = new ApiResult();
            res.setCode(-1);
            res.setMessage(e.getMessage());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().append(JSON.toJSONString(res));
            return false;
        }
        String userName = JwtUtil.getUserName(token);

        Map<String,Object> currentUser = new HashMap<>();
        currentUser.put("userName",userName);
        request.setAttribute("currentUser",currentUser);
        return true;
    }

    private boolean checkToken(String token){
        // 执行认证
        if (null == token) {
            throw  new BizException(ErrorEnum.ERROR_TOKEN_NOT_EXiST.code(), ErrorEnum.ERROR_TOKEN_NOT_EXiST.message());
        }
        if (!token.startsWith("Bearer ")) {
            throw  new BizException(ErrorEnum.ERROR_TOKEN_FORMAT.code(), ErrorEnum.ERROR_TOKEN_FORMAT.message());

        }
        String userName = JwtUtil.getUserName(token);
        if (StringUtil.isNullOrEmpty(userName)){
            return false;
        }
        //获取redis中的token信息
        String tokenInRedis = (String) redisUtil.get(String.format(RedisConstant.CACHE_JWT_KEY,userName));
        if (StringUtil.isNullOrEmpty(tokenInRedis)){
            //如果redis挂了可以保证正常执行
            return true;
        }
        long expireIn = JwtUtil.getExpireIn(token);

        String userNameInRedis = JwtUtil.getUserName(tokenInRedis);
        long expireInRedis = JwtUtil.getExpireIn(tokenInRedis);

        if (StringUtil.isNullOrEmpty(userNameInRedis)){
            return false;
        }
        //判断token是否过期
        if (expireIn != expireInRedis){
            return false;
        }
        //判断token过期时间
        return expireIn >= System.currentTimeMillis();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
