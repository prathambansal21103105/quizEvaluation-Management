package com.quiz.layoutPDF.Repository;

import com.quiz.layoutPDF.models.PlayerResponse;
import com.quiz.layoutPDF.models.PlayerResponseCollectionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerResponseRepository extends JpaRepository<PlayerResponse, Long> {
    Optional<PlayerResponse> findByQuizIdAndPlayerId(Long quizId, Long playerId);

    List<PlayerResponse> findByQuizId(Long quizId);

    @Query("SELECT new com.quiz.layoutPDF.models.PlayerResponseCollectionDTO(pr.id, pr.markedResponses, pr.score, pr.player.id, pr.quiz.id) " +
            "FROM PlayerResponse pr WHERE pr.quiz.id = :quizId order by pr.player.id")
    List<PlayerResponseCollectionDTO> findResponsesByQuizId(@Param("quizId") Long quizId);

    List<PlayerResponse> findByQuizIdOrderByPlayerId(Long quizId);

    void deleteByQuizId(Long quizId);

}
