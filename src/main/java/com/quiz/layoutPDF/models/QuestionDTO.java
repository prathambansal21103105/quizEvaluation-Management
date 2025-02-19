package com.quiz.layoutPDF.models;

import java.util.List;

public class QuestionDTO {
    private Long id;

    private String questionNum;
    private String question;
    private Long marks;
    private String answer;
    private List<String> options;
    private String image;

    public QuestionDTO(Long id, String questionNum, String question, Long marks, String answer, List<String> options, String image) {
        this.id = id;
        this.questionNum = questionNum;
        this.question = question;
        this.marks = marks;
        this.answer = answer;
        this.options = options;
        this.image = image;
    }

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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "QuestionDTO{" +
                "id=" + id +
                ", questionNum='" + questionNum + '\'' +
                ", question='" + question + '\'' +
                ", marks=" + marks +
                ", answer='" + answer + '\'' +
                ", options=" + options +
                ", image='" + image + '\'' +
                '}';
    }
}
