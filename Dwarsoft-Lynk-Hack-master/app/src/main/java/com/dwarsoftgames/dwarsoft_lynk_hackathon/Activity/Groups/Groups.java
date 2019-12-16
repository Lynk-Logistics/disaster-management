package com.dwarsoftgames.dwarsoft_lynk_hackathon.Activity.Groups;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.Database.AppDatabase;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.Models.GroupDetailsModel;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.AREA;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.CITY;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.GET_GROUP_DETAILS;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.STATES;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Utilities.UTCToIST;

public class Groups extends AppCompatActivity {

    private MaterialButton btCreateGroup;
    private MaterialSpinner spState, spCity, spArea;
    private RecyclerView recyclerView;

    private RequestQueue requestQueue;
    private AppDatabase db;

    private ArrayAdapter<String> spinnerAdapter_state;
    private ArrayAdapter<String> spinnerAdapter_city;
    private ArrayAdapter<String> spinnerAdapter_area;

    private ArrayList<String> states = new ArrayList<>();
    private ArrayList<String> city = new ArrayList<>();
    private ArrayList<String> area = new ArrayList<>();

    private String areaID;

    private final List<Object> mRecyclerViewItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        Window window = getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(getResources().getColor(R.color.white));

        init();
        setOnClicks();
        setAdapters();
        setSpinnerListeners();
        setRecyclerView();

        post_state();
        post_city("1");
        post_area("1");

        post_groupDetails();
    }

    private void init() {
        btCreateGroup = findViewById(R.id.btCreateGroup);
        spState = findViewById(R.id.spState);
        spCity = findViewById(R.id.spCity);
        spArea = findViewById(R.id.spArea);
        recyclerView = findViewById(R.id.recyclerView);

        requestQueue = Volley.newRequestQueue(getApplicationContext(),null);
        db = AppDatabase.getAppDatabase(getApplicationContext());

        mRecyclerViewItems.clear();

        areaID = db.userDao().getAreaID();
    }

    private void setOnClicks() {
        btCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Groups.this,CreateGroup.class);
                startActivity(intent);
            }
        });
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
                post_groupDetails();
            }
        });
    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,1));
        GroupsAdapter groupsAdapter = new GroupsAdapter(mRecyclerViewItems);
        recyclerView.setAdapter(groupsAdapter);
    }

    private void post_state() {

        states.clear();

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

        city.clear();

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
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i=0; i<jsonArray.length(); i++) {
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

        area.clear();
        if (spinnerAdapter_area != null) {
            spinnerAdapter_area.clear();
        }

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

    private void post_groupDetails() {

        Map<String, String> params = new HashMap<>();
        params.put("AreaID",areaID);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                GET_GROUP_DETAILS, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseGroups(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),getString(R.string.api_fail), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        jsonObjReq.setTag("GroupDetails");
        requestQueue.add(jsonObjReq);
    }

    private void parseGroups(JSONObject jsonObject) {
        try {
            if (jsonObject.getBoolean("isSuccess")) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i=0; i<jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    addData(jsonObject1);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addData(JSONObject jsonObject1) {
        GroupDetailsModel groupDetailsModel = new GroupDetailsModel();
        try {
            groupDetailsModel.setGroupsID(jsonObject1.getInt("GroupsID"));
            groupDetailsModel.setVolunteerID(jsonObject1.getInt("VolunteerID"));
            groupDetailsModel.setAreaID(jsonObject1.getInt("AreaID"));
            groupDetailsModel.setName(jsonObject1.getString("Name"));
            groupDetailsModel.setDescription(jsonObject1.getString("Description"));
            groupDetailsModel.setMembers(jsonObject1.getString("Members"));
            groupDetailsModel.setLink(jsonObject1.getString("link"));
            groupDetailsModel.setCreatedOn(UTCToIST(jsonObject1.getString("createdOn")));
            groupDetailsModel.setUpdatedOn(UTCToIST(jsonObject1.getString("updatedOn")));
            groupDetailsModel.setIsCompleted(jsonObject1.getInt("isCompleted"));
            groupDetailsModel.setIsActive(jsonObject1.getInt("isActive"));
            groupDetailsModel.setLatitude(jsonObject1.getString("latitude"));
            groupDetailsModel.setLongitude(jsonObject1.getString("longitude"));
            groupDetailsModel.setDistance(
                    getDistance(Double.parseDouble(db.userDao().getLatitude()),Double.parseDouble(db.userDao().getLongitude()),
                            Double.parseDouble(groupDetailsModel.getLatitude()),Double.parseDouble(groupDetailsModel.getLongitude())) + " KM"
            );
            mRecyclerViewItems.add(groupDetailsModel);
            setRecyclerView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist / 0.62137;

        return String.format(Locale.getDefault(), "%.1f", dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    private class GroupsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final List<Object> mRecyclerViewItems_adapter;

        GroupsAdapter(List<Object> mRecyclerViewItem){
            this.mRecyclerViewItems_adapter = mRecyclerViewItem;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            view = LayoutInflater.from(Groups.this).inflate(R.layout.custom_group_details, parent, false);
            return new GroupViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof GroupViewHolder) {
                GroupViewHolder groupViewHolder = (GroupViewHolder) holder;
                final GroupDetailsModel groupDetailsModel = (GroupDetailsModel) mRecyclerViewItems_adapter.get(position);

                String members = "Members - " + groupDetailsModel.getMembers();
                String distance = "Distance - " + groupDetailsModel.getDistance();

                groupViewHolder.tvName.setText(groupDetailsModel.getName());
                groupViewHolder.tvDescription.setText(groupDetailsModel.getDescription());
                groupViewHolder.tvDate.setText(groupDetailsModel.getCreatedOn());
                groupViewHolder.tvDistance.setText(distance);
                groupViewHolder.tvMembers.setText(members);

                groupViewHolder.btOpen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(groupDetailsModel.getLink()));
                        startActivity(i);
                    }
                });
            }
        }

        private class GroupViewHolder extends RecyclerView.ViewHolder {

            private TextView tvName, tvDescription, tvDate, tvDistance, tvMembers;
            private MaterialButton btOpen;

            GroupViewHolder(View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tvName);
                tvDescription = itemView.findViewById(R.id.tvDescription);
                tvDate = itemView.findViewById(R.id.tvDate);
                tvDistance = itemView.findViewById(R.id.tvDistance);
                tvMembers = itemView.findViewById(R.id.tvMembers);
                btOpen = itemView.findViewById(R.id.btOpen);
            }
        }

        @Override
        public int getItemCount() {
            return mRecyclerViewItems_adapter.size();
        }
    }
}