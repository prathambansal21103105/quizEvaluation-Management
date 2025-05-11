package com.quiz.layoutPDF.models;

import java.util.List;

public class ReviewWithResponseDTO {

    private Long reviewRequestId;
    private Long playerResponseId;
    private Long playerId;
    private Long quizId;
    private List<String> markedResponses;
    private String description;

    // Constructor
    public ReviewWithResponseDTO(Long reviewRequestId, Long playerResponseId, Long playerId, Long quizId,
                                 List<String> markedResponses, String description) {
        this.reviewRequestId = reviewRequestId;
        this.playerResponseId = playerResponseId;
        this.playerId = playerId;
        this.quizId = quizId;
        this.markedResponses = markedResponses;
        this.description = description;
    }

    // Getters and Setters
    public Long getReviewRequestId() {
        return reviewRequestId;
    }

    public void setReviewRequestId(Long reviewRequestId) {
        this.reviewRequestId = reviewRequestId;
    }

    public Long getPlayerResponseId() {
        return playerResponseId;
    }

    public void setPlayerResponseId(Long playerResponseId) {
        this.playerResponseId = playerResponseId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
