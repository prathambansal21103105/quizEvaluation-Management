package com.quiz.layoutPDF.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String course;
    private String courseCode;
    private Long maxMarks;
    private Boolean evaluationMode = false;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlayerResponse> playerResponses;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @JsonBackReference
    private Author author;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewRequest> reviewRequests;

    public Quiz() {
    }

    public Quiz(String title, String course, String courseCode, Long maxMarks, List<Question> questions, List<PlayerResponse> playerResponses, Author author) {
        this.title = title;
        this.course = course;
        this.courseCode = courseCode;
        this.maxMarks = maxMarks;
        this.questions = questions;
        this.playerResponses = playerResponses;
        this.author = author;
        this.evaluationMode = false;
        this.reviewRequests = new ArrayList<>();
    }

    public Quiz(Long id, String title, String course, String courseCode, Long maxMarks, Boolean evaluationMode, List<Question> questions, List<PlayerResponse> playerResponses, Author author) {
        this.id = id;
        this.title = title;
        this.course = course;
        this.courseCode = courseCode;
        this.maxMarks = maxMarks;
        this.evaluationMode = evaluationMode;
        this.questions = questions;
        this.playerResponses = playerResponses;
        this.author = author;
    }

    public Boolean getEvaluationMode() {
        return evaluationMode;
    }

    public void setEvaluationMode(Boolean evaluationMode) {
        this.evaluationMode = evaluationMode;
    }

    public List<ReviewRequest> getReviewRequests() {
        return reviewRequests;
    }

    public void setReviewRequests(List<ReviewRequest> reviewRequests) {
        this.reviewRequests = reviewRequests;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Long getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(Long maxMarks) {
        this.maxMarks = maxMarks;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<PlayerResponse> getPlayerResponses() {
        return playerResponses;
    }

    public void setPlayerResponses(List<PlayerResponse> playerResponses) {
        this.playerResponses = playerResponses;
    }

    public Author getAuthor() {
        return author;
    }
    public void setAuthor(Author author) {
        this.author = author;
    }

//    public Author getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(Author author) {
//        this.author = author;
//    }


    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", course='" + course + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", maxMarks=" + maxMarks +
                ", evaluationMode=" + evaluationMode +
//                ", questions=" + questions +
//                ", playerResponses=" + playerResponses +
//                ", author=" + author +
//                ", reviewRequests=" + reviewRequests +
                '}';
    }
}
