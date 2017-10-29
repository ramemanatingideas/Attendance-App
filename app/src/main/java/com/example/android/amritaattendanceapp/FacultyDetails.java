package com.example.android.amritaattendanceapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.android.amritaattendanceapp.MainActivity.facultyName;
import static com.example.android.amritaattendanceapp.MainActivity.status;


public class FacultyDetails extends AppCompatActivity {

    private static final String LOG_TAG =FacultyDetails.class.getSimpleName() ;
    private Button go_back;
    int count = 1;
    String C;
    EditText date;
    DatePickerDialog datePickerDialog;
    TextView Name_Field;
    TextView Dept;
    RadioButton rb;
    private ClassListAdapter adapter;
    ArrayList<ClassList> Classlist = new ArrayList<ClassList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_details);
        date = (EditText) findViewById(R.id.date);
        go_back = (Button)findViewById(R.id.goBack);
         Name_Field = (TextView)findViewById(R.id.name_field);
         Dept = (TextView)findViewById(R.id.department_text);
        rb=(RadioButton)findViewById(R.id.radioButton);
        ArrayList<RadioButton> listOfRadioButtons = new ArrayList<RadioButton>();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(FacultyDetails.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        //exceute the Class list async Task
        ClassDataAsyncTask asyncTask = new ClassDataAsyncTask();
        asyncTask.execute();


        //Name_Field  = (TextView)findViewById(R.id.Name_view);



        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                after_submit();
            }
        });
    }

    RadioButton listRadioButton = null;
    int listIndex = -1;

    public void onClickRadioButton(View v) {
        View vMain = ((View) v.getParent());
        // getParent() must be added 'n' times,
        // where 'n' is the number of RadioButtons' nested parents
        // in your case is one.

        // uncheck previous checked button.
        if (listRadioButton != null) listRadioButton.setChecked(false);
        // assign to the variable the new one
        listRadioButton = (RadioButton) v;
        // find if the new one is checked or not, and set "listIndex"
        if (listRadioButton.isChecked()) {
            listIndex = ((ViewGroup) vMain.getParent()).indexOfChild(vMain);
            Log.v(LOG_TAG,String.valueOf(listIndex));
        } else {
            listRadioButton = null;
            listIndex = -1;
        }
    }

    void setClassListAdapter(List<ClassList>classes)
    {
        //adapter.clear();
        ClassListAdapter adapter = new ClassListAdapter(this,Classlist);
        adapter.addAll(classes);
        ListView ls = (ListView)findViewById(R.id.list_item);
        ls.setAdapter(adapter);
    }

    public void after_submit()
    {

            Intent myIntent = new Intent(FacultyDetails.this, NextActivity.class);
            FacultyDetails.this.startActivity(myIntent);

    }

    public void percentager(View v)
    {
        //Intent myIntent = new Intent(FacultyDetails.this, PercentageList.class);
        //FacultyDetails.this.startActivity(myIntent);
    }


    //ASYNC TASK for the data fetching

    // Find a reference to the {@link ListView} in the layout
    // ArrayList<Earthquake> earthquakes = QueryUtils.fetchResponse();

        private  class ClassDataAsyncTask extends AsyncTask<URL,Void,List<ClassList>>
    {
        @Override
        protected List<ClassList> doInBackground(URL... urls) {
            String id = String.valueOf(status);
            URL url = createUrl("http://192.168.43.17:2000/classlist/"+id);
            if(url==null)
                return  null;

            List<ClassList> results =fetchResponse(url.toString());
            return results;
        }

        @Override
        protected void onPostExecute(List<ClassList> data) {
            // Clear the adapter of previous earthquake data
            setClassListAdapter(data);
            String fName = facultyName;
            Name_Field.setText(fName);
            //adapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
//                if (data != null && !data.isEmpty()) {
            // adapter.addAll(data);
//                }
        }
    }






    //==========THE FETCHING RESPONSE PART FROM THE SERVER===========================


    //method to create URL for the server
    private  URL createUrl(String StringUrls)
    {
        URL url = null;

        try
        {
            url = new URL(StringUrls);
        } catch (MalformedURLException m){
            Log.e(LOG_TAG, "createUrl: error in creating  Url  ",m );
        }

        return url;
    }



    //method to make http request for the server GET request to obtain the response from the server
    private  String makeHttpRequest(URL urls) throws IOException
    {
        String JsonResponse ="";
        if(urls==null) {
            return JsonResponse;
        }
        HttpURLConnection urlConnection =null;
        InputStream inputStream = null;

        try
        {
            urlConnection = (HttpURLConnection) urls.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            if(urlConnection.getResponseCode()==200)
            {
                inputStream = urlConnection.getInputStream();
                JsonResponse  = readFromStream(inputStream);
            }

        }catch(IOException e) {
            Log.e(LOG_TAG, "makeHttpRequest: error making hhtp request "+urlConnection.getResponseCode() );

        }

        finally {

            if(inputStream!=null)
                inputStream.close();

            if(urlConnection!=null)
                urlConnection.disconnect();
        }

        return JsonResponse;

    }


    //method to read the data from the server using input stream
    private String readFromStream(InputStream inputStream) throws IOException
    {
        StringBuilder output_response = new StringBuilder();
        if(inputStream!=null)
        {
            InputStreamReader isr = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();

            while(line!=null)
            {
                output_response.append(line);
                line  = br.readLine();
            }

            br.close();


        }

        return output_response.toString();

    }

    // fetch response method to get respnse from the json from the api

    public  List<ClassList> fetchResponse(String requestURL)
    {
        Log.v(LOG_TAG,"fetchResponse called");

        URL url = createUrl(requestURL);
        String JsonResponse = "";

        try{
            JsonResponse =  makeHttpRequest(url);
        }catch (IOException e)
        {
            Log.e(LOG_TAG, "fetchResponse: erorr in making HTTP request", e);
        }

        List<ClassList> classes = extractFeatureFromJSON(JsonResponse);

        return classes;
    }

    private  List<ClassList> extractFeatureFromJSON(String classItemsJSON) {


        if(TextUtils.isEmpty(classItemsJSON))
            return null;

        // Create an empty ArrayList that we can start adding earthquakes to
        List<ClassList>classes  = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONObject root = new JSONObject(classItemsJSON);
            JSONArray classArray  = root.getJSONArray("cls");

            for(int i=0;i<classArray.length();i++) {
                JSONObject classArrayJSONObject = classArray.getJSONObject(i);

                String branch = classArrayJSONObject.getString("branch");
                String section = classArrayJSONObject.getString("section");
                String semester = classArrayJSONObject.getString("semester");
                String sno = classArrayJSONObject.getString("sno");

                ClassList classList = new ClassList(semester,branch,section,false);
                classes.add(classList);
                // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
               }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("FacultyDetails", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of classes
        return classes;
    }







}
