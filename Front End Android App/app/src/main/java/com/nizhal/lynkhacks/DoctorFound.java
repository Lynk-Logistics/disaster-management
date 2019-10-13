package com.nizhal.lynkhacks;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DoctorFound extends AppCompatActivity {
    TextView spec_doc;
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
        tv.setText("Doctor Profile"); // ActionBar title text
        tv.setTextSize(22);
        tv.setTextColor(Color.BLACK);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv.setTypeface(typeface);
        actionBar.setCustomView(tv);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        setContentView(R.layout.activity_doctor_found);

        spec_doc = findViewById(R.id.spec_doc);
        Intent intent = getIntent();
        String stream = intent.getExtras().getString("stream");

        spec_doc.setText(stream);

    }
}
