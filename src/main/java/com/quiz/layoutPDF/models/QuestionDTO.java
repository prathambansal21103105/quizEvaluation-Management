package com.quiz.layoutPDF.models;

import java.util.List;

public class QuestionDTO {
    private Long id;

    private Long questionNum;
    private String question;
    private Long marks;
    private String answer;
    private List<String> options;
    private String imageId;

    public QuestionDTO(Long id, Long questionNum, String question, Long marks, String answer, List<String> options, String imageId) {
        this.id = id;
        this.questionNum = questionNum;
        this.question = question;
        this.marks = marks;
        this.answer = answer;
        this.options = options;
        this.imageId = imageId;
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

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
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
                ", imageId='" + imageId + '\'' +
                '}';
    }
}
