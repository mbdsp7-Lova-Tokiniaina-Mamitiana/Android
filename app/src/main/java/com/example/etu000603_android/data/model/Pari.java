package com.example.etu000603_android.data.model;

public class Pari {
    private Match match;
    private String description;
    private  double cote;

    public  Pari(){

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


    public double getCote() {
        return cote;
    }

    public void setCote(double cote) {
        this.cote = cote;
    }
}
