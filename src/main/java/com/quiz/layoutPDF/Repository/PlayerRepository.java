package com.quiz.layoutPDF.Repository;

import com.quiz.layoutPDF.models.Player;
import com.quiz.layoutPDF.models.PlayerQuizStatsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByEmail(String email);
    @Query("SELECT new com.quiz.layoutPDF.models.PlayerQuizStatsResponse(q.title, pr.score, q.maxMarks, pr.markedResponses, pr.scores, q.course, q.courseCode, q.id, pr.id, q.evaluationMode) " +
            "FROM PlayerResponse pr JOIN pr.quiz q WHERE pr.player.id = :playerId")
    List<PlayerQuizStatsResponse> getStatsForPlayer(@Param("playerId") Long playerId);
}
