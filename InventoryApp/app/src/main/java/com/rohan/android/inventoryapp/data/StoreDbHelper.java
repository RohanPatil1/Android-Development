package com.rohan.android.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.rohan.android.inventoryapp.data.StoreContract.ProductEntry;



public class StoreDbHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "store.db";
    private static final int DATABASE_VERSION = 1;
    public StoreDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + ProductEntry.TABLE_NAME + " ("
                + ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ProductEntry.PRODUCT_NAME + " TEXT NOT NULL, "
                + ProductEntry.PRICE + " INTEGER NOT NULL DEFAULT 0, "
                + ProductEntry.QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                + ProductEntry.SUPPLIER + " TEXT NOT NULL, "
                + ProductEntry.SUPPLIER_NO + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //No Updgrade Needed
    }
}
