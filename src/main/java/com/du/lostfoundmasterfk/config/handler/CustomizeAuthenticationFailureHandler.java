package com.du.lostfoundmasterfk.config.handler;

import com.alibaba.fastjson.JSON;
import com.du.lostfoundmasterfk.core.reponse.CommonReturnType;
import com.du.lostfoundmasterfk.validate.core.ValidateCodeException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.stereotype.Component;


import javax.security.auth.login.CredentialExpiredException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * @Author: Hutengfei
 * @Description: 登录失败处理逻辑
 * @Date Create in 2019/9/3 15:52
 */
@Component
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        CommonReturnType returnType = null;
        if (e instanceof AccountExpiredException){
            returnType = CommonReturnType.fail("账号过期");
        }else if (e instanceof BadCredentialsException){
            returnType = CommonReturnType.fail(e.getMessage());
        }else if (e instanceof CredentialsExpiredException){
            returnType = CommonReturnType.fail("密码过期");
        }else if (e instanceof DisabledException) {
            //账号不可用
            returnType = CommonReturnType.fail("账号被禁用");
        } else if (e instanceof LockedException) {
            //账号锁定
            returnType = CommonReturnType.fail("账号锁定");
        } else if(e instanceof NonceExpiredException) {
            //异地登录
            returnType = CommonReturnType.fail("异地登录");
        } else if(e instanceof SessionAuthenticationException) {
            //session异常
            returnType = CommonReturnType.fail("session错误");
        } else if(e instanceof ValidateCodeException) {
            //验证码异常
            returnType = CommonReturnType.fail(e.getMessage());
        } else {
            //其他未知异常
            returnType = CommonReturnType.fail(e.getMessage());
        }
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(returnType));
    }
}
