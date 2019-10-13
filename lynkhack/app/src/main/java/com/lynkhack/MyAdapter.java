package com.lynkhack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Map<String, Object>> mDataset;
    private Location current_location;
    Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Map<String, Object>> list, Location locationLocal, Context context) {
        mDataset = list;
        current_location = locationLocal;
        this.context = context;
    }


    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.name.setText(mDataset.get(position).get("username").toString());
        holder.status.setText(mDataset.get(position).get("Phone").toString());
        if (mDataset.get(position).get("lat") != null && mDataset.get(position).get("long") != null) {
            holder.location.setText(mDataset.get(position).get("lat").toString() + " " + mDataset.get(position).get("long").toString());


            if (current_location != null) {
                double cur_lat, cur_long;
                double user_lat, user_long;

                cur_lat = current_location.getLatitude();
                cur_long = current_location.getLongitude();

                user_lat = Double.parseDouble(mDataset.get(position).get("lat").toString());
                user_long = Double.parseDouble(mDataset.get(position).get("long").toString());

                holder.mButtonConnectOffline.setVisibility(View.VISIBLE);
                if (Math.round(distance(cur_lat,cur_long,user_lat,user_long)) <= 5){
                    holder.mButtonConnectOffline.setText("Lynk ME!! with "+mDataset.get(position).get("username"));
                    holder.location.setText("Around "+Math.round(distance(cur_lat,cur_long,user_lat,user_long))+" mtr. You can connect with "+mDataset.get(position).get("username")+" offline");
                }else{
                    holder.mButtonConnectOffline.setVisibility(View.GONE);
                }


            }else {
                holder.mButtonConnectOffline.setVisibility(View.GONE);
            }

        } else {
            holder.location.setText("Location not available");
            holder.mButtonConnectOffline.setVisibility(View.GONE);
        }

        holder.mButtonConnectOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = context.getPackageManager().getLaunchIntentForPackage("org.drulabs.localdash");
                i.putExtra("FROM_LYNK","Connect with NGO NAVEEN:8903877274 ");
                if (i!=null){
                    context.startActivity(i);
                }
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();

    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, location, status;
        Button mButtonConnectOffline;

        public MyViewHolder(@NonNull View v) {
            super(v);
            name = v.findViewById(R.id.dashboard_name_custom_textview);
            location = v.findViewById(R.id.dashboard_location_custom_textview);
            status = v.findViewById(R.id.dashboard_status_custom_textview);

            mButtonConnectOffline = v.findViewById(R.id.dashboard_connect_offline_custom_button);

        }
    }
}