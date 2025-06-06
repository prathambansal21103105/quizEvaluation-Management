package com.quiz.layoutPDF.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Player implements User {
    @Id
    private Long id;
    private String name;
    private String branch;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role = Role.PLAYER;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<PlayerResponse> playerResponses;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<ReviewRequest> reviewRequests;

    public Player() {
    }

    public Player(Long id, String name, String branch) {
        this.id = id;
        this.name = name;
        this.branch = branch;
        this.reviewRequests = new ArrayList<>();
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ReviewRequest> getReviewRequests() {
        return reviewRequests;
    }

    public void setReviewRequests(List<ReviewRequest> reviewRequests) {
        this.reviewRequests = reviewRequests;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPlayerResponses(List<PlayerResponse> playerResponses) {
        this.playerResponses = playerResponses;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getBranch() {
        return branch;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public List<PlayerResponse> getPlayerResponses() {
        return playerResponses;
    }

    @Override
    public String toString() {
        return "Player{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", branch='" + branch + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

}
