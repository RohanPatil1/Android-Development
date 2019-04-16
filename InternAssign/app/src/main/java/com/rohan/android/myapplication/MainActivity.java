package com.rohan.android.myapplication;

import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    TextView updgradeTV;
    RecyclerView subjectListView;
    List<SubjectModel> subDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        subjectListView = findViewById(R.id.subject_RV);
        toolbar = findViewById(R.id.toolbar);
        updgradeTV = findViewById(R.id.upgradeTV);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        setUpNavDrawer();

        String first = "Your free trial is expired on ";
        String next = "<font color='#00BCD4'><strong>29 Jan 2019</strong></font><br>";
        String end = " please upgrade now to get full access";
        updgradeTV.setText(Html.fromHtml(first + next + end));
        subDataList = getData();


        //SetUp Subject RecyclerView
        subjectListView.setLayoutManager(new LinearLayoutManager(this));
        subjectListView.setItemAnimator(new DefaultItemAnimator());
        subjectListView.setAdapter(new SubjectAdapter(this, subDataList));

    }

    private List<SubjectModel> getData() {
        List<SubjectModel> dataList = new ArrayList<>();
        SubjectModel sub1 = new SubjectModel("Mental Ability", R.drawable.subject1);
        SubjectModel sub2 = new SubjectModel("Physics", R.drawable.subject2);
        SubjectModel sub3 = new SubjectModel("Chemistry", R.drawable.subject3);
        SubjectModel sub4 = new SubjectModel("Mathematics", R.drawable.subject4);
        dataList.add(sub1);
        dataList.add(sub2);
        dataList.add(sub3);
        dataList.add(sub4);
        return dataList;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void setUpNavDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Toggle SetUp
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        drawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
    }
}
