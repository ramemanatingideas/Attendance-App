package com.example.android.amritaattendanceapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG =MainActivity.class.getSimpleName() ;
    private Button mBtLaunchActivity;
    public static int status = 0;
    public static String facultyName=null;
    static EditText ed1,ed2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtLaunchActivity = (Button) findViewById(R.id.loginBTN);
         ed1 = (EditText)findViewById(R.id.reg_text_view);
         ed2 = (EditText) findViewById(R.id.pw_text_view);


        mBtLaunchActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServerLogin loginAsync = new ServerLogin();
                loginAsync.execute();

            }
        });
    }




    private class ServerLogin extends AsyncTask<URL,Void, Integer> {
        @Override
        protected Integer doInBackground(URL... urls) {

            URL url = createUrl("http://192.168.43.17:2000/login");

            if(url==null)
                Log.v(TAG, "doInBackground: Url not connected");
            // URL jsonInput=params[0];
            String jsonResponseCode ="";

            try{
                jsonResponseCode = makeHttpRequest(url);

            }catch (IOException e)
            {
                Log.e(TAG, "doInBackground: Error JsonResponse",e );
            } catch (JSONException e) {
                e.printStackTrace();
            }



            String responseCode = jsonResponseCode;

            try {
                JSONArray jsonRespArray = new JSONArray(responseCode);
                JSONObject objJson  = jsonRespArray.getJSONObject(0);
                status = objJson.getInt("value");
                //JSONObject jsonName = new JSONObject(responseCode);
                JSONObject objJson1  = jsonRespArray.getJSONObject(1);
                facultyName = objJson1.getString("name");

                //facultyName  =nameField.toString();
                Log.i(TAG, "doInBackground: facultyNmae Json Response"+facultyName);

            } catch (JSONException e) {
                e.printStackTrace();
            }


            return status;
        }


        @Override
        protected void onPostExecute(Integer status) {

            if(status!=0){
                Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                launchActivity();
            }

            else
                Toast.makeText(getApplicationContext(), "Wrong credentails", Toast.LENGTH_SHORT).show();

            super.onPostExecute(status);
        }
    }



    private URL createUrl(String urlString)
    {
        URL url = null;

        try{
            url = new URL(urlString);
        }catch (MalformedURLException M)
        {
            Log.e(TAG, "createUrl: Malformed Url Exception",M );
            return null;
        }

        return url;
    }

    private String makeHttpRequest(URL url)throws IOException ,JSONException {

        //String jsonResponseToSend = "";
        String jsonResp ="";
        if (url == null) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        //String jsonResp = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.connect();


            JSONObject jsonObject = new JSONObject();

            final String uName = ed1.getText().toString();
            final String uPass = ed2.getText().toString();

            try{
                jsonObject.put("username",uName);
                jsonObject.put("password",uPass);

                Log.v(TAG,jsonObject.toString());
            }catch (JSONException J){
                Log.e(TAG, "JsonInput:JsonOBject convertion error ",J );
            }

            OutputStream os = urlConnection.getOutputStream();

            Writer writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonObject.toString());
            writer.close();
            os.close();

            if(urlConnection.getResponseCode()==200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String outLine ;

                while ((outLine = br.readLine())!=null) {
                    stringBuilder.append(outLine);
                    //outLine = br.readLine();
                }
                br.close();
                jsonResp = stringBuilder.toString();
                Log.e(TAG,jsonResp.toString());

            }
            else
            {
                Log.e(TAG,"POst request couldnt work ");
            }
        } catch (IOException e) {
            Log.e(TAG, "makeHttpRequest: IOException occured", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

        }

        return jsonResp;

    }

    private void launchActivity() {

        Intent intent = new Intent(this, FacultyDetails.class);
        startActivity(intent);
    }

}




