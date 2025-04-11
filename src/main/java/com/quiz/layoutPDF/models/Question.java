package com.quiz.layoutPDF.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long questionNum;
    @Column(name = "question", columnDefinition = "TEXT")
    private String question;
    private Long marks;
    private String answer;
    private List<String> options;
    private String imageId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    @JsonIgnore
    private Quiz quiz;

    public Question() {}

    public Question(Long id, Long questionNum, String question, Long marks, List<String> options) {
        this.id = id;
        this.questionNum = questionNum;
        this.question = question;
        this.marks = marks;
        this.options = options;
    }

    public Question(Long id, Long questionNum, String question, Long marks, String answer, List<String> options, String imageId, Quiz quiz) {
        this.id = id;
        this.questionNum = questionNum;
        this.question = question;
        this.marks = marks;
        this.answer = answer;
        this.options = options;
        this.imageId = imageId;
        this.quiz = quiz;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(Long questionNum) {
        this.questionNum = questionNum;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Long getMarks() {
        return marks;
    }

    public void setMarks(Long marks) {
        this.marks = marks;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionNum=" + questionNum +
                ", question='" + question + '\'' +
                ", marks=" + marks +
                ", answer='" + answer + '\'' +
                ", options=" + options +
                ", imageId='" + imageId + '\'' +
                '}';
    }

}
