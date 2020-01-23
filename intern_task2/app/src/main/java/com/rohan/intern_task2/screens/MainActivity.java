package com.rohan.intern_task2.screens;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.rohan.intern_task2.R;
import com.rohan.intern_task2.adapter.AptRecyclerAdapter;
import com.rohan.intern_task2.data_models.Appointment;
import com.rohan.intern_task2.utils.NotifReciever;


import java.util.ArrayList;

import java.util.GregorianCalendar;
import java.util.List;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FloatingActionButton fab;
    public List<Appointment> aptDataList = new ArrayList<>();
    FirebaseFirestore mFirestore;
    FirebaseUser mCurrUser;
    FirebaseAuth mAuth;
    AptRecyclerAdapter mAdapter;
    public static final int TWO_HOURS_IN_MILLIS = 7200000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.makeAptBTN);
        mRecyclerView = findViewById(R.id.aptRV);

        new MaterialShowcaseView.Builder(this)
                .setTarget(fab)
                .setDismissText("GOT IT")
                .setContentText("Create an appointment and get notify within 2 hours.")
                .setDelay(500) // optional but starting animations immediately in onCreate can make them choppy
                .singleUse("fab_showcase") // provide a unique ID used to ensure it is only shown once
                .show();


        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mCurrUser = mAuth.getCurrentUser();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadAppointments2();

        mAdapter = new AptRecyclerAdapter(aptDataList, this);
        mRecyclerView.setAdapter(mAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Creating an appointment with dummy data
                makeAppointment("Info Inc", "Sarthak", "9874561230", System.currentTimeMillis());


            }
        });

    }


    //Load All Appointments from Firebase collection name "appointment"
    private void loadAppointments2() {
        mFirestore.collection("appointments").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {

                    Appointment currApt = new Appointment(
                            documentChange.getDocument().getData().get("customer").toString(),
                            documentChange.getDocument().getData().get("name").toString(),
                            documentChange.getDocument().getData().get("phone_number").toString(),
                            (long) documentChange.getDocument().getData().get("timestamp")
                    );
                    aptDataList.add(currApt);


                    Log.d("TAG", "DATA: " + aptDataList.toString());
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        mAdapter = new AptRecyclerAdapter(aptDataList, this);
        mRecyclerView.setAdapter(mAdapter);

    }


    //Add each appointment to firestore to collection named "appointment"
    private void makeAppointment(String customer, String name, String phone, long timestamp) {

        Appointment appointment = new Appointment(customer, name, phone, timestamp);

        mFirestore.collection("appointments").add(appointment).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        });

        //Send Notification To User after 2 Hours
        notifyUser();
        loadAppointments2();

    }


    public void notifyUser() {


        //To Test for 10 seconds,uncomment this line

//    Long alertTime = new GregorianCalendar().getTimeInMillis() + 10 * 1000;
        long alertTime = new GregorianCalendar().getTimeInMillis() + TWO_HOURS_IN_MILLIS;
        Intent intent = new Intent(this, NotifReciever.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime, PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        }
    }
}