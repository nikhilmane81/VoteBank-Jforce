package com.nikhil.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Candidate
{

    @Id
    private Integer id;

    private String name;

    private Integer vote;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
    }
}
