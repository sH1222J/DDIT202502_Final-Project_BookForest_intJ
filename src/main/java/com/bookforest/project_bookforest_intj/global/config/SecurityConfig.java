package com.bookforest.project_bookforest_intj.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {



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
        http
            .authorizeHttpRequests(authorize -> authorize
                // 정적 자원 및 회원가입, 로그인, 아이디 찾기, 비밀번호 찾기 관련 페이지는 모두에게 허용
                .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico", "/user/register", "/user/login", "/user/find-id", "/user/forgot-password/**", "/user/reset-password/**").permitAll()
                // 홈페이지, 책 관련 페이지는 모두에게 허용
                .requestMatchers("/", "/book/**").permitAll()
                // 그 외 모든 요청은 인증된 사용자만 접근 가능
                .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                // 사용자 정의 로그인 페이지
                .loginPage("/user/login")
                // 로그인 성공 시 이동할 기본 URL
                .defaultSuccessUrl("/", true)
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

        return http.build();
    }
}
