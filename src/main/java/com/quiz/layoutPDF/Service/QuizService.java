package com.quiz.layoutPDF.Service;

import com.quiz.layoutPDF.Repository.QuizRepository;
import com.quiz.layoutPDF.models.Question;
import com.quiz.layoutPDF.models.Quiz;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuestionService questionService;

    public QuizService(QuizRepository quizRepository, QuestionService questionService) {
        this.quizRepository = quizRepository;
        this.questionService = questionService;
    }

    public List<Quiz> getAllquizes(String courseCode) {
        return quizRepository.findByCourseCode(courseCode);
    }

    public Long addQuiz(Quiz quiz) {
        Quiz savedQuiz = quizRepository.save(quiz);
        return savedQuiz.getId(); // Return the ID of the saved quiz
    }

    public Quiz getQuizById(Long id) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);
        return quizOptional.orElse(null);
    }

    public Boolean deleteQuiz(Long id) {
        if (quizRepository.existsById(id)) {
            quizRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Boolean addAnswersForQuiz(Quiz quiz, List<String> answers) {
        List<Question> questions = quiz.getQuestions();
        int counter = 0;
        try{
            for (Question question : questions) {
                questionService.addAnswer(question.getId(),answers.get(counter));
                counter++;
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}