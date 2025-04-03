package com.quiz.layoutPDF.Controller;
import com.quiz.layoutPDF.Service.QuestionImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/question-images")
public class QuestionImageController {
    private final QuestionImageService questionImageService;

    public QuestionImageController(QuestionImageService questionImageService) {
        this.questionImageService = questionImageService;
    }

    @PostMapping("")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String id = questionImageService.saveImage(file);
            return ResponseEntity.ok(id);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading image");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) {
        byte[] image = questionImageService.getImage(id);
        return (image != null) ?
                ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image) :
                ResponseEntity.notFound().build();
    }
}
