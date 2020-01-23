package com.rohan.intern_task2.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rohan.intern_task2.R;
import com.rohan.intern_task2.data_models.Appointment;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.List;


public class AptRecyclerAdapter extends RecyclerView.Adapter<AptRecyclerAdapter.MyViewHolder> {


    private List<Appointment> aptDataList;
    private Context mContext;

    public AptRecyclerAdapter(List<Appointment> aptDataList, Context mContext) {
        this.aptDataList = aptDataList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.appointment_layout, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.aptName.setText(aptDataList.get(position).getName());
        holder.aptPhone.setText(aptDataList.get(position).getPhone_number());

        holder.aptTime.setText(getDate(aptDataList.get(position).getTimestamp()));

    }

    @Override
    public int getItemCount() {
        return aptDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView aptName, aptPhone, aptTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            aptName = itemView.findViewById(R.id.aptNameTV);
            aptPhone = itemView.findViewById(R.id.aptPhoneTV);
            aptTime = itemView.findViewById(R.id.aptTimeTV);


        }
    }


    private String getDate(long time) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
        String dateTime = sdf.format(cal.getTime());
        return dateTime;

    }

}
