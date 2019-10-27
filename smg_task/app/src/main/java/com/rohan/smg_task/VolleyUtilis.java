package com.rohan.smg_task;


import android.graphics.Bitmap;

import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class VolleyUtilis {
    public static int id;
    private static VolleyUtilis volleyUtils;
    private static RequestQueue requestQueue;
    private static final String URL = "http://e90cvm.southeastasia.cloudapp.azure.com:8000/getText";


    public static VolleyUtilis getVolleyUtils() {
        if (volleyUtils == null) {
            volleyUtils = new VolleyUtilis();
        }
        return volleyUtils;
    }

    public static RequestQueue getRequestQueue() {

        if (volleyUtils == null) {
            requestQueue = Volley.newRequestQueue(ApplicationController.getContext());

        }
        return requestQueue;
    }

    public static String[] getTextFromImage(final Bitmap bitmap, final String name) {
        final String[] resultText = new String[1];

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));

                            resultText[0] = obj.getString("data");

                            Log.d("SMGG", "onResponse: " + obj.getString("data"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("SMGG", "onResponse: " + error.getMessage());
                        Toast.makeText(ApplicationController.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("filename=", String.valueOf(name));
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
//                long imagename = System.currentTimeMillis();

                params.put("name=", new DataPart(name + ".jpeg", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        getRequestQueue().add(volleyMultipartRequest);
        return resultText;
    }

    public static byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }






    public static void getTextFromImage2(Bitmap profilePic,String name)     {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        profilePic.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        final String base64image = Base64.encodeToString(imageBytes, Base64.DEFAULT);



        HashMap data = new HashMap();


        data.put("name", base64image);
        data.put("filename", name);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(data),

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("SMGG", "onResponse:  " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("SMGG", "onErrorResponse:  " + error.toString());
                    }
                }
        );
        getRequestQueue().add(jsonObjReq);


    }


}
