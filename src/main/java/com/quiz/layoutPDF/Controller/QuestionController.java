package com.quiz.layoutPDF.Controller;

import com.quiz.layoutPDF.Service.QuestionService;
import com.quiz.layoutPDF.models.Question;
import com.quiz.layoutPDF.models.QuestionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
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
            return new ResponseEntity<>("" + questionId, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Quiz not found or error occurred", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/duplicate/{quizId}")
    public ResponseEntity<String> addDuplicateQuestionToQuiz(@PathVariable Long quizId, @RequestBody Question question) {
        Long questionId = questionService.createDuplicateQuestionForQuiz(quizId, question);
        if (questionId != null) {
            return new ResponseEntity<>("" + questionId, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Question not added ", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update/{questionId}")
    public ResponseEntity<String> updateQuestion(@PathVariable Long questionId , @RequestBody QuestionDTO question) {
        System.out.println(question);
        Boolean updated = questionService.updateQuestionForQuiz(questionId,question);
        System.out.println(updated);
        if (updated) {
            return new ResponseEntity<>("Question updated successfully with ID: " + questionId, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Question not updated or error occurred", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long questionId) {
        Question question = questionService.getQuestionById(questionId);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<String> deleteQuestionById(@PathVariable Long questionId) {
        boolean deleted = questionService.deleteQuestionById(questionId);
        return deleted ? new ResponseEntity<>("Question deleted successfully", HttpStatus.NO_CONTENT)
                : new ResponseEntity<>("Question not found or error occurred", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/addAnswer/{questionId}")
    public ResponseEntity<String> addAnswerById(@PathVariable Long questionId, @RequestBody String answer) {
        Question question = questionService.getQuestionById(questionId);
        if (question != null) {
            boolean added = questionService.addAnswerToQuestion(questionId, answer);
            if (added) {
                return new ResponseEntity<>("Answer added successfully", HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Answer not added", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Question not found or error occurred", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("image/{questionId}")
    public ResponseEntity<String> deleteQuestionImageById(@PathVariable Long questionId) {
        boolean deleted = questionService.deleteQuestionImageById(questionId);
        return deleted ? new ResponseEntity<>("Question image deleted successfully", HttpStatus.NO_CONTENT)
                : new ResponseEntity<>("Question not found or error occurred", HttpStatus.BAD_REQUEST);
    }

}
