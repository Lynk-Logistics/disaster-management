package com.dwarsoftgames.dwarsoft_lynk_hackathon.Activity.Victim;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.Database.AppDatabase;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.Models.VictimsHelpModel;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.Models.VolunteerHelpModel;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.AREA;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.AUTH_KEY;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.CITY;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.GET_VOLUNTEER_DETAILS;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.STATES;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.TEXT_SMS;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.UPDATE_VRMAP_ISCOMPLETE;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Utilities.UTCToIST;

public class VictimDashboard extends AppCompatActivity implements OnMapReadyCallback {

    private MaterialSpinner spState, spCity, spArea;
    private MaterialButton btHelp;

    private RequestQueue requestQueue;
    private AppDatabase db;

    private String areaID;

    private ArrayAdapter<String> spinnerAdapter_state;
    private ArrayAdapter<String> spinnerAdapter_city;
    private ArrayAdapter<String> spinnerAdapter_area;

    private ArrayList<String> states = new ArrayList<>();
    private ArrayList<String> city = new ArrayList<>();
    private ArrayList<String> area = new ArrayList<>();

    private final List<Object> itemsList = new ArrayList<>();

    private GoogleMap mMap;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victim_dashboard);

        Window window = getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(getResources().getColor(R.color.white));

        init();
        initMaps();
        setAdapters();
        setSpinnerListeners();
        setOnClicks();

        post_state();
        post_city("1");
        post_area("1");
    }

    private void init() {
        spState = findViewById(R.id.spState);
        spCity = findViewById(R.id.spCity);
        spArea = findViewById(R.id.spArea);
        btHelp = findViewById(R.id.btHelp);

        requestQueue = Volley.newRequestQueue(getApplicationContext(), null);
        db = AppDatabase.getAppDatabase(getApplicationContext());

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

    private void initMaps() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
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
                post_getVolunteerDetails();
            }
        });
    }

    private void setOnClicks() {
        btHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VictimDashboard.this, VictimHelpResource.class);
                startActivity(intent);
            }
        });
    }

    private void initMapDetails() {

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(getApplicationContext());
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(getApplicationContext());
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(getApplicationContext());
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                int VHMapID = (Integer) marker.getTag();

                for (int i = 0; i < itemsList.size(); i++) {
                    VolunteerHelpModel volunteerHelpModel = (VolunteerHelpModel) itemsList.get(i);
                    if (volunteerHelpModel.getVRMapID() == VHMapID) {
                        phoneNumber = volunteerHelpModel.getPhoneNo();
                        break;
                    }
                }
                showCompleteDialog(VHMapID);
            }
        });
    }

    private void sendSMS() {
        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getData());
    }

    private JsonObjectRequest getData() {

        String message = "Hi, Someone has accepted your help request. Contact - " + db.userDao().getPhoneNumber();

        String url = TEXT_SMS + phoneNumber + AUTH_KEY + message;

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.message_sent), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.message_sent), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
        return jsonObjectRequest;
    }

    private void showCompleteDialog(final int VRMapID) {
        new AlertDialog.Builder(VictimDashboard.this)
                .setTitle(getString(R.string.complete_header1))
                .setMessage(getString(R.string.complete_description1))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendSMS();
//                        post_updateIsComplete(String.valueOf(VRMapID));
                    }
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .show();
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
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.api_fail), Snackbar.LENGTH_LONG);
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
                for (int i = 0; i < jsonArray.length(); i++) {
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
        params.put("StateID", stateID);

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
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.api_fail), Snackbar.LENGTH_LONG);
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
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    city.add(jsonObject1.getString("CityID"));
                    spinnerAdapter_city.add(jsonObject1.getString("CityName"));
                }
                if (city.size() > 0) {
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
        params.put("CityID", cityID);

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
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.api_fail), Snackbar.LENGTH_LONG);
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
                for (int i = 0; i < jsonArray.length(); i++) {
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

    private void post_getVolunteerDetails() {

        itemsList.clear();

        Map<String, String> params = new HashMap<>();
        params.put("AreaID", areaID);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                GET_VOLUNTEER_DETAILS, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseVolunteer(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.api_fail), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        jsonObjReq.setTag("VictimDetails");
        requestQueue.add(jsonObjReq);
    }

    private void parseVolunteer(JSONObject jsonObject) {
        try {
            if (jsonObject.getBoolean("isSuccess")) {
                area.clear();
                if (spinnerAdapter_area != null) {
                    spinnerAdapter_area.clear();
                }
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    VolunteerHelpModel volunteerHelpModel = new VolunteerHelpModel();
                    volunteerHelpModel.setVRMapID(jsonObject1.getInt("VRMapID"));
                    volunteerHelpModel.setVolunteerID(jsonObject1.getInt("VolunteerID"));
                    volunteerHelpModel.setHelpID(jsonObject1.getInt("HelpID"));
                    volunteerHelpModel.setAreaID(jsonObject1.getInt("AreaID"));
                    volunteerHelpModel.setDescription(jsonObject1.getString("Description"));
                    volunteerHelpModel.setLatitude(jsonObject1.getString("Latitude"));
                    volunteerHelpModel.setLongitude(jsonObject1.getString("Longitude"));
                    volunteerHelpModel.setPhoneNo(jsonObject1.getString("PhoneNo"));
                    volunteerHelpModel.setCreatedOn(jsonObject1.getString("createdOn"));
                    volunteerHelpModel.setUpdatedOn(jsonObject1.getString("updatedOn"));
                    itemsList.add(volunteerHelpModel);
                }
                setMapMarkers();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setMapMarkers() {
        for (int i = 0; i < itemsList.size(); i++) {
            VolunteerHelpModel volunteerHelpModel = (VolunteerHelpModel) itemsList.get(i);

            int helpID = volunteerHelpModel.getHelpID();
            String helpName;
            if (helpID == 1) {
                helpName = "Food";
            } else if (helpID == 2) {
                helpName = "Clothes";
            } else {
                helpName = "Shelter";
            }
            BitmapDescriptor bitmapDescriptor = null;
            if (helpName.equalsIgnoreCase("food")) {
                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
            } else if (helpName.equalsIgnoreCase("clothes")) {
                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
            } else {
                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
            }

            LatLng latlng = new LatLng(Double.parseDouble(volunteerHelpModel.getLatitude()), Double.parseDouble(volunteerHelpModel.getLongitude()));
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .title("Available - " + helpName)
                    .snippet(
                            "Description - " + volunteerHelpModel.getDescription() + "\n"
                                    + "Date - " + UTCToIST(volunteerHelpModel.getCreatedOn()) + "\n"
                                    + "Phone No - " + volunteerHelpModel.getPhoneNo()
                    )
                    .position(latlng)
                    .icon(bitmapDescriptor)
            );
            marker.setTag(volunteerHelpModel.getVRMapID());
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setMaps();
        initMapDetails();
        post_getVolunteerDetails();
    }

    private void setMaps() {
        mMap.setMyLocationEnabled(true);
        CameraUpdate center = CameraUpdateFactory.newLatLng(
                new LatLng(Double.parseDouble(db.userDao().getLatitude()),
                        Double.parseDouble(db.userDao().getLongitude())
                )
        );
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!checkPlayServices()) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "You need to install Google Play Services to use the App properly", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
            } else {
                finish();
            }
            return false;
        }
        return true;
    }
}