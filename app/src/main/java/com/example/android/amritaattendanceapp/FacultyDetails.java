package com.example.android.amritaattendanceapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class FacultyDetails extends AppCompatActivity {

    private Button go_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_details);

        go_back = (Button)findViewById(R.id.goBack);

        ArrayList<ClassList> Classlist = new ArrayList<ClassList>();
        Classlist.add(new ClassList("2","CSE","A",false));
        Classlist.add(new ClassList("3","ECE","B",false));
        Classlist.add(new ClassList("1","MEE","C",false));
        Classlist.add(new ClassList("4","CHE","E",false));
        Classlist.add(new ClassList("2","CSE","A",false));
        Classlist.add(new ClassList("3","ECE","B",false));
        Classlist.add(new ClassList("1","MEE","C",false));
        Classlist.add(new ClassList("4","CHE","E",false));
        Classlist.add(new ClassList("2","CSE","A",false));
        Classlist.add(new ClassList("3","ECE","B",false));
        Classlist.add(new ClassList("1","MEE","C",false));
        Classlist.add(new ClassList("4","CHE","E",false));
        Classlist.add(new ClassList("2","CSE","A",false));
        Classlist.add(new ClassList("3","ECE","B",false));
        Classlist.add(new ClassList("1","MEE","C",false));
        Classlist.add(new ClassList("4","CHE","E",false));

        ClassListAdapter adapter = new ClassListAdapter(this,Classlist);
        ListView ls = (ListView)findViewById(R.id.list_item);
        ls.setAdapter(adapter);

        Utility.setListViewHeightBasedOnChildren(ls);

        Button submit = (Button)findViewById(R.id.goBack);

        


        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }


}
