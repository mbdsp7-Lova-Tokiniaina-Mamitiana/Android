package com.example.BetSoccer_android.data.model;

import android.graphics.drawable.Drawable;

public class Equipe {
    private String id;
    private String name;
    private  String description;
    private  String  url_image;
    private Drawable logo_drawable=null;

    public Drawable getLogo_drawable() {
        return logo_drawable;
    }

    public void setLogo_drawable(Drawable logo_drawable) {
        this.logo_drawable = logo_drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }
    public Equipe(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Equipe(String name, String description, String url_image) {
        this.name = name;
        this.description = description;
        this.url_image = url_image;
    }
}
