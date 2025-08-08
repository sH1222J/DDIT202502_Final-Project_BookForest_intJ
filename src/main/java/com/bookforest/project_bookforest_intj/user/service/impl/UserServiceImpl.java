package com.bookforest.project_bookforest_intj.user.service.impl;

import com.bookforest.project_bookforest_intj.mapper.UserMapper;
import com.bookforest.project_bookforest_intj.user.repository.UserRepository;
import com.bookforest.project_bookforest_intj.user.vo.RegisterReqDto;
import com.bookforest.project_bookforest_intj.user.service.UserService;
import com.bookforest.project_bookforest_intj.user.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService; // UserDetailsService 주입

    // 임시로 토큰을 저장할 맵 (실제 운영에서는 DB에 저장해야 함)
    private final Map<String, String> passwordResetTokens = new HashMap<>();

    @Autowired
    UserMapper userMapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public int registerUser(RegisterReqDto registerReqDto){
        String password = registerReqDto.getPassword();
        password = this.bCryptPasswordEncoder.encode(password);
        registerReqDto.setPassword(password);

        int result = this.userMapper.registerUser(registerReqDto);

        return result;
    }

    @Override
    public String findUsernameByNameAndEmail(String userNm, String email) {
        return userRepository.findUsernameByNameAndEmail(userNm, email);
    }

    /**
     * 비밀번호 재설정 토큰을 생성하고 이메일 발송을 시뮬레이션합니다.
     * @param usernameOrEmail 사용자 아이디 또는 이메일
     */
    @Override
    public void createPasswordResetToken(String usernameOrEmail) {
        // 1. 사용자 조회 (아이디 또는 이메일로)
        UserVO user = userRepository.findByUsernameOrEmail(usernameOrEmail);
        if (user == null) {
            throw new IllegalArgumentException("등록되지 않은 아이디 또는 이메일입니다.");
        }

        // 2. 토큰 생성
        String token = UUID.randomUUID().toString();

        // 3. 토큰 저장 (실제로는 DB에 사용자 ID와 함께 저장하고 만료 시간 설정)
        passwordResetTokens.put(token, user.getUsername()); // 여기서는 username을 저장
        log.info("비밀번호 재설정 토큰 생성: {} (사용자: {})", token, user.getUsername());

        // 4. 이메일 발송 시뮬레이션
        log.info("이메일 발송 시뮬레이션: {} 님에게 비밀번호 재설정 링크 전송", user.getEmail());
        log.info("재설정 링크: http://localhost:8081/user/reset-password?token={}", token);
    }

    /**
     * 비밀번호 재설정 토큰의 유효성을 검증합니다.
     * @param token 재설정 토큰
     */
    @Override
    public void validatePasswordResetToken(String token) {
        if (!passwordResetTokens.containsKey(token)) {
            throw new IllegalArgumentException("유효하지 않거나 만료된 토큰입니다.");
        }
        // 실제로는 토큰의 만료 시간도 확인해야 함
    }

    /**
     * 새 비밀번호로 업데이트합니다.
     * @param token 재설정 토큰
     * @param newPassword 새 비밀번호
     */
    @Override
    public void resetPassword(String token, String newPassword) {
        String username = passwordResetTokens.get(token);
        if (username == null) {
            throw new IllegalArgumentException("유효하지 않거나 만료된 토큰입니다.");
        }

        // 1. 새 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(newPassword);

        // 2. 비밀번호 업데이트
        userRepository.updatePassword(username, encodedPassword);

        // 3. 사용된 토큰 삭제 (실제로는 DB에서 삭제)
        passwordResetTokens.remove(token);
        log.info("사용자 {}의 비밀번호가 재설정되었습니다.", username);
    }

    /**
     * 이름, 아이디, 이메일로 사용자를 찾아 임시 비밀번호를 발급합니다.
     * @param name 사용자 이름
     * @param username 사용자 아이디
     * @param email 사용자 이메일
     * @return 임시 비밀번호
     */
    @Override
    public String findPassword(String name, String username, String email) {

        log.info("findPaassword->name : " + name);
        log.info("findPaassword->username : " + username);
        log.info("findPaassword->email : " + email);

        UserVO user = userRepository.findByNameAndUsernameAndEmail(name, username, email);
        if (user == null) {
            throw new IllegalArgumentException("입력하신 정보와 일치하는 사용자가 없습니다.");
        }

        // 임시 비밀번호 생성
        String tempPassword = UUID.randomUUID().toString().substring(0, 8); // 8자리 임시 비밀번호
        String encodedPassword = passwordEncoder.encode(tempPassword);

        // 비밀번호 업데이트
        userRepository.updatePassword(user.getUsername(), encodedPassword);
        userRepository.updateMustChangePassword(user.getUsername(), true);
        log.info("사용자 {}의 임시 비밀번호가 발급되었습니다: {}", user.getUsername(), tempPassword);

        return tempPassword;
    }

    /**
     * 사용자 비밀번호를 업데이트하고 비밀번호 변경 필요 플래그를 해제합니다.
     * @param username 사용자 아이디
     * @param newPassword 새 비밀번호
     */
    @Override
    public void updatePasswordAndResetFlag(String username, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        userRepository.updatePassword(username, encodedPassword);
        userRepository.updateMustChangePassword(username, false);

        // SecurityContextHolder의 Authentication 객체 업데이트
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);

        log.info("사용자 {}의 비밀번호가 변경되었고, 비밀번호 변경 필요 플래그가 해제되었습니다.", username);
    }


}