package com.lynkhack;

import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    DatabaseReference mDatabase;
    private RecyclerView.Adapter mAdapter;
    FusedLocationProviderClient fusedLocationClient;
    Location locationLocal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        recyclerView = findViewById(R.id.recycler_dashboard);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
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

        mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                DataSnapshot snapshot = (DataSnapshot) dataSnapshot.getValue();
//                snapshot.child(snapshot.getKey()).getValue();
                List<Map<String,Object>> list = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.d("Naveen",child.getValue().toString());
                    Map<String,Object> g = (Map<String, Object>) dataSnapshot.child(child.getKey()).getValue();
//                    String phone=g.get("Phone").toString();
//                    String cat = g.get("category").toString();
                    String location = g.get("username").toString();

                    list.add(g);
                }
                mAdapter = new MyAdapter(list,locationLocal,DashboardActivity.this);
                recyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}
