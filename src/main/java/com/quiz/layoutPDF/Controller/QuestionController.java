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

    @PostMapping("/duplicate/{quizId}")
    public ResponseEntity<String> addDuplicateQuestionToQuiz(@PathVariable Long quizId, @RequestBody Question question) {
        Long questionId = questionService.createDuplicateQuestionForQuiz(quizId, question);
        if (questionId != null) {
            return new ResponseEntity<>("Duplicate question added successfully with ID: " + questionId, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Question not added ", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update/{questionId}")
    public ResponseEntity<String> updateQuestion(@PathVariable Long questionId , @RequestBody Question question) {
        Boolean updated = questionService.updateQuestionForQuiz(questionId,question);
        if (updated) {
            return new ResponseEntity<>("Question updated successfully with ID: " + questionId, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Question not updated or error occurred", HttpStatus.BAD_REQUEST);
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

    @PostMapping("/addAnswer/{questionId}")
    public ResponseEntity<String> addAnswerById(@PathVariable Long questionId, @RequestBody String answer) {
        Question question = questionService.getQuestionById(questionId);
        if (question != null) {
            Boolean added = questionService.addAnswerToQuestion(questionId, answer);
            if (added) {
                return new ResponseEntity<>("Answer added successfully", HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Answer not added", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Question not found or error occurred", HttpStatus.BAD_REQUEST);
    }

}
