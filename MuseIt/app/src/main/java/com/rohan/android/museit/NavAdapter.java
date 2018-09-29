package com.rohan.android.museit;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NavAdapter extends RecyclerView.Adapter<NavAdapter.myViewHolder> {
    private Context context;
    private List<NavData> dataList;

    public NavAdapter(Context context, List<NavData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.nav_list_view, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        NavData navItem = dataList.get(position);
        holder.image.setImageResource(navItem.getImageId());
        holder.name.setText(navItem.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentN = new Intent(context, PlayingActivity.class);
                context.startActivity(intentN);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        public myViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.nav_image);
            name = (TextView) itemView.findViewById(R.id.nav_name);
        }
    }
}
