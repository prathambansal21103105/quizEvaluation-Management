package com.quiz.layoutPDF.models;

import java.util.List;

public class PlayerQuizStatsResponse {

    private String title;
    private Long score;
    private Long maxMarks;
    private List<String> markedResponses;
    private List<Long> scores;
    private String course;
    private String courseCode;
    private Long quizId;
    private Long responseId;
    private boolean evaluationMode;

    // Constructors
    public PlayerQuizStatsResponse() {
    }

    public PlayerQuizStatsResponse(String title, Long score, Long maxMarks, List<String> markedResponses, List<Long> scores, String course, String courseCode, Long quizId, Long responseId, boolean evaluationMode) {
        this.title = title;
        this.score = score;
        this.maxMarks = maxMarks;
        this.markedResponses = markedResponses;
        this.scores = scores;
        this.course = course;
        this.courseCode = courseCode;
        this.quizId = quizId;
        this.responseId = responseId;
        this.evaluationMode = evaluationMode;
    }

    // Getters and Setters

    public boolean isEvaluationMode() {
        return evaluationMode;
    }

    public void setEvaluationMode(boolean evaluationMode) {
        this.evaluationMode = evaluationMode;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Long getResponseId() {
        return responseId;
    }

    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(Long maxMarks) {
        this.maxMarks = maxMarks;
    }

    public List<String> getMarkedResponses() {
        return markedResponses;
    }

    public void setMarkedResponses(List<String> markedResponses) {
        this.markedResponses = markedResponses;
    }

    public List<Long> getScores() {
        return scores;
    }

    public void setScores(List<Long> scores) {
        this.scores = scores;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
}
