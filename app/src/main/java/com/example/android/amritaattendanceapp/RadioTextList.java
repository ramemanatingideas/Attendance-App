package com.example.android.amritaattendanceapp;

/**
 * Created by kartik99 on 15/9/17.
 */

public class RadioTextList {
    private String StudentName;
    private String rollNo;
    private boolean choice;

    RadioTextList(String stuName,String roll,boolean check_radio)
    {
        StudentName =stuName;
        rollNo = roll;
        choice = check_radio;
    }

    public String getStudentName()
    {
        return StudentName;
    }
    public String getRollNo()
    {
        return rollNo;
    }

    public boolean getRadio()
    {
        return choice;
    }
}
