package com.example.etu000603_android.data.model;


public class Contact {

    public enum ContactType{
           COMMERCIAL,
           ACCOUNT_MANAGER
    }
    private int id;
    private String name;
    private String firstName;
    private String numero;
    private String email;
    private String address;
    private String username;

    private ContactType role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ContactType getRole() {
        return role;
    }

    public void setRole(ContactType role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        if(name==null||name.equals("null")){
            this.name="";
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
        if(firstName==null||firstName.equals("null")){
            this.firstName="";
        }
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
