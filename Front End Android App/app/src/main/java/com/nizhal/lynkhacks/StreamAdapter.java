package com.nizhal.lynkhacks;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ssaim on 13-10-2019.
 */

public class StreamAdapter extends RecyclerView.Adapter<StreamAdapter.MyViewHolder> {

    ArrayList<String> professName;
    ArrayList<String> professImage;
    Context context;

    public StreamAdapter(Context context, ArrayList<String> proName, ArrayList<String> proImg) {
        this.context = context;
        this.professName = proName;
        this.professImage = proImg;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        holder.proName.setText(professName.get(position));
//        holder.proImg.setText(emailIds.get(position));
//        holder.proImg.setImageResource(R.drawable.child);
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(professImage.get(position), "drawable",
                context.getPackageName());
        holder.proImg.setImageDrawable(resources.getDrawable(resourceId));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                Intent i = new Intent(context, ConfirmSelection.class);
                i.putExtra("stream", professName.get(position));
                context.startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return professName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView proName;
        CircleImageView proImg;

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            proName = itemView.findViewById(R.id.pro_name);
            proImg = itemView.findViewById(R.id.pro_img);

        }
    }

}
