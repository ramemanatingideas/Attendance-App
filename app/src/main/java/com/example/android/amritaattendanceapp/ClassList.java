package com.example.android.amritaattendanceapp;

/**
 * Created by rando51 on 14/9/17.
 */

public class ClassList {
    private String fYear;
    private String fDept;
    private String fSection;
    private boolean fStatus;
    private String fSerialNo;

    public ClassList(String year,String Dept,String Section,boolean Status,String serialNo)
    {
        fYear = year;
        fDept = Dept;
        fSection = Section;
        fStatus = Status;
        fSerialNo = serialNo;
    }

    public String getYear() { return fYear; }

    public String getDept(){ return fDept; }

    public String getSection(){ return fSection; }

    public boolean getStatus(){ return  fStatus; }

    public void setStatus(boolean b)
    {
        fStatus=b;
    }

    public String getfSerialNo(){return fSerialNo;}
}
