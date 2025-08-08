package com.bookforest.project_bookforest_intj.qna.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Qna {
    private String aeId;
    private String qnaNo;
    private String qnaTitle;
    private String qnaYmd;
    private String qnaContent;
    private String uaNo;
    private String ccgI001;
}