package com.example.jewar.fitnessinstructor;

/**
 * Created by jewar
 */
public class User {

    //Declare variables
    private String _name,_email, _phone, _address;
    private int _id;

    public User (int id, String name, String email, String phone, String address) {
        _id = id;
        _name = name;
        _email = email;
        _phone = phone;
        _address = address;

    }

    //create Getters and setters
    public int getId() {
        return _id;
    }
    public String getName(){
        return _name;
    }

    public String getEmail(){
        return _email;
    }

    public String getPhone(){
        return _phone;
    }

    public String getAddress(){
        return  _address;
    }

}

