package com.rohan.potholesfinder.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.auth.FirebaseUser;
import com.rohan.potholesfinder.R;

public class LoginActivity extends AppCompatActivity {


    TextView signupTV;
    EditText emailET, passwordET;
    Button signinBTN;
    FirebaseAuth mAuth;
    FirebaseUser currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailET = findViewById(R.id.signin_emailET);
        passwordET = findViewById(R.id.signin_passwordET);
        signupTV = findViewById(R.id.signupTV);
        signinBTN = findViewById(R.id.signinBTN);
        currUser=FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();


        signupTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                finish();
            }
        });


        signinBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    firebaseSignIn(emailET.getText().toString(), passwordET.getText().toString());
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Input", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    boolean validateInput() {
        CharSequence email = emailET.getText().toString();
        boolean isEmailValidate = !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
        return isEmailValidate;
    }

    private void firebaseSignIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "SignIn Failed : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(currUser!=null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }
    }
}
