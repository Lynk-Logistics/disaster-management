package com.dwarsoftgames.dwarsoft_lynk_hackathon.Activity.Victim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.Activity.SuccessScreen;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.Database.AppDatabase;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.GET_HELP_TYPES;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.REQUEST_HELP;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.SHARE_RESOURCES;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.STATES;

public class VictimHelpResource extends AppCompatActivity {

    private TextInputLayout tiDescription;
    private EditText etDescription, etMembers, etMale, etFemale, etChildren;
    private MaterialSpinner spHelp;
    private MaterialButton btRequest;

    private String description, members, male, female, children;
    private String helpID = "1";
    private ArrayAdapter<String> spinnerAdapter_help;
    private ArrayList<String> helpIDs = new ArrayList<>();

    private RequestQueue requestQueue;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victim_help_resource);

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

        post_helpTypes();
    }

    private void init() {
        tiDescription = findViewById(R.id.tiDescription);
        etDescription = findViewById(R.id.etDescription);
        etMembers = findViewById(R.id.etMembers);
        etMale = findViewById(R.id.etMale);
        etFemale = findViewById(R.id.etFemale);
        etChildren = findViewById(R.id.etChildren);
        spHelp = findViewById(R.id.spHelp);
        btRequest = findViewById(R.id.btRequest);

        requestQueue = Volley.newRequestQueue(getApplicationContext(), null);
        db = AppDatabase.getAppDatabase(getApplicationContext());
    }

    private void setOnClicks() {
        btRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    post_requestHelp();
                }
            }
        });
    }

    private void setAdapters() {
        spinnerAdapter_help = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter_help.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    private void setSpinnerListeners() {
        spHelp.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                helpID = helpIDs.get(position);
            }
        });
    }

    private boolean validateData() {
        int flag = 0;

        description = etDescription.getText().toString().trim();
        members = etMembers.getText().toString().trim();
        male = etMale.getText().toString().trim();
        female = etFemale.getText().toString().trim();
        children = etChildren.getText().toString().trim();

        if (description.length() > 3) {
            tiDescription.setErrorEnabled(false);
        } else {
            tiDescription.setErrorEnabled(true);
            tiDescription.setError("Description Not Proper");
            flag = 1;
        }

        if (members == null || members.equalsIgnoreCase("")) {
            flag = 1;
        }

        if (male == null || male.equalsIgnoreCase("")) {
            male = "0";
        }

        if (female == null || female.equalsIgnoreCase("")) {
            female = "0";
        }

        if (children == null || children.equalsIgnoreCase("")) {
            children = "0";
        }

        return flag != 1;
    }

    private void post_helpTypes() {

        Map<String, String> params = new HashMap<>();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                GET_HELP_TYPES, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseHelpTypes(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.api_fail), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        jsonObjReq.setTag("HelpTypes");
        requestQueue.add(jsonObjReq);
    }

    private void parseHelpTypes(JSONObject jsonObject) {
        try {
            if (jsonObject.getBoolean("isSuccess")) {
                helpIDs.clear();
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    helpIDs.add(jsonObject1.getString("HelpID"));
                    spinnerAdapter_help.add(jsonObject1.getString("HName"));
                }
                spHelp.setAdapter(spinnerAdapter_help);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void post_requestHelp() {

        Map<String, String> params = new HashMap<>();
        params.put("VictimID", String.valueOf(db.userDao().getVictimID()));
        params.put("HelpID", helpID);
        params.put("AreaID", db.userDao().getAreaID());
        params.put("PhoneNo", db.userDao().getPhoneNumber());
        params.put("latitude", db.userDao().getLatitude());
        params.put("longitude", db.userDao().getLongitude());
        params.put("Description", description);
        params.put("Members", members);
        params.put("Male", male);
        params.put("Female", female);
        params.put("Children", children);
        params.put("isCompleted", "0");

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                REQUEST_HELP, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseResources(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.api_fail), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        jsonObjReq.setTag("RequestHelp");
        requestQueue.add(jsonObjReq);
    }

    private void parseResources(JSONObject jsonObject) {
        try {
            if (jsonObject.getBoolean("isSuccess")) {
                Intent intent = new Intent(VictimHelpResource.this, SuccessScreen.class);
                startActivity(intent);
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
