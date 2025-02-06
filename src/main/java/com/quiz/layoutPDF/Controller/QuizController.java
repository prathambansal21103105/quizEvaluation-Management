package com.quiz.layoutPDF.Controller;

import com.quiz.layoutPDF.Service.QuizService;
import com.quiz.layoutPDF.Service.PdfService;
import com.quiz.layoutPDF.models.Quiz;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/course/{courseCode}")
    public ResponseEntity<List<Quiz>> getQuizByCourseCode(@PathVariable String courseCode) {
        return ResponseEntity.ok(quizService.getAllquizes(courseCode));
    }

    @PostMapping("")
    public ResponseEntity<String> createQuiz(@RequestBody Quiz quiz) {
        Long quizId = quizService.addQuiz(quiz);  // Get the quiz ID after creation
        return new ResponseEntity<>("Quiz successfully added with ID: " + quizId, HttpStatus.CREATED);
    }

    @GetMapping("/generate-pdf/{quizId}")
    public ResponseEntity<byte[]> generateQuizPdf(@PathVariable Long quizId) {
        // Fetch quiz by ID
        Quiz quiz = quizService.getQuizById(quizId);
        System.out.println(quiz);
        // Generate PDF
        if(quiz != null) {
            byte[] pdfContent = pdfService.generateQuizPdf(quiz);
            // Return PDF as response
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=quiz_" + quizId + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfContent);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
        return new ResponseEntity<>("Quiz successfully deleted", HttpStatus.BAD_REQUEST);
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
}
