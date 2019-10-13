package com.dwarsoftgames.dwarsoft_lynk_hackathon.Activity.Volunteer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.dwarsoftgames.dwarsoft_lynk_hackathon.Activity.Groups.Groups;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.R;
import com.google.android.material.button.MaterialButton;

public class VolunteerDashboard extends AppCompatActivity {

    private MaterialButton btHelp, btResources, btGroups, btPosts, btOrganization, btFunds;
    private ImageView btHelp1, btResources1, btGroups1, btPosts1, btOrganization1, btFunds1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_dashboard);

        Window window = getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(getResources().getColor(R.color.white));

        init();
        setOnClicks();
    }

    private void init() {
        btHelp = findViewById(R.id.btHelp);
        btResources = findViewById(R.id.btResources);
        btGroups = findViewById(R.id.btGroups);
        btPosts = findViewById(R.id.btPosts);
        btOrganization = findViewById(R.id.btOrganization);
        btFunds = findViewById(R.id.btFunds);

        btHelp1 = findViewById(R.id.btHelp1);
        btResources1 = findViewById(R.id.btResources1);
        btGroups1 = findViewById(R.id.btGroups1);
        btPosts1 = findViewById(R.id.btPosts1);
        btOrganization1 = findViewById(R.id.btOrganization1);
        btFunds1 = findViewById(R.id.btFunds1);
    }

    private void setOnClicks() {

        btHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VolunteerDashboard.this, VictimHelpMap.class);
                startActivity(intent);
            }
        });

        btResources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VolunteerDashboard.this, ShareResources.class);
                startActivity(intent);
            }
        });

        btGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VolunteerDashboard.this, Groups.class);
                startActivity(intent);
            }
        });

        btPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VolunteerDashboard.this, VolunteerPosts.class);
                startActivity(intent);
            }
        });

        btOrganization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VolunteerDashboard.this, OrganizationsList.class);
                startActivity(intent);
            }
        });

        btFunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VolunteerDashboard.this, Contribute.class);
                startActivity(intent);
            }
        });


        btHelp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VolunteerDashboard.this, VictimHelpMap.class);
                startActivity(intent);
            }
        });

        btResources1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VolunteerDashboard.this, ShareResources.class);
                startActivity(intent);
            }
        });

        btGroups1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VolunteerDashboard.this, Groups.class);
                startActivity(intent);
            }
        });

        btPosts1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VolunteerDashboard.this, VolunteerPosts.class);
                startActivity(intent);
            }
        });

        btOrganization1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VolunteerDashboard.this, OrganizationsList.class);
                startActivity(intent);
            }
        });

        btFunds1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VolunteerDashboard.this, Contribute.class);
                startActivity(intent);
            }
        });
    }
}