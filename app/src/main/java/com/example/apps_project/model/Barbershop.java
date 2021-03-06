package com.example.apps_project.model;

import android.util.Log;

import java.io.Serializable;

public class Barbershop implements Serializable {

    private String id;
    private String name;
    private String rate;
    private String urlImage;

    public Barbershop() {
    }

    public Barbershop(String id, String name, String rate, String urlImage) {
        this.id = id;
        this.name = name;
        this.rate = rate;
        this.urlImage = urlImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
