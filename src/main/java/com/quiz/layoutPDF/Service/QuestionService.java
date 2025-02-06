package com.quiz.layoutPDF.Service;

import com.quiz.layoutPDF.models.Question;
import com.quiz.layoutPDF.models.Quiz;
import com.quiz.layoutPDF.Repository.QuestionRepository;
import com.quiz.layoutPDF.Repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;

    public QuestionService(QuestionRepository questionRepository, QuizRepository quizRepository) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
    }


    public Long addQuestionToQuiz(Long quizId, Question question) {
        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        if (quiz == null) {
            return null;
        }

        question.setQuiz(quiz);
        Question savedQuestion = questionRepository.save(question);

        return savedQuestion.getId();
    }

    public boolean addAnswerToQuestion(Long questionId, String answer) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null) {
            return false;
        }
        try {
            question.setAnswer(answer);
            questionRepository.save(question);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public Question getQuestionById(Long questionId) {
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        return questionOptional.orElse(null);
    }

    public boolean deleteQuestionById(Long questionId) {
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        if (questionOptional.isPresent()) {
            Question question = questionOptional.get();
            Quiz quiz = question.getQuiz();
            if (quiz != null) {
                quiz.getQuestions().remove(question);
                quizRepository.save(quiz);
            }
            questionRepository.delete(question);
            return true;
        }
        return false;
    }

    public void addAnswer(Long id, String answer) throws Exception {
        Question question=getQuestionById(id);
        try {
            if (question != null) {
                question.setAnswer(answer);
                questionRepository.save(question);
            }
        }
        catch (Exception e) {
            throw new Exception("Answer not added");
        }
    }
}
