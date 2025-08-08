package com.bookforest.project_bookforest_intj.qna.controller;

import com.bookforest.project_bookforest_intj.qna.vo.QnaSaveReqVO;
import com.bookforest.project_bookforest_intj.qna.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QnaController {

    private final QnaService qnaService;

    @GetMapping("/customer-service")
    public String customerService(Model model) {
        log.info("customer-service 고객센터 페이지 접속");
        model.addAttribute("qnaList", qnaService.getQnaList());
        return "qna/customer_service";
    }

    @GetMapping("/qna/list")
    public String qnaList(Model model) {
        model.addAttribute("qnaList", qnaService.getQnaList());
        return "qna/customer_service";
    }

    @GetMapping("/qna/detail/{qnaNo}")
    public String qnaDetail(@PathVariable Long qnaNo, Model model) {
        model.addAttribute("qna", qnaService.getQna(qnaNo));
        return "qna/qna_detail";
    }

    @GetMapping("/qna/question")
    public String qnaForm() {
        return "qna/qna_form";
    }

    /**
     * 1:1 문의를 등록하는 POST 요청을 처리합니다.
     * @param qnaSaveReqDto 뷰에서 전달된 Q&A 저장 요청 데이터 (제목, 내용)
     * @param principal 현재 로그인한 사용자 정보를 담고 있는 객체
     * @return Q&A 목록 페이지로 리다이렉트
     */
    @PostMapping("/qna/question")
    public String saveQna(QnaSaveReqVO qnaSaveReqDto, Principal principal) {
        String userId = principal.getName(); // 현재 로그인한 사용자의 ID를 가져옵니다.
        log.info("1:1 문의 등록 요청. 작성자: {}, 제목: {}", userId, qnaSaveReqDto.getQnaTitle());

        qnaService.saveQna(qnaSaveReqDto, userId);

        log.info("1:1 문의 저장 완료. 목록 페이지로 리다이렉트합니다.");
        return "redirect:/customer-service"; // 수정된 고객센터 URL로 리다이렉트
    }
}