package com.example.apps_project.model;

import java.io.Serializable;

public class Reserve implements Serializable {

    private String id;
    private String idBarberShop;
    private String idBarbero;
    private String name;
    private String urlImageBarber;
    private String date;

    public Reserve() {
    }

    public Reserve(String id, String name, String urlImageBarber, String date,String idBarberShop,String idBarbero) {
        this.id = id;
        this.name = name;
        this.urlImageBarber = urlImageBarber;
        this.date = date;
        this.idBarbero=idBarbero;
        this.idBarberShop=idBarberShop;
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

    public String getIdBarberShop() {
        return idBarberShop;
    }

    public String getIdBarbero() {
        return idBarbero;
    }
}
