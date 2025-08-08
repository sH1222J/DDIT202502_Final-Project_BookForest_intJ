package com.bookforest.project_bookforest_intj.qna.service;

import com.bookforest.project_bookforest_intj.qna.vo.QnaSaveReqVO;
import com.bookforest.project_bookforest_intj.qna.entity.Qna;
import com.bookforest.project_bookforest_intj.qna.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

public interface QnaService {

    List<Qna> getQnaList();

    Qna getQna(Long qnaNo);

    void saveQna(QnaSaveReqVO qnaSaveReqDto, String name);
}

