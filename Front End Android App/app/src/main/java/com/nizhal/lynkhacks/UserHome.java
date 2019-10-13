package com.nizhal.lynkhacks;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UserHome extends AppCompatActivity {

    ArrayList<String> professNames = new ArrayList<>();
    ArrayList<String> professImages = new ArrayList<>();

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
        tv.setText(R.string.title_name_colored); // ActionBar title text
        tv.setTextSize(22);
        tv.setTextColor(Color.BLACK);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv.setTypeface(typeface);
        actionBar.setCustomView(tv);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        setContentView(R.layout.activity_user_home);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray userArray = obj.getJSONArray("profession");
            for (int i = 0; i < userArray.length(); i++) {
                JSONObject userDetail = userArray.getJSONObject(i);
                professNames.add(userDetail.getString("name"));
                professImages.add(userDetail.getString("image"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StreamAdapter streamAdapter = new StreamAdapter(UserHome.this, professNames, professImages);
        recyclerView.setAdapter(streamAdapter); // set the Adapter to RecyclerView
    }
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("stream.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
