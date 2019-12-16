package com.dwarsoftgames.dwarsoft_lynk_hackathon.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.dwarsoftgames.dwarsoft_lynk_hackathon.Activity.Authentication.UpdateProfile;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.Activity.Victim.VictimDashboard;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.Activity.Volunteer.VolunteerDashboard;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.Database.AppDatabase;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.Database.user_table;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.SHAREDPREF;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Permissions.checkCoarseLocation;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Permissions.checkFineLocation;

public class MainActivity extends AppCompatActivity {

    private MaterialButton btVolunteer;
    private MaterialButton btVictim;
    private MaterialButton btVolunteer1, btVictim1;
    private ImageView ivSettings;

    private AppDatabase db;
    private SharedPreferences sharedPreferences;

    //Location
    private FusedLocationProviderClient mFusedLocationClient;
    private Double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(getResources().getColor(R.color.white));

        init();
        setOnClicks();
        checkPermissions();
    }

    private void init() {
        btVictim = findViewById(R.id.btVictim);
        btVolunteer = findViewById(R.id.btVolunteer);
        btVictim1 = findViewById(R.id.btVictim1);
        btVolunteer1 = findViewById(R.id.btVolunteer1);
        ivSettings = findViewById(R.id.ivSettings);

        db = AppDatabase.getAppDatabase(getApplicationContext());
        sharedPreferences = getSharedPreferences(SHAREDPREF,MODE_PRIVATE);

        if (sharedPreferences.getBoolean("firstTime",true)) {
            sharedPreferences.edit().putBoolean("firstTime",false).apply();
            user_table user_table = new user_table();
            user_table.setUser_id(1);
            user_table.setPhoneNo("");
            user_table.setVictim_id(0);
            user_table.setVolunteer_id(0);
            user_table.setLatitude("");
            user_table.setLongitude("");
            db.userDao().insertAll(user_table);
        }
    }

    private void setOnClicks() {

        btVictim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissions()) {
                    checkVictim();
                }
            }
        });

        btVolunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissions()) {
                    checkVolunteer();
                }
            }
        });

        btVictim1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissions()) {
                    checkVictim();
                }
            }
        });

        btVolunteer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissions()) {
                    checkVolunteer();
                }
            }
        });

        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UpdateProfile.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkPermissions() {
        if (checkCoarseLocation(MainActivity.this) && checkFineLocation(MainActivity.this)) {
            getLocation();
            checkGPS();
            return true;
        } else {
            return false;
        }
    }

    private void checkGPS() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        Task<LocationSettingsResponse> result =
                LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult(
                                        MainActivity.this,
                                        LocationRequest.PRIORITY_HIGH_ACCURACY);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            } catch (ClassCastException e) {
                                // Ignore, should be an impossible error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            }
        });
    }

    private void checkVolunteer() {
        Intent intent = new Intent(MainActivity.this, VolunteerDashboard.class);
        startActivity(intent);
    }

    private void checkVictim() {
        Intent intent = new Intent(MainActivity.this, VictimDashboard.class);
        startActivity(intent);
    }

    //Location
    private void getLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations, this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            if (db.userDao().getLatitude().equalsIgnoreCase("") || db.userDao().getLongitude().equalsIgnoreCase("")) {
                                db.userDao().updateLatitude(String.valueOf(latitude));
                                db.userDao().updateLongitude(String.valueOf(longitude));
                            }
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case LocationRequest.PRIORITY_HIGH_ACCURACY:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        Log.d("LynkHack", "GPS Enabled by user");
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        Log.d("LynkHack", "User rejected GPS request");
                        showGPSDialog();
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    private void showGPSDialog() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(getString(R.string.gps_title))
                .setMessage(getString(R.string.gps_description))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.okay), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkGPS();
                    }
                })
                .show();
    }
}