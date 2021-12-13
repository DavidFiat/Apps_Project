package com.example.apps_project.model;

public class Rate {

    private String id, opinion;
    private double rate;

    public Rate() {
    }

    public Rate(String id, String opinion, double rate) {
        this.id = id;
        this.opinion = opinion;
        this.rate = rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

}
