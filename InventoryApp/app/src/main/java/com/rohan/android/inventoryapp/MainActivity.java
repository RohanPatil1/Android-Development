package com.rohan.android.inventoryapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rohan.android.inventoryapp.data.StoreDbHelper;

import static com.rohan.android.inventoryapp.data.StoreContract.ProductEntry.CONTENT_URI;

public class MainActivity extends AppCompatActivity {

    Button insert_activity_btn, show_stocks, delete_all_btn;
    private TextView displayDataView;
    private StoreDbHelper mDbHelper;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        builder = new AlertDialog.Builder(this);
        mDbHelper = new StoreDbHelper(this);
        insert_activity_btn = findViewById(R.id.insert_product);
        show_stocks = findViewById(R.id.show_stock);
        delete_all_btn = findViewById(R.id.delete_table_btn);
        builder.setTitle(R.string.warning);


        //onClickListener
        insert_activity_btn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Intent insert_intent = new Intent(MainActivity.this, QueryActivity.class);
                startActivity(insert_intent);
            }
        });

        show_stocks.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Intent data_intent = new Intent(MainActivity.this, StoreListActivity.class);
                startActivity(data_intent);
            }
        });

        delete_all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });
    }//onCreateEnds


    private void showAlert() {

        builder.setMessage("Do you want to remove all the products ?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri uri = CONTENT_URI;
                int rowsDeleted = getContentResolver().delete(uri, null, null);
                Toast.makeText(MainActivity.this, "Deleted Row :" + rowsDeleted, Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.setTitle(R.string.warning);
        alert.show();
    }
}

