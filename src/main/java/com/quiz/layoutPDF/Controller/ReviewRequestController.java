package com.quiz.layoutPDF.Controller;

import com.quiz.layoutPDF.Service.ReviewRequestService;
import com.quiz.layoutPDF.models.ReviewRequest;
import com.quiz.layoutPDF.models.ReviewRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/reviewRequest")
public class ReviewRequestController {

    private ReviewRequestService reviewRequestService;

    public ReviewRequestController(ReviewRequestService reviewRequestService) {
        this.reviewRequestService = reviewRequestService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewRequest> getReviewById(@PathVariable Long id) {
        ReviewRequest reviewRequest = reviewRequestService.getReviewRequestById(id);
        if(reviewRequest != null) {
            return ResponseEntity.ok(reviewRequest);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<Long> createReviewRequest(@RequestBody ReviewRequestDTO reviewRequestDTO) {
        String email=reviewRequestDTO.getEmail();
        Long quizId=reviewRequestDTO.getQuizId();
        String description=reviewRequestDTO.getDescription();
        Long id = reviewRequestService.createReviewRequest(email, quizId, description);
        if(id == -1){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReviewRequest(@PathVariable Long id) {
        Boolean deleted = reviewRequestService.deleteReviewRequest(id);
        if(deleted){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
