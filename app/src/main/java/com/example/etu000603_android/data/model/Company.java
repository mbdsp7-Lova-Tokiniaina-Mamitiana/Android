package com.example.etu000603_android.data.model;

import android.graphics.drawable.Drawable;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class Company {
    private int id;
    private String name;
    private Address address;
    private String url_logo;
    private List<Contact> listeDesContacts=new ArrayList<Contact>();
    private Drawable logo_drawable=null;

    public Drawable getLogo_drawable() {
        return logo_drawable;
    }

    public void setLogo_drawable(Drawable logo_drawable) {
        this.logo_drawable = logo_drawable;
    }

    public Company(int id, String name, Address address, String url_logo) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.url_logo = url_logo;
        if(name==null||name.equals("null")){
            this.name="";
        }


        if(url_logo==null||url_logo.equals("null")){
            this.url_logo="";
        }

    }
public Company(){

}
    public Company(String name, Address address, String url_logo) {
        this.name = name;
        this.address = address;
        this.url_logo = url_logo;
        if(name==null||name.equals("null")){
            this.name="";
        }


        if(url_logo==null||url_logo.equals("null")){
            this.url_logo="";
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
        if(name==null||name.equals("null")){
            this.name="";
        }

    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getUrl_logo() {

        return url_logo;
    }

    public void setUrl_logo(String url_logo) {
        this.url_logo = url_logo;
        if(url_logo==null||url_logo.equals("null")){
            this.url_logo="";
        }

    }

    public List<Contact> getListeDesContacts() {
        return listeDesContacts;
    }

    public void setListeDesContacts(List<Contact> listeDesContacts) {
        this.listeDesContacts = listeDesContacts;
    }
    public void addContact(Contact contact){
        listeDesContacts.add(contact);
    }
    private   String removeDiacriticalMarks(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
    public boolean contains(String companyName){

        String comparisonName = removeDiacriticalMarks(name); // Joao

        return comparisonName.toLowerCase().trim().contains(removeDiacriticalMarks(companyName.toLowerCase().trim()));

    }
    public boolean lesserThan(Company c){


        return  this.getName().length()<c.getName().length();
    }
}
