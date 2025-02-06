package com.quiz.layoutPDF.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionNum;
    @Column(name = "question", columnDefinition = "TEXT")
    private String question;
    private Long marks;
    private String answer;

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionNum='" + questionNum + '\'' +
                ", question='" + question + '\'' +
                ", marks=" + marks +
                ", answer='" + answer + '\'' +
                ", options=" + options +
                ", quiz=" + quiz +
                '}';
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @ElementCollection
    private List<String> options;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    @JsonIgnore
    private Quiz quiz;

    public Question(Long id, String questionNum, String question, Long marks, List<String> options) {
        this.id = id;
        this.questionNum = questionNum;
        this.question = question;
        this.marks = marks;
        this.options = options;
    }

    public Question() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(String questionNum) {
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

}
