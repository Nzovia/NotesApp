package com.nicholas.notesapp;

public class UserData {

    // ths is the user object class that will that will be populated with user data and passed to the firebase
    public String name,email, phone;

    //create an empty public constructor
    public UserData(){

    }
    //create a parameterised constructor
    public UserData(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



}

