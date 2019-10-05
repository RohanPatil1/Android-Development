package com.rohan.waymaps.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rohan.waymaps.DataModel.Place;
import com.rohan.waymaps.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.MyViewHolder> {
    List<Place> dataList;
    Context context;
    OnPlaceClickListener mOnPlaceClickListener;

    public PlacesAdapter(Context context, List<Place> dataList, OnPlaceClickListener onPlaceClickListener) {
        this.context = context;
        this.dataList = dataList;
        this.mOnPlaceClickListener = onPlaceClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(view, mOnPlaceClickListener);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Place currentPlaceName = dataList.get(position);
        holder.ratingBar.setRating(currentPlaceName.getPlaceRating());
        holder.txt.setText(currentPlaceName.getPlaceName());
        holder.vicinity.setText(currentPlaceName.getVicinity());


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView txt,vicinity;
        hyogeun.github.com.colorratingbarlib.ColorRatingBar ratingBar;
        OnPlaceClickListener onPlaceClickListener;

        public MyViewHolder(@NonNull View itemView, OnPlaceClickListener onPlaceClickListener) {
            super(itemView);


            txt = itemView.findViewById(R.id.placeName);
            vicinity = itemView.findViewById(R.id.placeVicinity);
            ratingBar = itemView.findViewById(R.id.placeRating);
            this.onPlaceClickListener = onPlaceClickListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            onPlaceClickListener.onPlaceClick(getAdapterPosition());
        }
    }

    public interface OnPlaceClickListener {

        void onPlaceClick(int position);
    }


}
