package com.quiz.layoutPDF.models;

import java.util.List;

public class PdfRequest {
    int flag;
    List<Integer> questionsPerPage;

    public PdfRequest() {
    }
    public PdfRequest(int flag, List<Integer> questionsPerPage) {
        this.flag = flag;
        this.questionsPerPage = questionsPerPage;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public List<Integer> getQuestionsPerPage() {
        return questionsPerPage;
    }

    public void setQuestionsPerPage(List<Integer> questionsPerPage) {
        this.questionsPerPage = questionsPerPage;
    }

    @Override
    public String toString() {
        return "pdfRequest{" +
                "flag=" + flag +
                ", questionsPerPage=" + questionsPerPage +
                '}';
    }
}
