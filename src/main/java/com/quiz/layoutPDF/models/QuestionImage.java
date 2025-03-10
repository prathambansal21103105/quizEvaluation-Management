package com.quiz.layoutPDF.models;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

@Document(collection = "images")
public class QuestionImage {
    @Id
    private String id;
    private byte[] imageData;

    public QuestionImage(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    @Override
    public String toString() {
        return "QuestionImage{" +
                "id='" + id + '\'' +
                ", imageData=" + Arrays.toString(imageData) +
                '}';
    }
}