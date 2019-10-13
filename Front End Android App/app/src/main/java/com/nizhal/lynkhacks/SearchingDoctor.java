package com.nizhal.lynkhacks;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SearchingDoctor extends AppCompatActivity {
    private static int spash = 4000;
    InputStream is = null;
    String line = null;
    String result = null;
    StringBuilder sb;

    int flag = 0;

    Handler h = new Handler();
    int delay = 2000; //2 seconds
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        TextView tv = new TextView(getApplicationContext());
        Typeface typeface = ResourcesCompat.getFont(this, R.font.merlo_regular);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
        tv.setLayoutParams(lp);
        tv.setText("Searching for nearby doctors..."); // ActionBar title text
        tv.setTextSize(22);
        tv.setTextColor(Color.BLACK);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv.setTypeface(typeface);
        actionBar.setCustomView(tv);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        setContentView(R.layout.activity_searching_doctor);

        h.postDelayed(new Runnable() {
            public void run() {
                if(flag==0)
                    checkStatus();
                runnable=this;
                h.postDelayed(runnable, delay);
            }
        }, delay);

    }
    public void checkStatus()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://lyflinehack.herokuapp.com/userdetails");
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
            httpPost.setParams(httpParams);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            is = entity.getContent();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(),"Server Timed out",Toast.LENGTH_SHORT).show();
            System.out.println(e.getMessage());
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
            is.close();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(),"Server Timed out",Toast.LENGTH_SHORT).show();
            System.out.println(e.getMessage());
        }

        JSONObject jsonObject;
        try {
            JSONArray jArray = new JSONArray(result);
            if(jArray.length() == 0)
            {
                Intent intent = getIntent();
                final String stream = intent.getExtras().getString("stream");
                Intent in = new Intent(getBaseContext(),DoctorFound.class);
                in.putExtra("stream",stream);
                startActivity(in);
                flag++;
                finish();
            }
            else
            {
                //doSomething
            }
        } catch (JSONException e) {
            Toast.makeText(getBaseContext(),"Server Timed out",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
