package com.lynkhack;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class RequestHelpActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    EditText name;
    EditText phone;
    String name_user;
    String phone_num;
    private FusedLocationProviderClient fusedLocationClient;
    private Location locationLocal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_help);
        name = findViewById(R.id.editText);
        phone = findViewById(R.id.editText2);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            locationLocal = location;
                        }
                    }
                });
    }


    private void getuserData(String cat) {
        name_user = name.getText().toString();
        phone_num = phone.getText().toString();
        Map<String, String> payload = new HashMap<>();
        payload.put("username", name_user);
        payload.put("Phone", phone_num);
        payload.put("category", cat);
        if (locationLocal != null) {
            payload.put("lat", String.valueOf(locationLocal.getLatitude()));
            payload.put("long", String.valueOf(locationLocal.getLongitude()));
        }
        mDatabase.child("users").child(phone_num).setValue(payload).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(RequestHelpActivity.this, "Requested Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RequestHelpActivity.this,DashboardActivity.class));

            }

        });

    }

    public void requestHelp(View view) {


    }

    public void rescue(View view) {
        getuserData("rescue");
    }

    public void food(View view) {
        getuserData("food");
    }

    public void medical(View view) {
        getuserData("medical");
    }

    public void others(View view) {
        getuserData("others");
    }
}
