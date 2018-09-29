package com.rohan.android.museit;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

//1-8 Thevenin
//9-18 OC and SC Test
//19-27 Mesh Analysis
//27-35 SuperPosition


public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyViewHolder> {
    private static final String TAG = AlbumAdapter.class.getName();
    private Context context;
    private List<AlbumData> dataList;

    public AlbumAdapter(Context context, List<AlbumData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.album_grid_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final AlbumData item = dataList.get(position);
        holder.album_image.setImageResource(item.getImageId());
        holder.album_artist.setText(item.getAlbum_artist());
        holder.album_name.setText(item.getAlbum_name());
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Msg Passed" + item.getImageId());
                Intent intent = new Intent(context, PlayingActivity.class);
                intent.putExtra("image_url", item.getImageId());
                intent.putExtra("name", item.getAlbum_name().toString());
                intent.putExtra("artist", item.getAlbum_artist().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView album_image;
        TextView album_name;
        TextView album_artist;
        FloatingActionButton btn;

        public MyViewHolder(View itemView) {
            super(itemView);
            album_image = (ImageView) itemView.findViewById(R.id.album_image);
            album_name = (TextView) itemView.findViewById(R.id.album_name);
            album_artist = (TextView) itemView.findViewById(R.id.album_artist);
            btn = (FloatingActionButton) itemView.findViewById(R.id.float_button);
        }
    }
}
