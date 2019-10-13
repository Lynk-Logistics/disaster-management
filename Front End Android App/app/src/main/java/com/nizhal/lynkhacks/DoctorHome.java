package com.nizhal.lynkhacks;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DoctorHome extends AppCompatActivity {
    private LoginTabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
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
        tv.setText(R.string.app_name_colored); // ActionBar title text
        tv.setTextSize(22);
        tv.setTextColor(Color.BLACK);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv.setTypeface(typeface);
        actionBar.setCustomView(tv);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        setContentView(R.layout.activity_doctor_home);

        viewPager = findViewById(R.id.docViewPager);
        tabLayout = findViewById(R.id.doctorTab);
        adapter = new LoginTabAdapter(getSupportFragmentManager());
        adapter.addFragment(new DoctorPastFragment(), "Records");
        adapter.addFragment(new DoctorProfileFragment(), "Profile");
        adapter.addFragment(new DoctorRequest(), "Requests");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
