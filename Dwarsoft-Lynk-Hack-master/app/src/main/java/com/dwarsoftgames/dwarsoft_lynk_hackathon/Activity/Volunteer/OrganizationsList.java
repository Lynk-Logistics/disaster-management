package com.dwarsoftgames.dwarsoft_lynk_hackathon.Activity.Volunteer;

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
import com.dwarsoftgames.dwarsoft_lynk_hackathon.Models.FundsModel;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.Models.GroupDetailsModel;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.Models.OrganizationListModel;
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
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.ORGANIZATION_FUNDS_LIST;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.ORGANIZATION_LIST;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.STATES;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Utilities.UTCToIST;

public class OrganizationsList extends AppCompatActivity {

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
        setContentView(R.layout.activity_organizations_list);

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
        setRecyclerView();

        post_state();
        post_city("1");
        post_area("1");

        post_organizationDetails();
    }

    private void init() {
        spState = findViewById(R.id.spState);
        spCity = findViewById(R.id.spCity);
        spArea = findViewById(R.id.spArea);
        recyclerView = findViewById(R.id.recyclerView);

        requestQueue = Volley.newRequestQueue(getApplicationContext(), null);
        db = AppDatabase.getAppDatabase(getApplicationContext());

        mRecyclerViewItems.clear();

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
                post_organizationDetails();
            }
        });
    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, 1));
        OrganizationListsAdapter organizationListsAdapter = new OrganizationListsAdapter(mRecyclerViewItems);
        recyclerView.setAdapter(organizationListsAdapter);
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

        city.clear();

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

        area.clear();
        if (spinnerAdapter_area != null) {
            spinnerAdapter_area.clear();
        }

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

    private void post_organizationDetails() {

        Map<String, String> params = new HashMap<>();
        params.put("AreaID", areaID);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                ORGANIZATION_LIST, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseOrganizationDetails(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.api_fail), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        jsonObjReq.setTag("OrganizationDetails");
        requestQueue.add(jsonObjReq);
    }

    private void parseOrganizationDetails(JSONObject jsonObject) {
        try {
            if (jsonObject.getBoolean("isSuccess")) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    addData(jsonObject1);
                }
                setRecyclerView();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addData(JSONObject jsonObject1) {
        OrganizationListModel organizationListModel = new OrganizationListModel();
        try {
            organizationListModel.setOrgID(jsonObject1.getInt("OrgID"));
            organizationListModel.setName(jsonObject1.getString("Name"));
            organizationListModel.setDescription(jsonObject1.getString("Description"));
            organizationListModel.setPhoneNo(jsonObject1.getString("PhoneNo"));
            organizationListModel.setGSTIn(jsonObject1.getString("GSTIn"));
            mRecyclerViewItems.add(organizationListModel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class OrganizationListsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final List<Object> mRecyclerViewItems_adapter;

        OrganizationListsAdapter(List<Object> mRecyclerViewItem) {
            this.mRecyclerViewItems_adapter = mRecyclerViewItem;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            view = LayoutInflater.from(OrganizationsList.this).inflate(R.layout.custom_organization_list, parent, false);
            return new GroupViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof GroupViewHolder) {
                GroupViewHolder groupViewHolder = (GroupViewHolder) holder;
                final OrganizationListModel organizationListModel = (OrganizationListModel) mRecyclerViewItems_adapter.get(position);

                String GST = "GST - " + organizationListModel.getGSTIn();

                groupViewHolder.tvName.setText(organizationListModel.getName());
                groupViewHolder.tvDescription.setText(organizationListModel.getDescription());
                groupViewHolder.tvGST.setText(GST);

                groupViewHolder.btContact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + organizationListModel.getPhoneNo()));
                        startActivity(intent);
                    }
                });
            }
        }

        private class GroupViewHolder extends RecyclerView.ViewHolder {

            private TextView tvName, tvDescription, tvGST;
            private MaterialButton btContact;

            GroupViewHolder(View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tvName);
                tvDescription = itemView.findViewById(R.id.tvDescription);
                tvGST = itemView.findViewById(R.id.tvGST);
                btContact = itemView.findViewById(R.id.btContact);
            }
        }

        @Override
        public int getItemCount() {
            return mRecyclerViewItems_adapter.size();
        }
    }
}