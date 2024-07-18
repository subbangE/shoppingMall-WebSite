package com.myapp.team.Board.Question;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    // 질문 제목 및 회원 일련번호만 불러오기 (페이징 처리)
    List<Question> getQuestions(@Param("offset") int offset, @Param("limit") int limit);

    // question_no를 통해 질문 한개씩 불러오기
    Question selectQuestion(int questionNo);

    // user_no와 관련된 질문 목록 불러오기
    List<Question> selectQuestionById(int userNo);

    // question 등록
    int insertQuestion(Question question);

    // question 수정
    int updateQuestion(int questionNo, String questionTitle, String questionContent);

    // question 삭제
    int deleteQuestion(int questionNo);
}
