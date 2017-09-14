package com.example.android.amritaattendanceapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rando51 on 14/9/17.
 */

public class ClassListAdapter extends ArrayAdapter<ClassList> {

    public ClassListAdapter(Activity context, ArrayList<ClassList>classLists){
        super(context,0,classLists);
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent){
        View listItem = convertView;

        if(listItem==null)
        {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);

        }

        ClassList classList = getItem(position);

        TextView department = (TextView)listItem.findViewById(R.id.dept);
        department.setText(classList.getDept());

        TextView Year = (TextView)listItem.findViewById(R.id.year);
        Year.setText(classList.getYear());

        TextView Section = (TextView)listItem.findViewById(R.id.section);
        Section.setText(classList.getSection());

        CheckBox checkBox = (CheckBox) listItem.findViewById(R.id.check_button_class);

        boolean attcheck = checkBox.isChecked();

        int count = 0;
        if(attcheck==true)
        {
            count+=1;
        }


    return listItem;

    }
}
