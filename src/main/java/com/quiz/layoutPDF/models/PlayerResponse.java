package com.quiz.layoutPDF.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "player_response")
public class PlayerResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long score= 0L;
    private List<String> markedResponses = new ArrayList<>();
    private List<Long> scores = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    @JsonIgnore
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    @JsonIgnore
    private Quiz quiz;

    public PlayerResponse(Long id, Long score, List<String> markedResponses, Player player, Quiz quiz, List<Long> scores) {
        this.id = id;
        this.score = score;
        this.markedResponses = markedResponses;
        this.player = player;
        this.quiz = quiz;
        this.scores = scores;
    }

    public PlayerResponse() {
    }

    public List<Long> getScores() {
        return scores;
    }

    public void setScores(List<Long> scores) {
        this.scores = scores;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public List<String> getMarkedResponses() {
        return markedResponses;
    }

    public void setMarkedResponses(List<String> markedResponses) {
        this.markedResponses = markedResponses;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public String toString() {
        return "PlayerResponse{" +
                "id=" + id +
                ", score=" + score +
                ", markedResponses=" + markedResponses +
                ", scores=" + scores +
//                ", player=" + player +
//                ", quiz=" + quiz +
                '}';
    }

}
