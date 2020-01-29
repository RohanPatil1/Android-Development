package com.rohan.potholesfinder.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rohan.potholesfinder.R;
import com.rohan.potholesfinder.data_model.News;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {


    List<News> newsDataList;
    Context mContext;

    public NewsAdapter(List<News> newsDataList, Context mContext) {
        this.newsDataList = newsDataList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        Log.d("onBind", newsDataList.toString());
        Log.d("onBind", "------------------------------------------------    ");

        Log.d("onBind", newsDataList.get(0).getDescription());

        holder.title.setText(newsDataList.get(position).getTitle());
       // holder.descrip.setText(newsDataList.get(position).getTitle());
        Glide.with(mContext).load(newsDataList.get(position).getImgUrl())
                .into(holder.img);

        //                holder.title.setText("Title goes here");
//                    holder.descrip.setText("Descrip Goes Here");
//        Glide.with(mContext).load("https://images.livemint.com/img/2020/01/27/600x338/339961778_0-14_1563198063780_1580112724461.jpg")
//                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return newsDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.newsTitleTV);
//            descrip = itemView.findViewById(R.id.descripTV);
            img = itemView.findViewById(R.id.newsImgIMV);

        }
    }
}
