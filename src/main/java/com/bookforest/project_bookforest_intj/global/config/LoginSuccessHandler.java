package com.bookforest.project_bookforest_intj.global.config;

import com.bookforest.project_bookforest_intj.user.service.CustomUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        if (customUser.getRegisterReqDto().getMustChangePassword() != null && customUser.getRegisterReqDto().getMustChangePassword()) {
            response.sendRedirect("/user/change-password");
        } else {
            response.sendRedirect("/");
        }
    }
}
