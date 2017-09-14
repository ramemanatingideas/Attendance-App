package com.example.android.amritaattendanceapp;

/**
 * Created by rando51 on 14/9/17.
 */

public class Faculty_Login {
    private String fName;
    private String fPassword;

    public Faculty_Login(String name,String pw)
    {
        fName = name;
        fPassword = pw;
    }

    public boolean validate(String facultyName,String facultyPassword)
    {
        if(fName.equals(facultyName)&&fPassword.equals(facultyPassword))
            return true;

        else
            return false;
    }

}
