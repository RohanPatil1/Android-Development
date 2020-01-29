package com.rohan.potholesfinder.LocationService;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdressIntentService  extends IntentService {

    protected ResultReceiver receiver;
    private static final String TAG = "Service";

    public AdressIntentService() {
        super("DisplayNotification");
    }

    public AdressIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null)
            return;

        Location location = intent.getParcelableExtra("location");
        receiver = intent.getParcelableExtra("receiver");
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses = null;
        String errorMessage = "";
        try {
            addresses = geocoder.getFromLocation(
                    location != null ? location.getLatitude() : 0,
                    location != null ? location.getLongitude() : 0,
                    1);
        } catch (IOException ioException) {
            errorMessage = "Service not available";
            Log.d(TAG, "onHandleIntent: " + ioException.toString());
        } catch (IllegalArgumentException illegalArgumentException) {
            errorMessage = "invalid longitude latitude used";
            Log.d(TAG, "onHandleIntent: latitude = "
                    + (location != null ? location.getLatitude() : 0) + " ,Longitude = " +
                    location.getLongitude() + "\n"
                    + illegalArgumentException.toString());
        }

        if (addresses == null || addresses.size() == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = "No Address found";
                Log.d(TAG, "onHandleIntent: " + errorMessage);
            }
            deliverResultToReceiver(0, errorMessage);
        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressFragment = new ArrayList<String>();
//
//            DataModel model = new DataModel(
//                    address.getCountryCode(),
//                    address.getPhone(),
//                    address.getSubLocality(),
//                    address.getLocality(),
//                    address.getAdminArea(),
//                    address.getSubAdminArea(),
//                    address.getFeatureName()
//            );
//            deliverResultToReceiver(1,model);
            for (int i = 0; i <= address.getMaxAddressLineIndex(); i++){
                addressFragment.add(address.getAddressLine(i));
            }
            Log.d(TAG, "onHandleIntent: Address found");
            deliverResultToReceiver(1,
                    TextUtils.join(System.getProperty("line.separator"),addressFragment));
        }

    }

    private void deliverResultToReceiver(int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putString("data_key", message);
        receiver.send(resultCode,bundle);

    }
//    private void deliverResultToReceiver(int resultCode, DataModel model) {
//        Bundle bundle = new Bundle();
//        String message = "Admin area "+model.mAdminArea
//                +"\nCountry code "+model.mCountryCode
//                +"\nFeature name "+model.mFeatureName
//                +"\nLocality "+model.mLocality
//                +"\nSub locality "+model.mSubLocality
//                +"\nSub Admin Area"+model.mSubAdminArea
//                +"\nPhone "+model.mPhone;
//        bundle.putString("data_key", message);
//        receiver.send(resultCode,bundle);
//
//    }
}
