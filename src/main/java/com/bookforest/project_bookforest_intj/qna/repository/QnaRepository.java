package com.bookforest.project_bookforest_intj.qna.repository;

import com.bookforest.project_bookforest_intj.qna.entity.Qna;
import com.bookforest.project_bookforest_intj.qna.vo.QnaVO;
import com.bookforest.project_bookforest_intj.qna.vo.QnaReplyVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * QNA (1:1 문의) 및 QNA_REPLY (문의 답변) 엔터티를 위한 MyBatis 매퍼 인터페이스입니다.
 * 사용자 문의 및 답변과 관련된 CRUD 작업을 위한 메서드를 제공합니다.
 */
@Mapper
public interface QnaRepository {

    int insertQna(QnaVO qnaVO);

    List<QnaVO> selectQnaList();

    List<QnaVO> selectQnaListByAeId(String aeId);

    QnaVO selectQnaDetail(String qnaNo);

    int updateQna(QnaVO qnaVO);

    int updateQnaStatus(@Param("qnaNo") String qnaNo, @Param("ccgI001") String ccgI001);

    int deleteQna(String qnaNo);

    int insertQnaReply(QnaReplyVO qnaReplyVO);

    List<QnaReplyVO> selectQnaRepliesByQnaNo(String qnaNo);

    int updateQnaReply(QnaReplyVO qnaReplyVO);

    int deleteQnaReply(String reNo);

    List<Qna> findAll();

    Qna findById(Long qnaNo);

    void save(Qna qna);
}