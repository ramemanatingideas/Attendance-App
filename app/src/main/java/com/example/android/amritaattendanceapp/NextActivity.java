package com.example.android.amritaattendanceapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

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
import java.util.List;

import static com.example.android.amritaattendanceapp.FacultyDetails.pos;

/**
 * Created by kartik99 on 15/9/17.
 */

public class NextActivity extends AppCompatActivity {


    public static final java.lang.String LOG_TAG =NextActivity.class.getSimpleName() ;
    static String class_id="";
    public String name="";
    public ListView listView;
    public  static int size;
    public Button submitbb;

//    SparseBooleanArray sparseBooleanArray;
        ArrayList<Boolean> sparseBooleanArray;
        ArrayList<RadioTextList> StudentList = new ArrayList<RadioTextList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        submitbb = (Button)findViewById(R.id.getcount);

       /* submitbb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Object selected;
                int cntChoice  =5;
                sparseBooleanArray.add(true);
                sparseBooleanArray.add(false);
//                 sparseBooleanArray = {true,false};
                for(int i =0;i<cntChoice;i++)
                {
                    if(sparseBooleanArray.get(i)){

                       // String item = listView.getAdapter().getItem(
                               //sparseBooleanArray.keyAt(i).toString();
                        //Log.i(LOG_TAG,item + " was selected");

                            name += listView.getItemAtPosition(i).toString()+"\n";

//                            RadioTextList obj = (RadioTextList)selected;
//                            name = obj.getStudentName();
                        Log.i(LOG_TAG,name);
                    }

                }
                Toast.makeText(NextActivity.this,name, Toast.LENGTH_SHORT).show();

            }
        });*/


        StudentDataAsyncTask asyncTask =  new StudentDataAsyncTask();
        asyncTask.execute();

        submitbb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<RadioListAdapter.selectedItems.size();i++)
                {

                }
            }
        });


    }


    void setStudentListAdapter(final List<RadioTextList>Students)
    {
        RadioListAdapter adapter = new RadioListAdapter(this,StudentList);
        adapter.addAll(Students);
        size = Students.size();
        listView = (ListView)findViewById(R.id.listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);



//        checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String selected  ="";
//                int cntChoice  =listView.getCount();
//                SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
//                for(int i =0;i<cntChoice;i++)
//                {
//                    if(sparseBooleanArray.get(i)){
//                        selected +=listView.getItemAtPosition(i).toString()+"\n";
//                    }
//                }
//                Log.v(LOG_TAG,selected);
//            }
//        });



    }


    //ASYNC TASK for the data fetching

    // Find a reference to the {@link ListView} in the layout
    // ArrayList<Earthquake> earthquakes = QueryUtils.fetchResponse();

    //private class UpdateStatusAsyncTask extends AsyncTask<URL,>

    private  class StudentDataAsyncTask extends AsyncTask<URL,Void,List<RadioTextList>>
    {
        @Override
        protected List<RadioTextList> doInBackground(URL... urls) {
            String class_number = String.valueOf(String.valueOf(pos));
            if(class_number==null)
                Log.e(LOG_TAG, "doInBackground: cladd id is nULL bIthces");
            URL url = createUrl("http://192.168.43.17:2000/studentlist/"+class_number);
            if(url==null)
                return  null;

            List<RadioTextList> results = fetchResponse(url.toString());
            return results;
        }

        @Override
        protected void onPostExecute(List<RadioTextList> data) {
            // Clear the adapter of previous earthquake data
            setStudentListAdapter(data);
        }
    }


    //================SERVER SIDE FETCHING OF THE STUDENT LIST FOR SEPCIFIC CLASS============

    //method to create URL for the server
    private URL createUrl(String StringUrls)
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

    public List<RadioTextList> fetchResponse(String requestURL)
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

        List<RadioTextList> students = extractFeatureFromJSON(JsonResponse);

        return students;
    }

    private  List<RadioTextList> extractFeatureFromJSON(String studentItemJSON) {


        if(TextUtils.isEmpty(studentItemJSON))
            return null;

        // Create an empty ArrayList that we can start adding earthquakes to
        List<RadioTextList>students  = new ArrayList<>();

        try {

            JSONObject root = new JSONObject(studentItemJSON);
            JSONArray studentArray  = root.getJSONArray("std");

            for(int i=0;i<studentArray.length();i++) {
                JSONObject studentArrayJSONObject = studentArray.getJSONObject(i);

                String name = studentArrayJSONObject.getString("name");
                class_id = studentArrayJSONObject.getString("class_id");
                String id = studentArrayJSONObject.getString("id");

                RadioTextList studentList = new RadioTextList(name,id,false);
                students.add(studentList);
                // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("FacultyDetails", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of classes
        return students;
    }




}