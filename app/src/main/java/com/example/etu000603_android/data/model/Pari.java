package com.example.etu000603_android.data.model;

public class Pari {
    private Match match;
    private String description;
    private int scoreDomicile;
    private  int scoreExterieur;
    private  double coteDomicile,coteExterieur;

    public  Pari(){

    }

    public Pari(Match match, String description, int scoreDomicile, int scoreExterieur, double coteDomicile, double coteExterieur) {
        this.match = match;
        this.description = description;
        this.scoreDomicile = scoreDomicile;
        this.scoreExterieur = scoreExterieur;
        this.coteDomicile = coteDomicile;
        this.coteExterieur = coteExterieur;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getScoreDomicile() {
        return scoreDomicile;
    }

    public void setScoreDomicile(int scoreDomicile) {
        this.scoreDomicile = scoreDomicile;
    }

    public int getScoreExterieur() {
        return scoreExterieur;
    }

    public void setScoreExterieur(int scoreExterieur) {
        this.scoreExterieur = scoreExterieur;
    }

    public double getCoteDomicile() {
        return coteDomicile;
    }

    public void setCoteDomicile(double coteDomicile) {
        this.coteDomicile = coteDomicile;
    }

    public double getCoteExterieur() {
        return coteExterieur;
    }

    public void setCoteExterieur(double coteExterieur) {
        this.coteExterieur = coteExterieur;
    }
}
