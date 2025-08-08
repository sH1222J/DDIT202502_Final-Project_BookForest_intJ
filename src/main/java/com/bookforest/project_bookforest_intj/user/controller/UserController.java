package com.bookforest.project_bookforest_intj.user.controller;

import com.bookforest.project_bookforest_intj.user.vo.RegisterReqDto;
import com.bookforest.project_bookforest_intj.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    /**
     * 회원가입 페이지를 보여줌.
     *
     * @return 회원가입 폼 뷰 이름
     */
    @GetMapping("/register")
    public String registerForm() {
        return "user/register";
    }

    /**
     * 회원가입 요청을 처리.
     *
     * @param registerReqDto 회원가입 정보
     * @return 로그인 페이지로 리다이렉트
     */
    @PostMapping("/register")
    public String register(RegisterReqDto registerReqDto) {
         /*
        RegisterReqDto(aeId=null, ccgU001=null, userNm=3, userId=null
        , address=7, email=4@5, enabled=null, password=2, phone=6
        , username=1, authVOList=null)
         */
        log.info("register->registerReqDto : " + registerReqDto);

        //USERS 테이블에 insert
        int result = userService.registerUser(registerReqDto);
        log.info("register->result : " + result);

        return "redirect:/user/login";
    }


    /**
     * 로그인 페이지를 보여줍니다.
     *
     * @return 로그인 폼 뷰 이름
     */
    @GetMapping("/login")
    public String loginForm() {
        String password = "password123";
        log.info("password : " + bCryptPasswordEncoder.encode(password));

        return "user/login";
    }

    /**
     * 아이디 찾기 폼 페이지를 보여줍니다.
     *
     * @return 아이디 찾기 폼 뷰 이름
     */
    @GetMapping("/find-id")
    public String findIdForm(Model model, HttpServletRequest request) {
        model.addAttribute("_csrf", request.getAttribute("_csrf"));
        return "user/find_id";
    }

    /**
     * 아이디 찾기 요청을 처리합니다.
     *
     * @param userNm 이름
     * @param email  이메일
     * @param model  뷰에 데이터를 전달하기 위한 모델
     * @return 아이디 찾기 결과 뷰 이름
     */
    @PostMapping("/find-id")
    public String findId(@RequestParam("userNm") String userNm,
                         @RequestParam("email") String email,
                         Model model) {
        log.info("findId - userNm: {}, email: {}", userNm, email);
        String foundUsername = userService.findUsernameByNameAndEmail(userNm, email);
        log.info("findId - foundUsername: {}", foundUsername);
        model.addAttribute("foundUsername", foundUsername);
        return "user/find_id_result";
    }

    /**
     * 비밀번호 찾기(재설정 요청) 폼 페이지를 보여줍니다.
     *
     * @return 비밀번호 찾기 폼 뷰 이름
     */
    @GetMapping("/forgot-password")
    public String forgotPasswordForm() {
        log.info("Request received for /user/forgot-password (GET)");
        return "user/find_password";
    }

    /**
     * 비밀번호 재설정 요청을 처리합니다.
     *
     * @param usernameOrEmail    아이디 또는 이메일
     * @param redirectAttributes 리다이렉트 시 메시지 전달을 위한 객체
     * @return 비밀번호 재설정 확인 페이지로 리다이렉트
     */
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam("usernameOrEmail") String usernameOrEmail,
                                 RedirectAttributes redirectAttributes) {
        log.info("/forgot-password -> usernameOrEmail", usernameOrEmail);
        try {
            //userService.createPasswordResetToken(usernameOrEmail);
            return "redirect:/user/forgot-password-confirmation";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/forgot-password";
        }
    }

    /**
     * 비밀번호 재설정 링크 발송 확인 페이지를 보여줍니다.
     *
     * @return 비밀번호 재설정 확인 뷰 이름
     */
    @GetMapping("/forgot-password-confirmation")
    public String forgotPasswordConfirmation() {
        return "user/find_password";
    }

    @GetMapping("/change-password")
    public String changePasswordForm() {
        return "user/change_password";
    }

    @GetMapping("/find-password")
    public String findPasswordForm() {
        return "user/find_password";
    }

    @PostMapping("/find-password")
    @ResponseBody
    public Map<String, String> findPassword(@RequestBody Map<String, String> payload) {
        ///find-password -> payload : {name=홍길동, username=ghdrlfehd001, email=a@naver.com}
        log.info("/find-password -> payload : {}", payload);
        Map<String, String> response = new HashMap<>();
        try {
            String name = payload.get("name");
            String username = payload.get("username");
            String email = payload.get("email");
            String tempPassword = userService.findPassword(name, username, email);
            response.put("success", "true");
            response.put("tempPassword", tempPassword);
        } catch (IllegalArgumentException e) {
            response.put("success", "false");
            response.put("message", e.getMessage());
        } catch (Exception e) {
            log.error("비밀번호 찾기 중 예상치 못한 오류 발생: ", e);
            response.put("success", "false");
            response.put("message", "비밀번호 찾기 중 서버 오류가 발생했습니다.");
        }
        return response;
    }

    @PostMapping("/change-password")
    @ResponseBody
    public Map<String, String> changePassword(@RequestBody Map<String, String> payload, Principal principal) {
        log.info("changePassword method called.");
        Map<String, String> response = new HashMap<>();
        try {
            String newPassword = payload.get("newPassword");
            String username = principal.getName(); // 현재 로그인한 사용자 이름 가져오기
            log.info("Attempting to change password for user: {} with new password: {}", username, newPassword);
            userService.updatePasswordAndResetFlag(username, newPassword);
            log.info("Password successfully changed for user: {}", username);
            response.put("success", "true");
            response.put("message", "비밀번호가 성공적으로 변경되었습니다.");
        } catch (Exception e) {
            log.error("비밀번호 변경 중 오류 발생: ", e);
            response.put("success", "false");
            response.put("message", "비밀번호 변경 중 오류가 발생했습니다.");
        }
        return response;
    }

    /**
     * 비밀번호 재설정 폼 페이지를 보여줍니다. (토큰 유효성 검증 포함)
     *
     * @param token              재설정 토큰
     * @param model              뷰에 데이터를 전달하기 위한 모델
     * @param redirectAttributes 리다이렉트 시 메시지 전달을 위한 객체
     * @return 새 비밀번호 설정 폼 뷰 이름 또는 에러 페이지로 리다이렉트
     */
    @GetMapping("/reset-password")
    public String resetPasswordForm(@RequestParam("token") String token,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {
        log.info("/reset-password -> token: {}", token);
        try {
            // 토큰 유효성 검증 로직 (UserService에서 구현 예정)
            // 유효하다면 토큰을 모델에 추가하여 폼에 hidden 필드로 전달
            userService.validatePasswordResetToken(token);
            model.addAttribute("token", token);
            return "user/find_password";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/login"; // 유효하지 않은 토큰이면 로그인 페이지로 리다이렉트
        }
    }

    /**
     * 새 비밀번호 설정을 처리합니다.
     *
     * @param token              재설정 토큰
     * @param newPassword        새 비밀번호
     * @param confirmPassword    새 비밀번호 확인
     * @param redirectAttributes 리다이렉트 시 메시지 전달을 위한 객체
     * @return 비밀번호 재설정 완료 페이지로 리다이렉트
     */
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token,
                                @RequestParam("newPassword") String newPassword,
                                @RequestParam("confirmPassword") String confirmPassword,
                                RedirectAttributes redirectAttributes) {
        log.info("reset-password -> token", token);
        try {
            if (!newPassword.equals(confirmPassword)) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
            userService.resetPassword(token, newPassword);
            return "redirect:/user/reset-password-success";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/reset-password?token=" + token; // 실패 시 다시 재설정 폼으로
        }
    }

    /**
     * 비밀번호 재설정 완료 페이지를 보여줍니다.
     *
     * @return 비밀번호 재설정 완료 뷰 이름
     */
    @GetMapping("/reset-password-success")
    public String resetPasswordSuccess() {
        return "user/find_password";
    }
}