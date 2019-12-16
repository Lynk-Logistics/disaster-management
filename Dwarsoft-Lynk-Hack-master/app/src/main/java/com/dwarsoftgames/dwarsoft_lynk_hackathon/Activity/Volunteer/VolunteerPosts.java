package com.dwarsoftgames.dwarsoft_lynk_hackathon.Activity.Volunteer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.Activity.MainActivity;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.Database.AppDatabase;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.Models.VRMapModel;
import com.dwarsoftgames.dwarsoft_lynk_hackathon.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.GET_VRMAP;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Constants.UPDATE_VRMAP_ISCOMPLETE;
import static com.dwarsoftgames.dwarsoft_lynk_hackathon.Utils.Utilities.UTCToIST;

public class VolunteerPosts extends AppCompatActivity {

    private RecyclerView recyclerView;

    private final List<Object> mRecyclerViewItems = new ArrayList<>();

    private RequestQueue requestQueue;
    private AppDatabase db;

    private int postNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_posts);

        Window window = getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(getResources().getColor(R.color.white));

        init();
        post_volunteerDetails();
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        requestQueue = Volley.newRequestQueue(getApplicationContext(), null);
        db = AppDatabase.getAppDatabase(getApplicationContext());
        mRecyclerViewItems.clear();
    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, 1));
        VRMapAdapter vrMapAdapter = new VRMapAdapter(mRecyclerViewItems);
        recyclerView.setAdapter(vrMapAdapter);
    }

    private void post_volunteerDetails() {
        Map<String, String> params = new HashMap<>();
        params.put("VolunteerID", String.valueOf(db.userDao().getVolunteerID()));

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                GET_VRMAP, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseVRDetails(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.api_fail), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        jsonObjReq.setTag("VRDetails");
        requestQueue.add(jsonObjReq);
    }

    private void parseVRDetails(JSONObject jsonObject) {
        try {
            if (jsonObject.getBoolean("isSuccess")) {
                postNumber = 0;
                mRecyclerViewItems.clear();
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
        VRMapModel vrMapModel = new VRMapModel();
        try {
            postNumber++;
            vrMapModel.setPost("Post #" + postNumber);
            vrMapModel.setVRMapID(jsonObject1.getInt("VRMapID"));
            vrMapModel.setDescription(jsonObject1.getString("Description"));
            mRecyclerViewItems.add(vrMapModel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class VRMapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final List<Object> mRecyclerViewItems_adapter;

        VRMapAdapter(List<Object> mRecyclerViewItem) {
            this.mRecyclerViewItems_adapter = mRecyclerViewItem;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            view = LayoutInflater.from(VolunteerPosts.this).inflate(R.layout.custom_my_posts, parent, false);
            return new GroupViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof GroupViewHolder) {
                GroupViewHolder groupViewHolder = (GroupViewHolder) holder;
                final VRMapModel vrMapModel = (VRMapModel) mRecyclerViewItems_adapter.get(position);
                String description = "Description - " + vrMapModel.getDescription();
                groupViewHolder.tvName.setText(vrMapModel.getPost());
                groupViewHolder.tvDescription.setText(description);
                groupViewHolder.btOpen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showCompleteDialog(vrMapModel.getVRMapID());
                    }
                });
            }
        }

        private class GroupViewHolder extends RecyclerView.ViewHolder {

            private TextView tvName, tvDescription;
            private MaterialButton btOpen;

            GroupViewHolder(View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tvName);
                tvDescription = itemView.findViewById(R.id.tvDescription);
                btOpen = itemView.findViewById(R.id.btOpen);
            }
        }

        @Override
        public int getItemCount() {
            return mRecyclerViewItems_adapter.size();
        }
    }

    private void showCompleteDialog(final int VRMapID) {
        new AlertDialog.Builder(VolunteerPosts.this)
                .setTitle(getString(R.string.close_complete))
                .setMessage(getString(R.string.complete_description))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.okay), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        post_updateIsComplete(String.valueOf(VRMapID));
                    }
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .show();
    }

    private void post_updateIsComplete(String VRMapID) {
        Map<String, String> params = new HashMap<>();
        params.put("VRMapID", VRMapID);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                UPDATE_VRMAP_ISCOMPLETE, new JSONObject(params),
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

        jsonObjReq.setTag("UpdateIsComplete");
        requestQueue.add(jsonObjReq);
    }

    private void parseResponse(JSONObject jsonObject) {
        try {
            if (jsonObject.getBoolean("isSuccess")) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.success), Snackbar.LENGTH_LONG);
                snackbar.show();
                post_volunteerDetails();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
