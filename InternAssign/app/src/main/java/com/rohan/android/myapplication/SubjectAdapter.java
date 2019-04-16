package com.rohan.android.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.MyViewHolder> {


    private Context context;
    private List<SubjectModel> subjectList;

    public SubjectAdapter(Context context, List<SubjectModel> subjectList) {
        this.context = context;
        this.subjectList = subjectList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.subject__item_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final SubjectModel currItem =subjectList.get(position);
        holder.subjectTV.setText(currItem.getSubjectTitle());
        holder.subjectImageView.setImageResource(currItem.getImageID());

    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView subjectTV;
        ImageView subjectImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            subjectTV = itemView.findViewById(R.id.subjectTextView);
            subjectImageView = itemView.findViewById(R.id.subjectImageView);

        }
    }

}
