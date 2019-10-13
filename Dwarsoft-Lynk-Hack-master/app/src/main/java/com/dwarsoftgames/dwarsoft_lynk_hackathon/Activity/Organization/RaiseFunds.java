package com.dwarsoftgames.dwarsoft_lynk_hackathon.Activity.Organization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.dwarsoftgames.dwarsoft_lynk_hackathon.Activity.SuccessScreen;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.Database.AppDatabase;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.ORGANIZATION_FUND_CAMPAIGN;

public class RaiseFunds extends AppCompatActivity {

    private TextInputLayout tiName, tiDescription, tiLink;
    private EditText etName, etDescription, etSponsor, etLink;
    private MaterialButton btCreate;

    private RequestQueue requestQueue;
    private AppDatabase db;

    private String name, description, sponsor, link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_funds);

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
        tiLink = findViewById(R.id.tiLink);
        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etSponsor = findViewById(R.id.etSponsor);
        etLink = findViewById(R.id.etLink);
        btCreate = findViewById(R.id.btCreate);

        requestQueue = Volley.newRequestQueue(getApplicationContext(), null);
        db = AppDatabase.getAppDatabase(getApplicationContext());
    }

    private void setOnClicks() {
        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateDetails()) {
                    post_createCampaign();
                }
            }
        });
    }

    private boolean validateDetails() {
        int flag = 0;
        name = etName.getText().toString().trim();
        description = etDescription.getText().toString().trim();
        sponsor = etSponsor.getText().toString().trim();
        link = etLink.getText().toString().trim();

        if (name.length() < 2) {
            flag = 1;
            tiName.setErrorEnabled(true);
            tiName.setError("Name Not Proper");
        } else {
            tiName.setErrorEnabled(false);
        }

        if (description.length() < 2) {
            flag = 1;
            tiDescription.setErrorEnabled(true);
            tiDescription.setError("Description Not Proper");
        } else {
            tiDescription.setErrorEnabled(false);
        }

        if (sponsor == null) {
            sponsor = "";
        }

        if (!link.contains("http")) {
            flag = 1;
            tiLink.setErrorEnabled(true);
            tiLink.setError("Link Not Proper");
        } else {
            tiLink.setErrorEnabled(false);
        }

        return flag != 1;
    }

    private void post_createCampaign() {

        Map<String, String> params = new HashMap<>();
        params.put("OrgID", String.valueOf(db.userDao().getOrgID()));
        params.put("Name", name);
        params.put("Description", description);
        params.put("Sponsor", sponsor);
        params.put("Link", link);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                ORGANIZATION_FUND_CAMPAIGN, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseCreateGroup(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.api_fail), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        jsonObjReq.setTag("RaiseFunds");
        requestQueue.add(jsonObjReq);
    }

    private void parseCreateGroup(JSONObject jsonObject) {
        try {
            if (jsonObject.getBoolean("isSuccess")) {
                Intent intent = new Intent(RaiseFunds.this, SuccessScreen.class);
                startActivity(intent);
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
