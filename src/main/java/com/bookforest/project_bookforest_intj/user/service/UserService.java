package com.bookforest.project_bookforest_intj.user.service;

import com.bookforest.project_bookforest_intj.user.vo.RegisterReqDto;
import com.bookforest.project_bookforest_intj.user.vo.UserVO;
import com.bookforest.project_bookforest_intj.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public interface UserService {

    int registerUser(RegisterReqDto registerReqDto);

    String findUsernameByNameAndEmail(String userNm, String email);

    void createPasswordResetToken(String usernameOrEmail);
    void validatePasswordResetToken(String token);

    void resetPassword(String token, String newPassword);

    String findPassword(String name, String username, String email);

    void updatePasswordAndResetFlag(String username, String newPassword);
}
