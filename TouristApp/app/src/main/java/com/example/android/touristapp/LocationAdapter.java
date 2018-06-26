package com.example.android.touristapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {

    private Context context;
    private List<Location> dataList;

    public LocationAdapter(Context context, List<Location> dataList) {
        this.context=context;
        this.dataList = dataList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.list_item_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Location location = dataList.get(position);
        holder.name.setText(location.getName());
        holder.image.setImageResource(location.getImageId());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);
            image=(ImageView) itemView.findViewById(R.id.image);
            name=(TextView) itemView.findViewById(R.id.name);
        }
    }


}
