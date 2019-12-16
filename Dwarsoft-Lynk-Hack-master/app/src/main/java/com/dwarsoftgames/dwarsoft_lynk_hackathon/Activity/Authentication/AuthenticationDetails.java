package com.dwarsoftgames.dwarsoft_lynk_hackathon.Activity.Authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.Activity.MainActivity;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.Activity.Volunteer.VolunteerDashboard;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.Database.AppDatabase;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.AREA;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.AUTH_DATA;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.CITY;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.SHAREDPREF;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.STATES;

public class AuthenticationDetails extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private TextInputLayout tiName;
    private EditText etName;
    private TextView tvAddress;
    private MaterialSpinner spState, spCity, spArea;
    private MaterialButton btEnter;

    private RequestQueue requestQueue;
    private AppDatabase db;
    private SharedPreferences sharedPreferences;

    private ArrayAdapter<String> spinnerAdapter_state;
    private ArrayAdapter<String> spinnerAdapter_city;
    private ArrayAdapter<String> spinnerAdapter_area;

    private ArrayList<String> states = new ArrayList<>();
    private ArrayList<String> city = new ArrayList<>();
    private ArrayList<String> area = new ArrayList<>();

    private String areaID;
    private String address;
    private String name;
    private double latitude;
    private double longitude;

    //Location
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_details);

        Window window = getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(getResources().getColor(R.color.white));

        init();
        setAdapters();
        setSpinnerListeners();
        setOnClicks();

        getLocation();

        post_state();
        post_city("1");
        post_area("1");
    }

    private void init() {
        tiName = findViewById(R.id.tiName);
        etName = findViewById(R.id.etName);
        tvAddress = findViewById(R.id.tvAddress);
        spState = findViewById(R.id.spState);
        spCity = findViewById(R.id.spCity);
        spArea = findViewById(R.id.spArea);
        btEnter = findViewById(R.id.btEnter);

        requestQueue = Volley.newRequestQueue(getApplicationContext(),null);
        db = AppDatabase.getAppDatabase(getApplicationContext());
        sharedPreferences = getSharedPreferences(SHAREDPREF,MODE_PRIVATE);

        spState.setHint("Select State");
        spCity.setHint("Select City");
        spArea.setHint("Select Area");

        states.clear();
        city.clear();
        area.clear();
        if (spinnerAdapter_area != null) {
            spinnerAdapter_area.clear();
        }

        areaID = db.userDao().getAreaID();
    }

    private void setAdapters() {
        spinnerAdapter_state = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerAdapter_city = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerAdapter_area = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter_area.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    private void clearAdapters() {
        spinnerAdapter_city = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerAdapter_area = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter_area.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spCity.setAdapter(spinnerAdapter_city);
        spArea.setAdapter(spinnerAdapter_area);
    }

    private void setSpinnerListeners() {
        spState.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                clearAdapters();
                post_city(states.get(position));
            }
        });

        spCity.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                post_area(city.get(position));
            }
        });

        spArea.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                areaID = area.get(position);
            }
        });
    }

    private void setOnClicks() {
        btEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    post_authDetails();
                }
            }
        });

        tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAddress();
            }
        });
    }

    private void post_state() {

        Map<String, String> params = new HashMap<>();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                STATES, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseStates(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),getString(R.string.api_fail), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        jsonObjReq.setTag("States");
        requestQueue.add(jsonObjReq);
    }

    private void parseStates(JSONObject jsonObject) {
        try {
            if (jsonObject.getBoolean("isSuccess")) {
                states.clear();
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i=0; i<jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    states.add(jsonObject1.getString("StateID"));
                    spinnerAdapter_state.add(jsonObject1.getString("StateName"));
                }

                spState.setAdapter(spinnerAdapter_state);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void post_city(String stateID) {

        Map<String, String> params = new HashMap<>();
        params.put("StateID",stateID);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                CITY, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseCity(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),getString(R.string.api_fail), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        jsonObjReq.setTag("City");
        requestQueue.add(jsonObjReq);
    }

    private void parseCity(JSONObject jsonObject) {
        try {
            if (jsonObject.getBoolean("isSuccess")) {
                city.clear();
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i=0; i<jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    city.add(jsonObject1.getString("CityID"));
                    spinnerAdapter_city.add(jsonObject1.getString("CityName"));
                }
                if (city.size() > 0) {
                    areaID = "1";
                    post_area(city.get(0));
                }
                spCity.setAdapter(spinnerAdapter_city);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void post_area(String cityID) {

        Map<String, String> params = new HashMap<>();
        params.put("CityID",cityID);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                AREA, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseArea(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),getString(R.string.api_fail), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        jsonObjReq.setTag("Area");
        requestQueue.add(jsonObjReq);
    }

    private void parseArea(JSONObject jsonObject) {
        try {
            if (jsonObject.getBoolean("isSuccess")) {
                area.clear();

                if (spinnerAdapter_area != null) {
                    spinnerAdapter_area.clear();
                }
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i=0; i<jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    area.add(jsonObject1.getString("AreaID"));
                    spinnerAdapter_area.add(jsonObject1.getString("AName"));
                }

                spArea.setAdapter(spinnerAdapter_area);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean validateData() {
        name = etName.getText().toString().trim();
        if (name.length() > 2) {
            tiName.setErrorEnabled(false);
        } else {
            tiName.setErrorEnabled(true);
            tiName.setError("Please enter proper Name");
            return false;
        }

        if (latitude == 0 || longitude == 0) {
            getLocation();
        }

        if (address == null) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),getString(R.string.address_fail), Snackbar.LENGTH_LONG);
            snackbar.show();

            return false;
        }

        if (address.equalsIgnoreCase("")) {
            getAddress();
        }

        return true;
    }

    private void post_authDetails() {

        Map<String, String> params = new HashMap<>();
        params.put("phoneNo",db.userDao().getPhoneNumber());
        params.put("Name",name);
        params.put("Address",address);
        params.put("Latitude", String.valueOf(latitude));
        params.put("Longitude", String.valueOf(longitude));
        params.put("AreaID",areaID);

        db.userDao().updateLatitude(String.valueOf(latitude));
        db.userDao().updateLongitude(String.valueOf(longitude));
        db.userDao().updateAreaID(areaID);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                AUTH_DATA, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),getString(R.string.api_fail), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        jsonObjReq.setTag("AuthenticationDetails");
        requestQueue.add(jsonObjReq);
    }

    private void parseResponse(JSONObject jsonObject) {

        try {
            if (jsonObject.getBoolean("isSuccess")) {
                sharedPreferences.edit().putBoolean("volunteerDetails",true).apply();
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                db.userDao().updateVolunteerID(jsonObject1.getInt("VolunteerID"));
                db.userDao().updateVictimID(jsonObject1.getInt("VictimID"));

                openDashboard();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void openDashboard() {
        Intent intent = new Intent(AuthenticationDetails.this, MainActivity.class);
        startActivity(intent);
        finish();
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
                        }
                    }
                });
    }

    private void getAddress() {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//            String city = addresses.get(0).getLocality();
//            String state = addresses.get(0).getAdminArea();
//            String country = addresses.get(0).getCountryName();
//            String postalCode = addresses.get(0).getPostalCode();
//            String knownName = addresses.get(0).getFeatureName();
            String setAddress = "Address - " + address;
            tvAddress.setText(setAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("LynkHack","error 1 : "+i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("LynkHack","error 2 : "+connectionResult.getErrorMessage());
    }
}