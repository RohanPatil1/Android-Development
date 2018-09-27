package com.rohan.android.assignments;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {


    private Context context;
    private RecyclerView recyclerView;
    private List<String> names;
    private List<String> urls;

    public DataAdapter(Context context, RecyclerView recyclerView, List<String> names, List<String> urls) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.names = names;
        this.urls = urls;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.listnName.setText(names.get(position));
    }

    public void Customupdate(String fileName, String url) {
        names.add(fileName.toString());
        urls.add(url);
        notifyDataSetChanged(); //Refreshes the RecyclerView Automatically
    }


    @Override
    public int getItemCount() {
        return names.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView listnName;

        public MyViewHolder(View itemView) {
            super(itemView);
            listnName = itemView.findViewById(R.id.list_item_name);

            listnName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = recyclerView.getChildLayoutPosition(v);
                    Intent pdfIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urls.get(position)));
                    try {
                        context.startActivity(pdfIntent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(context, "Error," + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }//ViewHolderEnds
}
