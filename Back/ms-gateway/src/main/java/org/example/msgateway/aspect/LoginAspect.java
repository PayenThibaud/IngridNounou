package org.example.msgateway.aspect;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.msgateway.tools.RestClient;
import org.example.msgateway.utils.PortAPI;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class LoginAspect {

    @Before("execution(* org.example.msgateway.Controller.loginController.*.*(..))")
    public void testTokenJwt( ) {
        RestClient<String> testRestClient = new RestClient<>("http://localhost:"+ PortAPI.portUserJwt +"/api/test");
        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = servletRequest.getSession();
        String tokenVerif = (String) session.getAttribute("token");

        if(!testRestClient.testToken(tokenVerif, String.class)) {
            throw new RuntimeException();
        }
    }
}