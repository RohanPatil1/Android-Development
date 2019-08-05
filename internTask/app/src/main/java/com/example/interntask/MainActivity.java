package com.example.interntask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.interntask.Service.BGService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button startBtn, stopBtn, sendBtn;
    EditText inputET;
    String inputText;
    BGService myService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn = findViewById(R.id.start_service_btn);
        stopBtn = findViewById(R.id.stop_service_btn);
        sendBtn = findViewById(R.id.send_btn);
        inputET = findViewById(R.id.input_et);

        startBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
        sendBtn.setOnClickListener(this);
    }


    //Starts Background Service
    private void startBgService() {
        Intent intent = new Intent(this, BGService.class);
        bindService(intent, mConn, Context.BIND_AUTO_CREATE);

        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
    }

    //Stops Background Service
    private void stopBgService() {
        unbindService(mConn);
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
    }

    //Sends String text to  Background Service
    private void sendDataBG() {

        //Gets modified text from BGService
        String data = myService.getUserMsg(inputText);
        Toast.makeText(myService, data, Toast.LENGTH_SHORT).show();
        //Set Text
        inputET.setText(data);

    }

    //Checks Service's Status
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_service_btn:
                if (isMyServiceRunning(BGService.class)) {
                    Toast.makeText(MainActivity.this, "Service is already started!", Toast.LENGTH_SHORT).show();
                } else {
                    startBgService();
                }
                break;

            case R.id.stop_service_btn:
                if (!isMyServiceRunning(BGService.class)) {
                    Toast.makeText(MainActivity.this, "Service is not yet started!", Toast.LENGTH_SHORT).show();

                } else {
                    stopBgService();
                }
                break;

            case R.id.send_btn:
                if (!inputET.getText().toString().isEmpty()) {
                    inputText = inputET.getText().toString();
                    sendDataBG();
                } else {
                    Toast.makeText(MainActivity.this, "Input is Empty!", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }


    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {

            BGService.LocalService localService = (BGService.LocalService) service;
            myService = localService.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };


}
