package com.rohan.android.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import static com.rohan.android.inventoryapp.data.StoreContract.ProductEntry.CONTENT_URI;
import static com.rohan.android.inventoryapp.data.StoreContract.ProductEntry.PRICE;
import static com.rohan.android.inventoryapp.data.StoreContract.ProductEntry.PRODUCT_NAME;
import static com.rohan.android.inventoryapp.data.StoreContract.ProductEntry.QUANTITY;
import static com.rohan.android.inventoryapp.data.StoreContract.ProductEntry.SUPPLIER;
import static com.rohan.android.inventoryapp.data.StoreContract.ProductEntry.SUPPLIER_NO;
import static com.rohan.android.inventoryapp.data.StoreContract.ProductEntry._ID;


public class CursorAdaptor extends CursorAdapter {


    public CursorAdaptor(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        TextView _name = view.findViewById(R.id.name_tv);
        TextView _price = view.findViewById(R.id.price_tv);
        TextView _quantity = view.findViewById(R.id.quantity_tv);
        TextView _supplier = view.findViewById(R.id.supplier_tv);
        TextView _supplier_no = view.findViewById(R.id.supplier_no_tv);
        TextView _saleBtn = view.findViewById(R.id.sale_button);

        String name = cursor.getString(cursor.getColumnIndex(PRODUCT_NAME));
        int price = cursor.getInt(cursor.getColumnIndex(PRICE));
        int quantity = cursor.getInt(cursor.getColumnIndex(QUANTITY));
        String supplier = cursor.getString(cursor.getColumnIndex(SUPPLIER));
        String  supplier_no = cursor.getString(cursor.getColumnIndex(SUPPLIER_NO));

        _name.setText(name);
        _price.setText(String.valueOf(price));
        _supplier.setText(supplier);
        _supplier_no.setText(supplier_no);
        _quantity.setText(String.valueOf(quantity));

        final int _id = cursor.getInt(cursor.getColumnIndex(_ID));
        final int stock_quantity = cursor.getInt(cursor.getColumnIndex(QUANTITY));

        _saleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stock_quantity > 0) {

                    String selection = _ID + " = ? ";
                    String[] selectionArgs = {String.valueOf(_id)};

                    int new_quantity = stock_quantity - 1;
                    ContentValues values = new ContentValues();
                    values.put(QUANTITY, new_quantity);
                    Uri new_uri = ContentUris.withAppendedId(CONTENT_URI, _id);
                    context.getContentResolver().update(new_uri, values, selection, selectionArgs);
                } else {
                    Toast.makeText(context, "No Stock Left !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}


