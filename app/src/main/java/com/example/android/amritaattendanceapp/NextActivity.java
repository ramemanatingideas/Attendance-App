package com.example.android.amritaattendanceapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by kartik99 on 15/9/17.
 */

public class NextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        ArrayList<RadioTextList> StudentList = new ArrayList<RadioTextList>();
        StudentList.add(new RadioTextList("Rando","CB.EN.U4CSE16045",true));
        StudentList.add(new RadioTextList("Kartikeyan","CB.EN.U4CSE16019",true));
        StudentList.add(new RadioTextList("Wick","CB.EN.U4CSE16048",true));
        StudentList.add(new RadioTextList("Xavier","CB.EN.U4CSE16049",true));
        StudentList.add(new RadioTextList("Ross","CB.EN.U4CSE16050",true));
        StudentList.add(new RadioTextList("Perry","CB.EN.U4CSE16051",true));
        StudentList.add(new RadioTextList("Ananthakrishnapuram S Ram Narayanan","CB.EN.U4CSE16058",true));
        StudentList.add(new RadioTextList("Shakespeare","CB.EN.U4CSE16078",true));
        RadioListAdapter adapter = new RadioListAdapter(this,StudentList);

        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}