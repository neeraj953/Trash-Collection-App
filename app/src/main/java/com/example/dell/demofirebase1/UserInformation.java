package com.example.dell.demofirebase1;

/**
 * Created by Dell on 11/13/2018.
 */

public class UserInformation {

    public String Name;
    public String Phone_No;
    public String Address;
    public String Pin_Code;

    public UserInformation(){

    }

    public UserInformation(String name, String phone_No, String address, String pin_Code) {
        Name = name;
        Phone_No = phone_No;
        Address = address;
        Pin_Code = pin_Code;
    }
}
