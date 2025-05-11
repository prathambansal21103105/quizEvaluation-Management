package com.quiz.layoutPDF.models;

public class ReviewRequestDTO {

    String description;
    String email;
    Long quizId;

    public ReviewRequestDTO() {
    }

    public ReviewRequestDTO(String description, String email, Long quizId) {
        this.description = description;
        this.email = email;
        this.quizId = quizId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    @Override
    public String toString() {
        return "ReviewRequestDTO{" +
                "description='" + description + '\'' +
                ", email=" + email +
                ", quizId=" + quizId +
                '}';
    }
}
