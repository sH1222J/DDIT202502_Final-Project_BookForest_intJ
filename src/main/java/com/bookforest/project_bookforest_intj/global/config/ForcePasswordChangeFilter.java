package com.bookforest.project_bookforest_intj.global.config;

import com.bookforest.project_bookforest_intj.user.service.CustomUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ForcePasswordChangeFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();

        // 비밀번호 변경 페이지, 로그아웃, 정적 자원 요청은 필터를 통과시킵니다.
        if (requestUri.equals("/user/change-password") ||
            requestUri.equals("/user/logout") ||
            requestUri.startsWith("/css/") ||
            requestUri.startsWith("/js/") ||
            requestUri.startsWith("/images/") ||
            requestUri.equals("/favicon.ico")) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof CustomUser) {
            CustomUser customUser = (CustomUser) authentication.getPrincipal();
            if (customUser.getRegisterReqDto().getMustChangePassword() != null && customUser.getRegisterReqDto().getMustChangePassword()) {
                // 비밀번호 변경이 필요한 경우, 비밀번호 변경 페이지로 리디렉션
                response.sendRedirect(request.getContextPath() + "/user/change-password");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
