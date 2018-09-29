package com.rohan.android.inventoryapp;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import static com.rohan.android.inventoryapp.data.StoreContract.ProductEntry.CONTENT_URI;
import static com.rohan.android.inventoryapp.data.StoreContract.ProductEntry.PRICE;
import static com.rohan.android.inventoryapp.data.StoreContract.ProductEntry.PRODUCT_NAME;
import static com.rohan.android.inventoryapp.data.StoreContract.ProductEntry.QUANTITY;
import static com.rohan.android.inventoryapp.data.StoreContract.ProductEntry.SUPPLIER;
import static com.rohan.android.inventoryapp.data.StoreContract.ProductEntry.SUPPLIER_NO;
import static com.rohan.android.inventoryapp.data.StoreContract.ProductEntry._ID;

public class StoreListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "StoreActivity";
    CursorAdaptor adapter;
    Cursor cursor;      //list_item_cursor
    Cursor cursor1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);

        getLoaderManager().initLoader(10, null, this);

        String[] projection = {
                _ID,
                PRODUCT_NAME,
                PRICE,
                QUANTITY,
                SUPPLIER,
                SUPPLIER_NO
        };
        cursor1 = getContentResolver().query(CONTENT_URI, projection, null, null, null);
        adapter = new CursorAdaptor(this, cursor1, 0);

        View empty_view = findViewById(R.id.empty_view);
        ListView store_listView = findViewById(R.id.store_listView);
        store_listView.setEmptyView(empty_view);
        store_listView.setAdapter(adapter);
        store_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), "You Clicked!", Toast.LENGTH_SHORT).show();

                cursor = (Cursor) parent.getItemAtPosition(position);
                int _id = cursor.getInt(cursor.getColumnIndex(_ID));
                String name = cursor.getString(cursor.getColumnIndex(PRODUCT_NAME));
                int price = cursor.getInt(cursor.getColumnIndex(PRICE));
                int quantity = cursor.getInt(cursor.getColumnIndex(QUANTITY));
                String supplier = cursor.getString(cursor.getColumnIndex(SUPPLIER));
                String supplier_no = cursor.getString(cursor.getColumnIndex(SUPPLIER_NO));

                Intent intent = new Intent(StoreListActivity.this, QueryActivity.class);
                intent.putExtra("_id", _id);
                intent.putExtra("name", name);
                intent.putExtra("price", price);
                intent.putExtra("quantity", quantity);
                intent.putExtra("supplier", supplier);
                intent.putExtra("supplier_no", supplier_no);
                Log.i(TAG, "You clicked : " + name + price + quantity + supplier + supplier_no);
                startActivity(intent);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                _ID,
                PRODUCT_NAME,
                PRICE,
                QUANTITY,
                SUPPLIER,
                SUPPLIER_NO
        };
        return new CursorLoader(this, CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
