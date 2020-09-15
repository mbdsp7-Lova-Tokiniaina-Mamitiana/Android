package com.example.etu000603_android.data.model;

public class Address {
    private  String street="";
    private int number=0;
    private int postalCode=0;
    private  String city="";
    private  String country="";
    public Address(){

    }
    public Address(String street, int number, int postalCode, String city, String country) {
        this.street = street;
        if(street==null||street.equals("null")){
            this.street="";
        }
        this.number = number;
        this.postalCode = postalCode;
        this.city = city;

        if(city==null||city.equals("null")){
            this.city="";
        }
        this.country = country;

        if(country==null||country.equals("null")){
            this.country="";
        }
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String toString(){
        return ((number != 0)?number + " ":"")+""+street+ " "+((postalCode != 0)?postalCode + " ":"")+""+city+","+country;
    }
}
