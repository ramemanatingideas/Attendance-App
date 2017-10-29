package com.example.android.amritaattendanceapp;

/**
 * Created by rando51 on 14/9/17.
 */

public class Faculty_Login {
    private String fName;
    private String fPassword;

    private String OriginalName = "admin";
    private String OriginalPass = "admin";

    public Faculty_Login(String name,String pw)
    {
        fName = name;
        fPassword = pw;
    }

//    public boolean validate(EditText ed1,EditText ed2)
//    {
//        if(ed1.getText().toString().equals("ram")&&ed2.getText().toString().equals("abcd"))
//            return true;
//
//        else
//            return false;
//    }

    public boolean validate(String userName,String userPassWord)
    {
        if(userName.equals(OriginalName)&&userPassWord.equals(OriginalPass))
            return true;

        else
            return false;
    }

}


