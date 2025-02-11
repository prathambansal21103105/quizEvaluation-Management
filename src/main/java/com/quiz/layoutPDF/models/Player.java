package com.quiz.layoutPDF.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Player {
    @Id
    private Long id;
    private String name;
    private String branch;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    List<PlayerResponse> playerResponses;

    public Player(Long id, String name, String branch, List<PlayerResponse> playerResponses) {
        this.id = id;
        this.name = name;
        this.branch = branch;
        this.playerResponses = playerResponses;
    }

    public Player() {
    }

    public Player(Long playerId, String unknown, String unknown1) {
        this.id = playerId;
        this.name = unknown;
        this.branch = unknown1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<PlayerResponse> getPlayerResponses() {
        return playerResponses;
    }

    public void setPlayerResponses(List<PlayerResponse> playerResponses) {
        this.playerResponses = playerResponses;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", branch='" + branch + '\'' +
                '}';
    }
}
