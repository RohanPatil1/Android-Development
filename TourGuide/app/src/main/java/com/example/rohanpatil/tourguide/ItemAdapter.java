package com.example.rohanpatil.tourguide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohan Patil on 20-04-2018.
 */

public class ItemAdapter extends ArrayAdapter<ItemD> {
    public ItemAdapter(Context context, ArrayList<ItemD> items) {
        super(context, 0, items);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View list_view = convertView;
        if(list_view==null){
            list_view =LayoutInflater.from(getContext()).inflate(R.layout.list_item_view,parent,false);
        }
        ItemD currentItem = getItem(position);

        TextView title = (TextView) list_view.findViewById(R.id.list_item_text);
        title.setText(currentItem.getName());

        ImageView image = (ImageView) list_view.findViewById(R.id.list_item_image);
        image.setImageResource(currentItem.getmImageID());


        return list_view;


    }
}
