package com.quiz.layoutPDF.models;

import java.util.List;

public class PlayerResponseDTO {
    private Long playerId;
    private Long quizId;
    private List<String> markedResponses;
    private Long score;

    public PlayerResponseDTO(Long playerId, Long quizId, List<String> markedResponses, Long score) {
        this.playerId = playerId;
        this.quizId = quizId;
        this.markedResponses = markedResponses;
        this.score = score;
    }

    public PlayerResponseDTO() {
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public List<String> getMarkedResponses() {
        return markedResponses;
    }

    public void setMarkedResponses(List<String> markedResponses) {
        this.markedResponses = markedResponses;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "PlayerResponseDTO{" +
                "playerId=" + playerId +
                ", quizId=" + quizId +
                ", markedResponses='" + markedResponses + '\'' +
                ", score=" + score +
                '}';
    }
}
