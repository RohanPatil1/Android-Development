package com.example.rohanpatil.tourguide;

import android.content.ClipData;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private static  int SPLASH_TIME_OUT = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ListView list_item_view  = (ListView) findViewById(R.id.list_item_view);
        ArrayList<ItemD> items = new ArrayList<>();
        items.add(new ItemD("Tourist Attraction",R.drawable.mumbaig));
        items.add(new ItemD("Beaches",R.drawable.beaches));
        items.add(new ItemD("Hotels",R.drawable.hotels));
        items.add(new ItemD("Amusement Park",R.drawable.amp));
        items.add(new ItemD("Religious Attraction",R.drawable.temple));

        ItemAdapter adapter = new ItemAdapter(this,items);
        list_item_view.setAdapter(adapter);

        list_item_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent homeIntet = new Intent(HomeActivity.this,TouristAttrActivity.class);
                        startActivity(homeIntet);
                        finish();
                    }
                },SPLASH_TIME_OUT);




            }
        });

    }
}
