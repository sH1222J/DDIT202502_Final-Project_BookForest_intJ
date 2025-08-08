package com.bookforest.project_bookforest_intj.qna.vo;

import lombok.Data;

/**
 * QNA_REPLY (문의 답변) 테이블을 위한 Value Object (VO) 입니다.
 * 사용자 문의에 대한 답변 데이터 구조를 나타냅니다.
 */
@Data
public class QnaReplyVO {
    private String reNo;
    private String reContent;
    private String userId;
    private String qnaNo;
    private String aeId;
}
