package com.rohan.android.assignments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminActivty extends AppCompatActivity {

    FirebaseDatabase mDatabase;//Used to upload the files
    FirebaseStorage mStorage;//Used to Store the URIs of our uploads
    @BindView(R.id.fileNameET)
    EditText fileNameET;
    @BindView(R.id.selectBtn)
    Button selectBtn;
    @BindView(R.id.sectionSpinner)
    Spinner spinner;
    @BindView(R.id.notifyTV)
    TextView notifyTV;
    @BindView(R.id.spinnerSelection)
    TextView showSpinner;
    @BindView(R.id.uploadBtn)
    Button uploadBtn;
    @BindView(R.id.goList)
    Button goList;
    String childName;


    Uri pddfUri;
    String FileName;
    private static final int SELECT_REQUEST_CODE = 99;
    private static final int PERMISSION_REQUEST_CODE = 9;
    @BindView(R.id.progressBarAdmin)
    ProgressBar progressBarAdmin;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);

        mStorage = FirebaseStorage.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        //Ad_Setup
        MobileAds.initialize(AdminActivty.this, "ca-app-pub-4441201959431024~1191281378");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Spinner_Setup
        final String[] sections = {"Assign", "Pract", "Others"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sections);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        showSpinner.setText(sections[0]);
                        childName = sections[0].trim();
                        break;
                    case 1:
                        showSpinner.setText(sections[1]);
                        childName = sections[1].trim();
                        break;

                    case 2:
                        showSpinner.setText(sections[2]);
                        childName = sections[2].trim();
                        break;
                }
            }//OnItemEnds

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });//setOnItemEnds
    }//onCreate()Ends


    @OnClick({R.id.selectBtn, R.id.uploadBtn, R.id.goList})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.selectBtn:
                selectPDF();
                break;
            case R.id.uploadBtn:
                if (pddfUri != null) {
                    uploadFile(pddfUri);
                }
                break;
            case R.id.goList:
                Intent intentGoList = new Intent(AdminActivty.this, SemesterActivity.class);
                intentGoList.putExtra("childName", childName);
                startActivity(intentGoList);
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectPDF();
        } else {
            Toast.makeText(AdminActivty.this, "Permisson Required !", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectPDF() {
        Intent intentPDF = new Intent(Intent.ACTION_VIEW);
        intentPDF.setType("application/pdf");
        intentPDF.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intentPDF.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentPDF, SELECT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_REQUEST_CODE && data != null && resultCode == RESULT_OK) {
            pddfUri = data.getData();
            if (TextUtils.isEmpty(fileNameET.toString())) {
                FileName = "No Name";
            } else {
                FileName = fileNameET.getText().toString();
            }
            notifyTV.setText("Selected File :" + FileName);

        } else {
            Toast.makeText(AdminActivty.this, "Failed to fetch the file", Toast.LENGTH_SHORT).show();

        }
    }

    private void uploadFile(Uri pdfUri) {
//        final String fileName = FileName = FileName + "_" + System.currentTimeMillis() + ".pdf";
        final String fileName_withNoPdf = FileName + System.currentTimeMillis() + "";
        StorageReference storageReference = mStorage.getReference();
        storageReference.child("Uploads").child(childName).child(fileName_withNoPdf).putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String fileUri = taskSnapshot.getDownloadUrl().toString();
                        DatabaseReference reference = mDatabase.getReference();
                        reference.child(childName).child(fileName_withNoPdf).setValue(fileUri).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Intent intent = new Intent();
                                    intent.putExtra("childName", childName);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminActivty.this, "Failed, Error :" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        //Yet To Be Implmented
                        double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        progressBarAdmin.setProgress((int)progress);
                    }
                });
    }
}//ClassEnds


