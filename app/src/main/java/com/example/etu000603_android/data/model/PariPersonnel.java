package com.example.etu000603_android.data.model;

import java.sql.Timestamp;


public class PariPersonnel extends Pari {
    private String user;
    private double mise;
    private Timestamp date;
    private PariStatut statut;


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public double getMise() {
        return mise;
    }

    public void setMise(double mise) {
        this.mise = mise;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public PariStatut getStatut() {
        return statut;
    }

    public void setStatut(PariStatut statut) {
        this.statut = statut;
    }
}
