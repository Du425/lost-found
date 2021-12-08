package com.du.lostfoundmasterfk.config.session;

import com.alibaba.fastjson.JSON;
import com.du.lostfoundmasterfk.core.reponse.CommonReturnType;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomizeSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {
        //失效则踢出session
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        String sessionId = sessionInformationExpiredEvent.getRequest().getRequestedSessionId();
        sessionRegistry.getSessionInformation(sessionId).expireNow();
        CommonReturnType returnType = CommonReturnType.fail("您的账号已经在别的地方登录，当前登录已失效。如果密码遭到泄露，请立即修改密码！");
        HttpServletResponse response = sessionInformationExpiredEvent.getResponse();
        response.setContentType("text/json;character=utf-8");
        response.getWriter().write(JSON.toJSONString(returnType));

    }
}
