package com.myapp.team.Board.Answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    @Autowired
    private AnswerMapper answerMapper;

    public Answer selectQuestionByNo(int questionNo){
        return answerMapper.selectQuestionByNo(questionNo);
    }

    public int insertAnswer(Answer answer) {
        return answerMapper.insertAnswer(answer);
    }

    public int updateAnswer(int questionNo, String answerTitle, String answerContent) {
        return answerMapper.updateAnswer(questionNo, answerTitle, answerContent);
    }

    public int deleteAnswer(int questionNo) {
        return answerMapper.deleteAnswer(questionNo);
    }
}
