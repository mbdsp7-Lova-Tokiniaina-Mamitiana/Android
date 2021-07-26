package com.example.etu000603_android.data.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Match {

    private String id;
    private  Equipe domicile,exterieur;
    private double localistionX,localisationY;
    private  String description;
    private Timestamp date;
    private List<Pari> listPari = new ArrayList<>();
    private boolean termine;

    public boolean isTermine() {
        return termine;
    }

    public void setTermine(boolean termine) {
        this.termine = termine;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public  Match(){

    }

    public List<Pari> getListPari() {
        return listPari;
    }

    public void setListPari(List<Pari> listPari) {
        this.listPari = listPari;
    }
    
    public Match(Equipe domicile, Equipe exterieur, double localistionX, double localisationY) {
        this.domicile = domicile;
        this.exterieur = exterieur;
        this.localistionX = localistionX;
        this.localisationY = localisationY;
    }

    public Equipe getDomicile() {
        return domicile;
    }

    public void setDomicile(Equipe domicile) {
        this.domicile = domicile;
    }

    public Equipe getExterieur() {
        return exterieur;
    }

    public void setExterieur(Equipe exterieur) {
        this.exterieur = exterieur;
    }

    public double getLocalistionX() {
        return localistionX;
    }

    public void setLocalistionX(double localistionX) {
        this.localistionX = localistionX;
    }

    public double getLocalisationY() {
        return localisationY;
    }

    public void setLocalisationY(double localisationY) {
        this.localisationY = localisationY;
    }


}
