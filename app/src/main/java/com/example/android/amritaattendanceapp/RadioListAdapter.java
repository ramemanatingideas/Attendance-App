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
 * Created by kartik99 on 15/9/17.
 */

public class RadioListAdapter extends ArrayAdapter<RadioTextList> {

    public RadioListAdapter(Activity context, ArrayList<RadioTextList> radioListAdapters){
        super(context,0,radioListAdapters);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View ListItem = convertView;

        if(ListItem==null){
            ListItem = LayoutInflater.from(getContext()).inflate(R.layout.singleitem,parent,false);
        }

        RadioTextList radioList = getItem(position);
        TextView StudRoll  = (TextView) ListItem.findViewById(R.id.roll_no);
        StudRoll.setText(radioList.getRollNo());

        TextView StudName = (TextView) ListItem.findViewById(R.id.name);
        StudName.setText(radioList.getStudentName());


        return ListItem;
    }





}
