package com.example.android.amritaattendanceapp;

/**
 * Created by rando51 on 14/9/17.
 */

public class ClassList {
    private String fYear;
    private String fDept;
    private String fSection;
    private boolean fStatus;

    public ClassList(String year,String Dept,String Section,boolean Status)
    {
        fYear = year;
        fDept = Dept;
        fSection = Section;
        fStatus = Status;
    }

    public String getYear() { return fYear; }

    public String getDept(){ return fDept; }

    public String getSection(){ return fSection; }

    public boolean getStatus(){ return  fStatus; }

}
