package com.example.apps_project.model;

public class Reserve {

    private String id;
    private String name;
    private String urlImageBarber;
    private String date;

    public Reserve() {
    }

    public Reserve(String id, String name, String urlImageBarber, String date) {
        this.id = id;
        this.name = name;
        this.urlImageBarber = urlImageBarber;
        this.date = date;
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

    public String getUrlImageBarber() {
        return urlImageBarber;
    }

    public void setUrlImageBarber(String urlImageBarber) {
        this.urlImageBarber = urlImageBarber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
