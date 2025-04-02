package com.quiz.layoutPDF.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "author")
public class Author implements User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String email;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    List<Quiz> quizzes;

    public Author() {
    }

    public Author(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public Author(List<Quiz> quizzes, String email, String password, String name, Long id) {
        this.quizzes = quizzes;
        this.email = email;
        this.password = password;
        this.name = name;
        this.id = id;
    }



    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public List<Quiz> getQuizzes() {
//        return quizzes;
//    }
//
//    public void setQuizzes(List<Quiz> quizzes) {
//        this.quizzes = quizzes;
//    }

    @Override
    public String toString() {
        return "Author{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ". quizzes=" + quizzes + '\'' +
                '}';
    }
}
