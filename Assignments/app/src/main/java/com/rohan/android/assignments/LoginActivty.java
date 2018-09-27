package com.rohan.android.assignments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivty extends AppCompatActivity {

    @BindView(R.id.toolbarLogin)
    Toolbar toolbarSem;
    @BindView(R.id.admin_login_et1)
    EditText adminLoginEt1;
    @BindView(R.id.admin_login_et2)
    EditText adminLoginEt2;
    @BindView(R.id.verifyAdmin)
    Button verifyAdmin;

    private String name;
    private String pass_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activty);
        ButterKnife.bind(this);


        toolbarSem.setTitle("");
        setSupportActionBar(toolbarSem);

        verifyAdmin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                name = adminLoginEt1.getText().toString();
                pass_value = adminLoginEt2.getText().toString();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(name)) {

                    if (name.equals(getString(R.string.action_name)) && pass_value.equals(getString(R.string.titlei))) {
                        Toast.makeText(LoginActivty.this, "Welcome,Admin", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivty.this, AdminActivty.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivty.this, "Not ,Admin", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(LoginActivty.this, "Please Fill The Inputs", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }
}
