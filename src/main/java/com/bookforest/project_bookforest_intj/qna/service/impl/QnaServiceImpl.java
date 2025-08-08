package com.bookforest.project_bookforest_intj.qna.service.impl;

import com.bookforest.project_bookforest_intj.qna.vo.QnaSaveReqVO;
import com.bookforest.project_bookforest_intj.qna.entity.Qna;
import com.bookforest.project_bookforest_intj.qna.repository.QnaRepository;
import com.bookforest.project_bookforest_intj.qna.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaServiceImpl implements QnaService {

    private final QnaRepository qnaRepository;

    @Override
    public List<Qna> getQnaList() {
        return qnaRepository.findAll();
    }

    @Override
    public Qna getQna(Long qnaNo) {
        return qnaRepository.findById(qnaNo);
    }

    /**
     * 사용자가 작성한 1:1 문의를 데이터베이스에 저장합니다.
     * @param qnaSaveReqDto 컨트롤러에서 전달된 Q&A 저장 요청 데이터
     * @param userId 현재 로그인한 사용자의 ID
     */
    @Override
    public void saveQna(QnaSaveReqVO qnaSaveReqDto, String userId) {
        // 1. 뷰에서 받은 데이터(DTO)를 DB에 저장할 엔티티 객체로 변환합니다.
        Qna qna = new Qna();
        qna.setAeId(userId); // 컨트롤러에서 받은 로그인 사용자 ID 설정
        qna.setQnaTitle(qnaSaveReqDto.getQnaTitle());
        qna.setQnaContent(qnaSaveReqDto.getQnaContent());

        // 2. qnaNo(PK), qnaYmd(작성일), ccgI001(상태)는 MyBatis Mapper에서 처리됩니다.
//        log.info("DB에 저장할 Q&A 엔티티 생성: {}", qna);

        // 3. 리포지토리를 통해 DB에 저장합니다.
        qnaRepository.save(qna);

        // <selectKey>를 사용했기 때문에, save 호출 후에는 qna 객체에 DB에서 생성된 qnaNo가 자동으로 채워집니다.
//        log.info("DB 저장 완료. 생성된 QNA 번호: {}", qna.getQnaNo());
    }
}
