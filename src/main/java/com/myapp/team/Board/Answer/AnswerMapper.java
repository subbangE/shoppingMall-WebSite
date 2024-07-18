package com.myapp.team.Board.Answer;

import org.apache.ibatis.annotations.*;


@Mapper
public interface AnswerMapper {

    Answer selectQuestionByNo(int questionNo);

    int insertAnswer(Answer answer);

    int updateAnswer(int questionNo, String answerTitle, String answerContent);

    int deleteAnswer(int questionNo);
}
