package com.rohan.android.inventoryapp;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.rohan.android.inventoryapp.data.StoreContract.ProductEntry.CONTENT_URI;
import static com.rohan.android.inventoryapp.data.StoreContract.ProductEntry.PRICE;
import static com.rohan.android.inventoryapp.data.StoreContract.ProductEntry.PRODUCT_NAME;
import static com.rohan.android.inventoryapp.data.StoreContract.ProductEntry.QUANTITY;
import static com.rohan.android.inventoryapp.data.StoreContract.ProductEntry.SUPPLIER;
import static com.rohan.android.inventoryapp.data.StoreContract.ProductEntry.SUPPLIER_NO;
import static com.rohan.android.inventoryapp.data.StoreContract.ProductEntry._ID;

public class QueryActivity extends AppCompatActivity {

    private EditText etName, etPrice, etSupplier, etSNumber;
    private Button insert_btn, increment, decrement, update_btn, delete_btn, call_btn;
    private TextView quantity_tv;
    private int quantity_value = 0;
    private TextInputLayout inputLayout_name, inputLayout_price, inputLayout_supplier, inputLayout_supplier_no;
    int _id, price, quantity = 0;
    int et_productPrice;
    AlertDialog.Builder builder;

    String name, supplier, supplier_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.warning);

        increment = findViewById(R.id.increment);
        decrement = findViewById(R.id.decrement);
        insert_btn = findViewById(R.id.insert_btn);
        update_btn = findViewById(R.id.update_product);
        delete_btn = findViewById(R.id.delete_product);
        call_btn = findViewById(R.id.call_btn);

        etName = findViewById(R.id.et_name);
        etPrice = findViewById(R.id.et_price);
        quantity_tv = findViewById(R.id.quantity);
        etSupplier = findViewById(R.id.et_supplier);
        etSNumber = findViewById(R.id.et_sNumber);

        inputLayout_name = findViewById(R.id.textInput_name);
        inputLayout_price = findViewById(R.id.textInput_price);
        inputLayout_supplier = findViewById(R.id.textInput_supplier);
        inputLayout_supplier_no = findViewById(R.id.textInput_supplier_no);

        Intent intent = getIntent();
        _id = intent.getIntExtra("_id", 0);
        name = intent.getStringExtra("name");
        price = intent.getIntExtra("price", 0);
        quantity = intent.getIntExtra("quantity", 0);
        quantity_value = quantity;
        supplier = intent.getStringExtra("supplier");
        supplier_no = intent.getStringExtra("supplier_no");


        //if id = 0, it means we want to insert
        if (_id != 0) {
            etName.setText(name.toString());
            etPrice.setText(String.valueOf(price));
            quantity_tv.setText(String.valueOf(quantity).toString());
            etSupplier.setText(supplier.toString());
            etSNumber.setText(supplier_no.toString());
            insert_btn.setVisibility(View.GONE);
        } else {
            update_btn.setVisibility(View.GONE);
            delete_btn.setVisibility(View.GONE);
            call_btn.setVisibility(View.GONE);
        }

        //OnClickListeners
        insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(validate(v)){
                   insertProduct();
               }
            }
        });
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate(v)) {
                    Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                    updateProduct();

                } else {
                    Toast.makeText(getApplicationContext(), "Try Again!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate(v)){
                    deleteProduct();
                }


            }
        });

        //add quantity btn
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity_value += 1;
                display(quantity_value);
            }
        });

        //subtract quantity btn
        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity_value -= 1;
                if (quantity_value <= -1) {
                    quantity_value = 0;
                }
                display(quantity_value);
            }
        });

        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_call = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", supplier_no.toString(), null));
                startActivity(intent_call);

            }
        });
    }//onCreate Ends

    public boolean validate(View view) {

            if (validate_name() && validate_price() && validate_supplier() && validate_supplier_no()) {
                return true;
            }
            else{
                return false;
            }

    }

    private boolean validate_supplier_no() {

        if (etSNumber.getText().toString().isEmpty()) {
            inputLayout_supplier_no.setError("Supplier cannot be blanked");
            return false;
        } else {
            inputLayout_supplier_no.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validate_supplier() {

        if (etSupplier.getText().toString().isEmpty()) {
            inputLayout_supplier.setError("Supplier cannot be blanked");
            return false;
        } else {
            inputLayout_supplier.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validate_price() {

        if (etPrice.getText() != null && !etPrice.getText().toString().equals("")) {
            inputLayout_price.setErrorEnabled(false);
            return true;
        } else {
            inputLayout_price.setError("Price cannot be empty!");
            return false;
        }
    }

    private boolean validate_name() {
        if (etName.getText().toString().isEmpty()) {
            inputLayout_name.setError("Name cannot be blanked");
            return false;
        } else {
            inputLayout_name.setErrorEnabled(false);
            return true;
        }
    }

    /*
    private void deleteProduct() {
        String selection = _ID + " = ? ";
        String[] selectionArgs = {String.valueOf(_id)};

        Uri uri = ContentUris.withAppendedId(CONTENT_URI, _id);
        int rowsDeleted = getContentResolver().delete(uri, selection, selectionArgs);
        }
        */

    private void updateProduct() {

        String selection = _ID + " = ? ";
        String[] selectionArgs = {String.valueOf(_id)};
        String et_productName = etName.getText().toString();

        if (etPrice.getText().toString().isEmpty()) {
            et_productPrice = 0;
        } else {
            et_productPrice = Integer.parseInt(etPrice.getText().toString());
        }

        int tv_productQuantity = Integer.parseInt(quantity_tv.getText().toString());
        String et_productSupplier = etSupplier.getText().toString();
        String et_productSNumber = etSNumber.getText().toString();

        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME, et_productName);
        values.put(PRICE, et_productPrice);
        values.put(QUANTITY, tv_productQuantity);
        values.put(SUPPLIER, et_productSupplier);
        values.put(SUPPLIER_NO, et_productSNumber);

        Integer quantity = values.getAsInteger(QUANTITY);
        if (quantity != null && quantity < 0) {
            throw new IllegalArgumentException("Inventory requires valid weight");
        }
        Integer price = values.getAsInteger(PRICE);
        if (price != null && price < 0) {
            throw new IllegalArgumentException("Inventory requires valid amount");
        }


        Uri uri = CONTENT_URI;
        int rowsUpdated = getContentResolver().update(uri, values, selection, selectionArgs);
        finish();
    }

    private void insertProduct() {
        String et_productName = etName.getText().toString();
        if (etPrice.getText().toString().isEmpty()) {
            et_productPrice = 0;
        } else {
            et_productPrice = Integer.parseInt(etPrice.getText().toString());
        }

        int tv_productQuantity = Integer.parseInt(quantity_tv.getText().toString());
        String et_productSupplier = etSupplier.getText().toString();
        String et_productSNumber = etSNumber.getText().toString();


        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME, et_productName);
        values.put(PRICE, et_productPrice);
        values.put(QUANTITY, tv_productQuantity);
        values.put(SUPPLIER, et_productSupplier);
        values.put(SUPPLIER_NO, et_productSNumber);

        Integer quantity = values.getAsInteger(QUANTITY);
        if (quantity != null && quantity < 0) {
            throw new IllegalArgumentException("Inventory requires valid weight");
        }
        Integer price = values.getAsInteger(PRICE);
        if (price != null && price < 0) {
            throw new IllegalArgumentException("Inventory requires valid amount");
        }


        Uri uri = CONTENT_URI;
        Uri uriRowsInserted = getContentResolver().insert(uri, values);
        finish();
    }

    //for updating the quantity_tv
    public void display(int number) {
        quantity_tv.setText(String.valueOf(number));
    }


    private void deleteProduct() {

        builder.setMessage("Do you want to remove this product ?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selection = _ID + " = ? ";
                String[] selectionArgs = {String.valueOf(_id)};

                Uri uri = ContentUris.withAppendedId(CONTENT_URI, _id);
                int rowsDeleted = getContentResolver().delete(uri, selection, selectionArgs);
                Toast.makeText(QueryActivity.this, "Deleted Row :" + rowsDeleted, Toast.LENGTH_SHORT).show();
                finish();

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
