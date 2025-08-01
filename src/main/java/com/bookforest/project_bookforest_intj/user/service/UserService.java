package com.bookforest.project_bookforest_intj.user.service;

import com.bookforest.project_bookforest_intj.user.entity.UserVO;
import com.bookforest.project_bookforest_intj.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // 임시로 토큰을 저장할 맵 (실제 운영에서는 DB에 저장해야 함)
    private final Map<String, String> passwordResetTokens = new HashMap<>();

    public void registerUser(UserVO user) {

    }

    public String findUsernameByNameAndEmail(String userNm, String email) {
        return userRepository.findUsernameByNameAndEmail(userNm, email);
    }

    /**
     * 비밀번호 재설정 토큰을 생성하고 이메일 발송을 시뮬레이션합니다.
     * @param usernameOrEmail 사용자 아이디 또는 이메일
     */
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
}
