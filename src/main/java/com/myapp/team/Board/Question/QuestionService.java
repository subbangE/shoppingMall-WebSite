package com.myapp.team.Board.Question;

import com.myapp.team.Board.Answer.Answer;
import com.myapp.team.Board.Answer.AnswerMapper;
import com.myapp.team.Board.Attachment.Attachment;
import com.myapp.team.Board.Attachment.AttachmentMapper;
import com.myapp.team.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Autowired
    private UserMapper userMapper;

    // questiondetail (질문과 답변 보여주기 위함 => 질문 1개, 답변 1개)
    public Question getQuestionById(int questionNo) {
        Question question = questionMapper.selectQuestion(questionNo);
        if (question != null) {
            Answer answer = answerMapper.selectQuestionByNo(questionNo);

            if (answer != null) {
                question.setAnswer(answer);
            }
        }

        List<Attachment> attachments = attachmentMapper.getAttachmentsByNo(questionNo);
        question.setAttachments(attachments);

        String userName = userMapper.findByUserNo(question.getUserNo());
        question.setUserName(userName);

        return question;
    }

    // 페이징 처리를 위한 질문 목록
    public List<Question> getQuestions(int page) {
        int size = 10;
        int offset = (page - 1) * size;
        List<Question> questions = questionMapper.getQuestions(offset, size);

        // 출력되는 질문들을 User 테이블의 userId를 가져와서 userId에 입력
        for (Question question : questions) {
            String userName = userMapper.findByUserNo(question.getUserNo());
            question.setUserName(userName);
        }
        return questions;
    }

}
