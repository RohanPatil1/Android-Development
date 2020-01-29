package com.rohan.potholesfinder.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rohan.potholesfinder.R;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {


    EditText emailET, passwordET, nameET, phoneET, confirmpassET;
    Button signupBTN;
    FirebaseAuth mAuth;
FirebaseFirestore mFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        signupBTN = findViewById(R.id.signupBTN);
        nameET = findViewById(R.id.nameET);
        phoneET = findViewById(R.id.phoneET);
        confirmpassET = findViewById(R.id.confirmpassET);
        mFirestore=FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        signupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {



                        firebaseSignUp(emailET.getText().toString(),passwordET.getText().toString());
                } else {
                    Toast.makeText(SignUpActivity.this, "Invalid Input", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void firebaseSignUp(String email, String password) {

    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                addUserFirestore();
                startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                finish();
            }
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(SignUpActivity.this, "Failed: "+e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    });

    }


    boolean validateInput() {
        CharSequence email = emailET.getText().toString();
        boolean isEmailValidate = !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
        boolean isPasswordValidate = !TextUtils.isEmpty(passwordET.getText().toString()) && !TextUtils.isEmpty(confirmpassET.getText().toString()) && (passwordET.getText().toString().equals(confirmpassET.getText().toString()));
        boolean isPhoneValidate = android.util.Patterns.PHONE.matcher(phoneET.getText().toString()).matches();
        boolean isNameValidate = !nameET.getText().toString().isEmpty();


        return isEmailValidate && isPasswordValidate && isPhoneValidate && isNameValidate;
    }


    private void addUserFirestore(){
         Map<String,String> mData = new HashMap<>();
         mData.put("name",nameET.getText().toString());
         mData.put("email",emailET.getText().toString());
         mData.put("phone",phoneET.getText().toString());
        mFirestore.collection("users").document().set(mData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                  Log.d("TAG","User Added");

                }
            }
        });
    }

}
