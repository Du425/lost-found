package com.du.lostfoundmasterfk.config.handler;

import com.alibaba.fastjson.JSON;
import com.du.lostfoundmasterfk.core.reponse.CommonReturnType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomizeAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        CommonReturnType returnType = CommonReturnType.fail(e.getMessage());
        httpServletResponse.setContentType("text/json; character=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(returnType));
    }
}
