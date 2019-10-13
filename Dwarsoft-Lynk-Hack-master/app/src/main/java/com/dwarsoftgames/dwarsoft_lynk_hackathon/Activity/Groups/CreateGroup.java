package com.dwarsoftgames.dwarsoft_lynk_hackathon.Activity.Groups;

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
import com.dwarsoftgames.dwarsoft_lynk_hackathon.Activity.Volunteer.ShareResources;
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

import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.CREATE_GROUP;

public class CreateGroup extends AppCompatActivity {

    private TextInputLayout tiName, tiDescription, tiMembers, tiLink;
    private EditText etName, etDescription, etMembers, etLink;
    private MaterialButton btCreate;

    private RequestQueue requestQueue;
    private AppDatabase db;

    private String name, description, members, link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

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
        tiMembers = findViewById(R.id.tiMembers);
        tiLink = findViewById(R.id.tiLink);
        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etMembers = findViewById(R.id.etMembers);
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
                    post_CreateGroup();
                }
            }
        });
    }

    private boolean validateDetails() {
        int flag = 0;
        name = etName.getText().toString().trim();
        description = etDescription.getText().toString().trim();
        members = etMembers.getText().toString().trim();
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

        if (members.length() == 0) {
            flag = 1;
            tiMembers.setErrorEnabled(true);
            tiMembers.setError("Specify Members");
        } else {
            tiMembers.setErrorEnabled(false);
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

    private void post_CreateGroup() {

        Map<String, String> params = new HashMap<>();
        params.put("VolunteerID", String.valueOf(db.userDao().getVolunteerID()));
        params.put("AreaID", db.userDao().getAreaID());
        params.put("Name", name);
        params.put("Description", description);
        params.put("Members", members);
        params.put("link", link);
        params.put("isCompleted", "0");
        params.put("latitude", db.userDao().getLatitude());
        params.put("longitude", db.userDao().getLongitude());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                CREATE_GROUP, new JSONObject(params),
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

        jsonObjReq.setTag("Authentication");
        requestQueue.add(jsonObjReq);
    }

    private void parseCreateGroup(JSONObject jsonObject) {
        try {
            if (jsonObject.getBoolean("isSuccess")) {
                Intent intent = new Intent(CreateGroup.this, SuccessScreen.class);
                startActivity(intent);
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
