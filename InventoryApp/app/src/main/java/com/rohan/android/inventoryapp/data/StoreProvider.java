package com.rohan.android.inventoryapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.rohan.android.inventoryapp.data.StoreContract.CONTENT_AUTHORITY;
import static com.rohan.android.inventoryapp.data.StoreContract.PATH_PRODUCTS;
import static com.rohan.android.inventoryapp.data.StoreContract.ProductEntry.TABLE_NAME;
import static com.rohan.android.inventoryapp.data.StoreContract.ProductEntry._ID;

public class StoreProvider extends ContentProvider {

    private static final String TAG = StoreProvider.class.getSimpleName();
    private StoreDbHelper mDbHelper;

    //constants for the operation
    private static final int PRODUCTS = 1; //For Whole Table
    private static final int PRODUCTS_ID = 2; //For specific row in a table identified by _ID(Integer)
    private static final int PRODUCTS_NAME = 3;//For specific row in a table by name identified by product name(String)
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_PRODUCTS, PRODUCTS);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_PRODUCTS + "/#", PRODUCTS_ID);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_PRODUCTS + "/*", PRODUCTS_NAME);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new StoreDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        switch (uriMatcher.match(uri)) {
            case PRODUCTS:
                cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException(TAG + "Unknown URI: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        switch (uriMatcher.match(uri)) {
            case PRODUCTS:
                return insertProduct(uri, values, TABLE_NAME);

            default:
                throw new IllegalArgumentException(TAG + "Unknown URI: " + uri);
        }
    }

    private Uri insertProduct(Uri uri, ContentValues values, String tableName) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long rowId = db.insert(tableName, null, values);

        if (rowId == -1) {
            Log.e(TAG, "Insert error for URI :" + uri);

            return null;
        } else {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return ContentUris.withAppendedId(uri, rowId);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        switch (uriMatcher.match(uri)) {
            case PRODUCTS:
                return deleteRecords(uri, null, null, TABLE_NAME);

            case PRODUCTS_ID:
                selection = _ID + " = ? ";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return deleteRecords(uri, selection, selectionArgs, TABLE_NAME);

            default:
                throw new IllegalArgumentException(TAG + "Unkown Uri :" + uri);
        }
    }

    private int deleteRecords(Uri uri, String selection, String[] selectionArgs, String tableName) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowsDeleted = db.delete(tableName, selection, selectionArgs);

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        switch (uriMatcher.match(uri)) {

            case PRODUCTS:
                return updateRecord(uri, values, selection, selectionArgs, TABLE_NAME);

            case PRODUCTS_ID:
                return updateRecord(uri, values, selection, selectionArgs, TABLE_NAME);

            default:
                throw new IllegalArgumentException("Unkonwn URI : " + uri);
        }
    }

    private int updateRecord(Uri uri, ContentValues values, String selection, String[] selectionArgs, String tableName) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowsUpdated = db.update(tableName, values, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}