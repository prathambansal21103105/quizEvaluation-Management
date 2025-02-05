package com.quiz.layoutPDF.Controller;

import com.quiz.layoutPDF.Service.QuestionService;
import com.quiz.layoutPDF.models.Question;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/{quizId}")
    public ResponseEntity<String> addQuestionToQuiz(@PathVariable Long quizId, @RequestBody Question question) {
        Long questionId = questionService.addQuestionToQuiz(quizId, question);
        if (questionId != null) {
            return new ResponseEntity<>("Question added successfully with ID: " + questionId, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Quiz not found or error occurred", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long questionId) {
        Question question = questionService.getQuestionById(questionId);
        return question != null ? new ResponseEntity<>(question, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<String> deleteQuestionById(@PathVariable Long questionId) {
        boolean deleted = questionService.deleteQuestionById(questionId);
        return deleted ? new ResponseEntity<>("Question deleted successfully", HttpStatus.NO_CONTENT)
                : new ResponseEntity<>("Question not found or error occurred", HttpStatus.BAD_REQUEST);
    }

}
