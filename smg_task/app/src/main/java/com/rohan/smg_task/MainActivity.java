package com.rohan.smg_task;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Bitmap myImage;
    private static final String URL = "http://e90cvm.southeastasia.cloudapp.azure.com:8000/getText";
    Button selectBtn, uploadBtn;
    final int PICK_CERTIF_REQUEST = 99;
    ImageView myImageView;
    TextView resultTV;
    String[] resultText = new String[1];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectBtn = findViewById(R.id.selectImgBtn);
        myImageView = findViewById(R.id.myImageView);
        uploadBtn = findViewById(R.id.uploadImgBtn);
        resultTV = findViewById(R.id.resultTV);

        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //                VolleyUtilis.getTextFromImage2(myImage, "myimage.jpeg");
                resultText = VolleyUtilis.getTextFromImage(myImage, "myimage");
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                if (resultText[0] == null) {
                    resultTV.setText("Response : "+"null");
                } else {
                    resultTV.setText("Response : "+resultText[0]);
                }

            }
        });


    }


    public static byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_CERTIF_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_CERTIF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri picturePath = data.getData();
            try {
                myImage = MediaStore.Images.Media.getBitmap(getContentResolver(), picturePath);
                myImageView.setImageBitmap(myImage);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}