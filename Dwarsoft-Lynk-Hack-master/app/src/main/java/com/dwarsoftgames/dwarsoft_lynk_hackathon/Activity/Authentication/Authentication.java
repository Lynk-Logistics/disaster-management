package com.dwarsoftgames.dwarsoft_lynk_hackathon.Activity.Authentication;

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
import com.dwarsoftgames.dwarsoft_lynk_hackathon.Activity.MainActivity;
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

import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.AUTH;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.SHAREDPREF;

public class Authentication extends AppCompatActivity {

    private TextInputLayout tiPhoneNumber;
    private EditText etPhoneNumber;
    private MaterialButton btEnter;
    private String phoneNumber;

    private RequestQueue requestQueue;
    private AppDatabase db;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

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
        tiPhoneNumber = findViewById(R.id.tiPhoneNumber);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btEnter = findViewById(R.id.btEnter);

        requestQueue = Volley.newRequestQueue(getApplicationContext(),null);
        db = AppDatabase.getAppDatabase(getApplicationContext());
        sharedPreferences = getSharedPreferences(SHAREDPREF,MODE_PRIVATE);
    }

    private void setOnClicks() {
        btEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = etPhoneNumber.getText().toString().trim();
                if (phoneNumber.length() != 10) {
                    tiPhoneNumber.setErrorEnabled(true);
                    tiPhoneNumber.setError("Phone Number should have 10 digits");
                } else {
                    tiPhoneNumber.setErrorEnabled(false);
                    post_Phone();
                }
            }
        });
    }

    private void post_Phone() {

        Map<String, String> params = new HashMap<>();
        params.put("phoneNo",phoneNumber);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                AUTH, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseAuth(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),getString(R.string.api_fail), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        jsonObjReq.setTag("Auth");
        requestQueue.add(jsonObjReq);
    }

    private void parseAuth(JSONObject jsonObject) {
        try {
            if (jsonObject.getBoolean("isSuccess")) {
                db.userDao().updatePhoneNo(phoneNumber);
                sharedPreferences.edit().putBoolean("user_auth", true).apply();
                if (jsonObject.getBoolean("isNewUser")) {
                    openAuthDetails();
                } else {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    db.userDao().updateVolunteerID(jsonObject1.getInt("VolunteerID"));
                    db.userDao().updateVictimID(jsonObject1.getInt("VictimID"));
                    db.userDao().updateAreaID(jsonObject1.getString("AreaID"));

                    openDashboard();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void openAuthDetails() {
        Intent intent = new Intent(Authentication.this, AuthenticationDetails.class);
        startActivity(intent);
        finish();
    }

    private void openDashboard() {
        Intent intent = new Intent(Authentication.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}