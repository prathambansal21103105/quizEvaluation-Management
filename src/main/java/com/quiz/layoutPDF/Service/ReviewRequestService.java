package com.quiz.layoutPDF.Service;

import com.quiz.layoutPDF.Repository.PlayerRepository;
import com.quiz.layoutPDF.Repository.QuizRepository;
import com.quiz.layoutPDF.Repository.ReviewRequestRepository;
import com.quiz.layoutPDF.models.Player;
import com.quiz.layoutPDF.models.Quiz;
import com.quiz.layoutPDF.models.ReviewRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewRequestService {

    private final PlayerRepository playerRepository;
    private final QuizRepository quizRepository;
    private final ReviewRequestRepository reviewRequestRepository;


    public ReviewRequestService(ReviewRequestRepository reviewRequestRepository, PlayerRepository playerRepository, QuizRepository quizRepository) {
        this.reviewRequestRepository = reviewRequestRepository;
        this.playerRepository = playerRepository;
        this.quizRepository = quizRepository;
    }

    public ReviewRequest getReviewRequestById(Long id) {
        return reviewRequestRepository.findById(id).orElse(null);
    }

    public Long createReviewRequest(String email, Long quizId, String description) {
        System.out.println(email + " " + quizId + " " + description);
        Player player = playerRepository.findByEmail(email).orElse(null);
        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        ReviewRequest reviewRequest = new ReviewRequest(description, player, quiz);
        List<ReviewRequest> reviewRequestList =
                reviewRequestRepository.findReviewRequestByPlayerIdAndQuizId(player.getId(), quizId);
        if(reviewRequestList.isEmpty()) {
            return reviewRequestRepository.save(reviewRequest).getId();
        }
        else{
            return (long) -1;
        }
    }

    public Boolean deleteReviewRequest(Long id) {
        ReviewRequest reviewRequest = reviewRequestRepository.findById(id).orElse(null);
        if(reviewRequest != null) {
            reviewRequestRepository.delete(reviewRequest);
            return true;
        }
        return false;
    }
}
