package com.rohan.android.museit;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    RecyclerView nav_recyclerView;
    RecyclerView album_recyclerView;
    private static List<NavData> nav_dataList;
    private static List<AlbumData> album_dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nav_recyclerView = (RecyclerView) findViewById(R.id.nav_recyclerView);
        album_recyclerView = (RecyclerView) findViewById(R.id.album_recyclerview);
        nav_dataList = NavData.getData();
        album_dataList = AlbumData.getData();

        //NavRecyclerView
        nav_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        nav_recyclerView.setItemAnimator(new DefaultItemAnimator());
        nav_recyclerView.setAdapter(new NavAdapter(this, nav_dataList));


        //AlbumRecyclerView
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        album_recyclerView.setLayoutManager(mLayoutManager);
        album_recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(0), true));
        album_recyclerView.setItemAnimator(new DefaultItemAnimator());
        album_recyclerView.setAdapter(new AlbumAdapter(this, album_dataList));

        //Toolbar SetUp
        setUpToolbar();
    }

    private void setUpToolbar() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(""); //Else Textview in XML  Overlaps with app name.
        setSupportActionBar(toolbar);
        //Toggle SetUp
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}

