package com.example.etu000603_android.data.model;

public class Match {

    private  Equipe domicile,exterieur;
    private double localistionX,localisationY;
    private  String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public  Match(){

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
