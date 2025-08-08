package com.bookforest.project_bookforest_intj.qna.vo;

import lombok.Data;

/**
 * QNA (1:1 문의) 테이블을 위한 Value Object (VO) 입니다.
 * 사용자 문의 데이터 구조를 나타냅니다.
 */
@Data
public class QnaVO {
    private String aeId;
    private String qnaNo;
    private String qnaTitle;
    private String qnaYmd;
    private String qnaContent;
    private String uaNo;
    private String ccgI001;
}
