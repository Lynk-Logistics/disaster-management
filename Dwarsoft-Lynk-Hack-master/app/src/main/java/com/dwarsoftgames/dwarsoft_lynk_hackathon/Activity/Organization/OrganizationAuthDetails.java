package com.dwarsoftgames.dwarsoft_lynk_hackathon.Activity.Organization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.Database.AppDatabase;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.ORGANIZATION_AUTH_DATA;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.SHAREDPREF;

public class OrganizationAuthDetails extends AppCompatActivity {

    private TextInputLayout tiName, tiDescription, tiPhone, tiGST;
    private EditText etName, etDescription, etPhone, etGST;
    private MaterialButton btEnter;

    private String name, description, phone, GST;

    private RequestQueue requestQueue;
    private AppDatabase db;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_auth_details);

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
        tiName = findViewById(R.id.tiName);
        tiDescription = findViewById(R.id.tiDescription);
        tiPhone = findViewById(R.id.tiPhone);
        tiGST = findViewById(R.id.tiGST);
        etGST = findViewById(R.id.etGST);
        etPhone = findViewById(R.id.etPhone);
        etDescription = findViewById(R.id.etDescription);
        etName = findViewById(R.id.etName);
        btEnter = findViewById(R.id.btEnter);

        requestQueue = Volley.newRequestQueue(getApplicationContext(), null);
        db = AppDatabase.getAppDatabase(getApplicationContext());
        sharedPreferences = getSharedPreferences(SHAREDPREF, MODE_PRIVATE);
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
    }

    private boolean validateData() {
        int flag = 0;

        name = etName.getText().toString().trim();
        description = etDescription.getText().toString().trim();
        phone = etPhone.getText().toString().trim();
        GST = etGST.getText().toString().trim();

        if (name == null || name.length() < 3) {
            tiName.setErrorEnabled(true);
            tiName.setError("Name Not Proper");
            flag = 1;
        } else {
            tiName.setErrorEnabled(false);
        }

        if (description == null || description.length() < 3) {
            tiDescription.setErrorEnabled(true);
            tiDescription.setError("Description Not Proper");
            flag = 1;
        } else {
            tiDescription.setErrorEnabled(false);
        }

        if (phone == null || phone.length() != 10) {
            tiPhone.setErrorEnabled(true);
            tiPhone.setError("Phone Not Proper");
            flag = 1;
        } else {
            tiPhone.setErrorEnabled(false);
        }

        if (GST == null || GST.length() < 3) {
            tiGST.setErrorEnabled(true);
            tiGST.setError("GST IN Not Proper");
            flag = 1;
        } else {
            tiGST.setErrorEnabled(false);
        }

        return flag != 1;
    }

    private void post_authDetails() {

        Map<String, String> params = new HashMap<>();
        params.put("phoneNo", phone);
        params.put("Name", name);
        params.put("GSTIn", GST);
        params.put("Description", description);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                ORGANIZATION_AUTH_DATA, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.api_fail), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        jsonObjReq.setTag("AuthenticationDetails");
        requestQueue.add(jsonObjReq);
    }

    private void parseResponse(JSONObject jsonObject) {

        try {
            if (jsonObject.getBoolean("isSuccess")) {
                sharedPreferences.edit().putBoolean("org_auth", true).apply();
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                db.userDao().updateOrgID(jsonObject1.getInt("insertId"));

                openDashboard();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void openDashboard() {
        Intent intent = new Intent(OrganizationAuthDetails.this, OrganizationDashboard.class);
        startActivity(intent);
        finish();
    }
}