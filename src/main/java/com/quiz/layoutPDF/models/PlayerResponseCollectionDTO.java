package com.quiz.layoutPDF.models;

import java.util.List;

public class PlayerResponseCollectionDTO {

    private Long id;
    private List<String> markedResponses;
    private Long score;
    private Long playerId;
    private Long quizId;

    public PlayerResponseCollectionDTO(Long id, List<String> markedResponses, Long score, Long playerId, Long quizId) {
        this.id = id;
        this.markedResponses = markedResponses;
        this.score = score;
        this.playerId = playerId;
        this.quizId = quizId;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public List<String> getMarkedResponses() {
        return markedResponses;
    }

    public Long getScore() {
        return score;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMarkedResponses(List<String> markedResponses) {
        this.markedResponses = markedResponses;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    @Override
    public String toString() {
        return "PlayerResponseCollectionDTO{" +
                "id=" + id +
                ", markedResponses=" + markedResponses +
                ", score=" + score +
                ", playerId=" + playerId +
                ", quizId=" + quizId +
                '}';
    }
}
