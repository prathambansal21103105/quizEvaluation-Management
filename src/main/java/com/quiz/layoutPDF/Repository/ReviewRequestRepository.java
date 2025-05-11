package com.quiz.layoutPDF.Repository;

import com.quiz.layoutPDF.models.ReviewRequest;
import com.quiz.layoutPDF.models.ReviewWithResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRequestRepository extends JpaRepository<ReviewRequest, Long> {
    List<ReviewRequest> findReviewRequestByPlayerIdAndQuizId(Long playerId, Long quizId);

    @Query("SELECT new com.quiz.layoutPDF.models.ReviewWithResponseDTO(rr.id, pr.id, pr.player.id, pr.quiz.id, pr.markedResponses, rr.description) " +
            "FROM ReviewRequest rr JOIN PlayerResponse pr " +
            "ON rr.player.id = pr.player.id AND rr.quiz.id = :quizId AND rr.quiz.id = pr.quiz.id")
    List<ReviewWithResponseDTO> getAllReviewsForQuiz(@Param("quizId") Long quizId);
}
