package com.quiz.layoutPDF.Repository;

import com.quiz.layoutPDF.models.PlayerResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerResponseRepository extends JpaRepository<PlayerResponse, Long> {
    Optional<PlayerResponse> findByQuizIdAndPlayerId(Long quizId, Long playerId);

    List<PlayerResponse> findByQuizId(Long quizId);

    List<PlayerResponse> findByQuizIdOrderByPlayerId(Long quizId);

    void deleteByQuizId(Long quizId);

}
