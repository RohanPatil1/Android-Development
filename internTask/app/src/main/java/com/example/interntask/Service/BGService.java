package com.example.interntask.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class BGService extends Service {


    private final IBinder mBinder = new LocalService();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    public class LocalService extends Binder {
        public BGService getService() {
            return BGService.this;
        }
    }

    public String getUserMsg(String userInputText) {
        return "Hello, " + userInputText;
    }


}
