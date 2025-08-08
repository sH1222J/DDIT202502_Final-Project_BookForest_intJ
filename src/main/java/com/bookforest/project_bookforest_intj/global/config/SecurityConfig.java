package com.bookforest.project_bookforest_intj.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private ForcePasswordChangeFilter forcePasswordChangeFilter;

    //5. 패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /*
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /* 기존 설정 주석 처리
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
                .requestMatchers("/**", "/user/**", "/book/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/user/login") // Custom login page
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            );
        */

        // 새 설정 적용
        http.csrf(csrf->csrf.disable()).httpBasic(hbasic->hbasic.disable())
            .authorizeHttpRequests(authorize -> authorize
                // 정적 자원, 메인, 회원가입, 로그인, 아이디/비밀번호 찾기 등은 모두에게 허용
                .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/favicon.ico", 
                                 "/user/register", "/user/login", "/user/find-id", "/user/forgot-password", 
                                 "/user/reset-password", "/user/forgot-password-confirmation", "/user/change-password").permitAll()
                // 도서 목록, 도서 상세, 고객센터 조회는 모두에게 허용
                .requestMatchers("/book/**", "/customer-service", "/qna/list").permitAll()
                // 1:1 문의 작성 및 마이페이지 관련 기능은 인증된 사용자만 접근 가능
                .requestMatchers("/qna/question", "/mypage/**").authenticated()
                // 그 외 모든 요청은 일단 허용 (필요에 따라 변경)
                .anyRequest().permitAll()
            )
            .formLogin(formLogin -> formLogin
                // 사용자 정의 로그인 페이지
                .loginPage("/user/login")
                // 로그인 성공 시 이동할 기본 URL
                .successHandler(loginSuccessHandler)
                // 로그인 실패 시 이동할 URL
                .failureUrl("/user/login?error=true")
                // 로그인 페이지는 모두에게 허용
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            );

        http.addFilterBefore(forcePasswordChangeFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
