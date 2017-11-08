package com.example.android.amritaattendanceapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rando51 on 14/9/17.
 */

public class ClassListAdapter extends ArrayAdapter<ClassList> {

    private int mSelectedVariation;

    public ClassListAdapter(Activity context, ArrayList<ClassList> classLists){
        super(context,0,classLists);
        //mSelectedVariation = selectVariation;
    }


    int selectedPosition=0;
    @Override

    public View getView(final int position, View convertView, ViewGroup parent){
        View listItem = convertView;

        if(listItem==null)
        {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
           // RadioButton radioButton = (RadioButton) listItem.findViewById(radioButton);

        }

        ClassList classList = getItem(position);

        TextView department = (TextView)listItem.findViewById(R.id.dept);
        department.setText(classList.getDept());

        TextView Year = (TextView)listItem.findViewById(R.id.year);
        Year.setText(classList.getYear());

        TextView Section = (TextView)listItem.findViewById(R.id.section);
        Section.setText(classList.getSection());

        TextView Sno= (TextView)listItem.findViewById(R.id.sno);
        Sno.setText(classList.getfSerialNo());

        //CheckBox checkBox = (CheckBox) listItem.findViewById(R.id.check_button_class);

        //boolean attcheck = checkBox.isChecked();

        final RadioButton radioButton = (RadioButton) listItem.findViewById(R.id.radioButton);
//
        radioButton.setChecked(position == selectedPosition);
        radioButton.setTag(position);
//        if(position==mSelectedVariation) radioButton.setChecked(true);
//        else radioButton.setChecked(false);

        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = (Integer)view.getTag();
                ClassListAdapter.this.notifyDataSetChanged();
                LinearLayout l  =(LinearLayout) view.getParent();
                TextView tv = (TextView)l.getChildAt(4);
                FacultyDetails.pos = tv.getText().toString();
            }
        });


    return listItem;

    }
}
