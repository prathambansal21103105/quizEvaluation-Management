package com.quiz.layoutPDF.Controller;

import com.quiz.layoutPDF.Service.QuizService;
import com.quiz.layoutPDF.Service.PdfService;
import com.quiz.layoutPDF.models.Question;
import com.quiz.layoutPDF.models.Quiz;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/quiz")
public class QuizController {
    private QuizService quizService;
    private PdfService pdfService;

    public QuizController(QuizService quizService, PdfService pdfService) {
        this.quizService = quizService;
        this.pdfService = pdfService;
    }

    @GetMapping("")
    public ResponseEntity<List<Quiz>> getAllQuiz() {
        return ResponseEntity.ok(quizService.findAllQuizes());
    }

    @GetMapping("/course/{courseCode}")
    public ResponseEntity<List<Quiz>> getQuizByCourseCode(@PathVariable String courseCode) {
        return ResponseEntity.ok(quizService.getAllquizes(courseCode));
    }

    @PostMapping("")
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
        Long quizId = quizService.addQuiz(quiz);
        quiz.setId(quizId);
        return new ResponseEntity<>(quiz, HttpStatus.CREATED);
    }

    @GetMapping("/generate-pdf/{quizId}")
    public ResponseEntity<byte[]> generateQuizPdf(@PathVariable Long quizId) {
        Quiz quiz = quizService.getQuizById(quizId);
        if(quiz != null) {
            byte[] pdfContent = pdfService.generateQuizPdf(quiz);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=quiz_" + quizId + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfContent);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/search/{searchInput}")
    public ResponseEntity<List<Quiz>> searchQuiz(@PathVariable String searchInput) {
        try {
            List<Quiz> quizList = quizService.searchQuizBySearchTerm(searchInput);
            return ResponseEntity.ok(quizList);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long id) {
        System.out.println(id);
        Quiz quiz = quizService.getQuizById(id);
        System.out.println(quiz);
        if(quiz != null) {
            return new ResponseEntity<>(quiz, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable Long id) {
        Boolean deleted = quizService.deleteQuiz(id);
        if(deleted) {
            return new ResponseEntity<>("Quiz successfully deleted", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Quiz couldn't be deleted", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("addAnswers/{id}")
    public ResponseEntity<String> addAnswersForQuiz(@PathVariable Long id, @RequestBody List<String> answers) {
        Quiz quiz = quizService.getQuizById(id);
        if(quiz != null) {
            Boolean added = quizService.addAnswersForQuiz(quiz,answers);
            if(added) {
                return new ResponseEntity<>("Answers successfully added", HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("Answers not added", HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return new ResponseEntity<>("Quiz not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("update/{id}")
    public ResponseEntity<String> updateQuiz(@PathVariable Long id, @RequestBody Quiz quiz) {
        Boolean updated = quizService.updateQuiz(id,quiz);
        if(updated) {
            return new ResponseEntity<>("Quiz successfully updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Quiz not updated", HttpStatus.NOT_FOUND);
    }

    @GetMapping("duplicate/{id}")
    public ResponseEntity<Quiz> createDuplicateQuiz(@PathVariable Long id) {
        Quiz quiz = quizService.getQuizById(id);
        if(quiz != null) {
            Long quizId = quizService.createDuplicate(id);
            Quiz duplicateQuiz = quizService.getQuizById(quizId);
            System.out.println(duplicateQuiz.getQuestions().size());
            return new ResponseEntity<>(duplicateQuiz, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/evaluate/{id}")
    public ResponseEntity<String> evaluateQuiz(@PathVariable Long id) {
        try {
            Boolean evaluate = quizService.evaluate(id);
            if(evaluate) {
                return new ResponseEntity<>("Quiz successfully evaluated", HttpStatus.OK);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Quiz not evaluated", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Quiz couldn't be evaluated", HttpStatus.OK);
    }
}
