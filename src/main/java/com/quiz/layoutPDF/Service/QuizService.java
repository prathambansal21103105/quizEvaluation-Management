package com.quiz.layoutPDF.Service;

import com.quiz.layoutPDF.Repository.QuestionRepository;
import com.quiz.layoutPDF.Repository.QuizRepository;
import com.quiz.layoutPDF.models.Question;
import com.quiz.layoutPDF.models.Quiz;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuestionService questionService;
    private final QuestionRepository questionRepository;

    public QuizService(QuizRepository quizRepository, QuestionService questionService, QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.questionService = questionService;
        this.questionRepository = questionRepository;
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

    public Boolean updateQuiz(Long id, Quiz quiz) {
        Quiz savedQuiz = getQuizById(id);
        if (savedQuiz == null) {
            return false;
        }
        try {
            savedQuiz.setCourse(quiz.getCourse());
            savedQuiz.setCourseCode(quiz.getCourseCode());
            savedQuiz.setTitle(quiz.getTitle());
            savedQuiz.setMaxMarks(quiz.getMaxMarks());
            quizRepository.save(savedQuiz);
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Long createDuplicate(Long id) {
        Quiz savedQuiz = getQuizById(id);
        List<Question> questions = savedQuiz.getQuestions();
        Quiz newQuiz = new Quiz(savedQuiz.getTitle() + " (Duplicate)", savedQuiz.getCourse(), savedQuiz.getCourseCode(), savedQuiz.getMaxMarks(),new ArrayList<>(),new ArrayList<>());
        Quiz duplicateQuiz = quizRepository.save(newQuiz);
        for(Question question : questions){
            Question newQuestion = new Question();
            newQuestion.setQuestion(question.getQuestion());
            newQuestion.setAnswer(question.getAnswer());
            newQuestion.setQuestionNum(question.getQuestionNum());
            newQuestion.setMarks(question.getMarks());
            List<String> newOptions = new ArrayList<>(question.getOptions());
            newQuestion.setOptions(newOptions);
            newQuestion.setQuiz(duplicateQuiz);
            questionRepository.save(newQuestion);
        }
        return duplicateQuiz.getId();
    }
}