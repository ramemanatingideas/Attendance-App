package com.example.android.amritaattendanceapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kartik99 on 15/9/17.
 */

public class RadioListAdapter extends ArrayAdapter<RadioTextList> {

   // static CheckBox checkBox;
    static  String name;
    static  boolean status;
    static int position_status;
  public static ArrayList<String>selectedItems = new ArrayList<String>();
    public static ArrayList<Boolean>checkBoxSelect = new ArrayList<Boolean>();

    public RadioListAdapter(Activity context, ArrayList<RadioTextList> radioListAdapters){
        super(context,0,radioListAdapters);
    }

    public static ArrayList<String> getSelectedItem()
    {
        return selectedItems;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent){
        View ListItem = convertView;

        if(ListItem==null){
            ListItem = LayoutInflater.from(getContext()).inflate(R.layout.singleitem,parent,false);
        }

        final RadioTextList radioList = getItem(position);
        TextView StudRoll  = (TextView) ListItem.findViewById(R.id.roll_no);
        StudRoll.setText(radioList.getRollNo());

        TextView StudName = (TextView) ListItem.findViewById(R.id.name);
        StudName.setText(radioList.getStudentName());

        final CheckBox checkBox = (CheckBox)ListItem.findViewById(R.id.check_button);
         String pos = "";

        /*checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(checkBox.isChecked())
                {
                    selectedItems.add(checkBox.getText().toString());
                }
                else
                {
                    selectedItems.remove(checkBox.getText().toString());
                }

            }
        });*/


            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //  for (int i = 0; i <size; i++) {
                    RelativeLayout rl = (RelativeLayout) view.getParent();
                    TextView tv = (TextView) rl.getChildAt(0);
                    name = tv.getText().toString();

                    if(checkBox.isChecked()) {
                        selectedItems.add(name);
                        int location_name  = selectedItems.indexOf(name);

                        status  = checkBox.isChecked();
                        checkBoxSelect.add(status);
                        position_status = checkBoxSelect.indexOf(status);
                    }

                    else if(!checkBox.isChecked()) {
                        status =  checkBox.isChecked();

                        //checkBoxSelect.add(status);
                        checkBoxSelect.set(checkBoxSelect.indexOf(position_status),status);
                        selectedItems.remove(name);
                    }
                    //  }



                }
            });







        return ListItem;
    }





}
