package com.rohan.android.inventoryapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class StoreContract {

    private StoreContract() {
    }

    public static final String CONTENT_AUTHORITY = "com.rohan.android.inventoryapp.data.StoreProvider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PRODUCTS = "products";

    public static final class ProductEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCTS);
        public static final String TABLE_NAME = "products";
        public static final String _ID = BaseColumns._ID;
        public static final String PRODUCT_NAME = "Name";
        public static final String PRICE = "Price";
        public static final String QUANTITY = "Quantity";
        public static final String SUPPLIER = "Supplier";
        public static final String SUPPLIER_NO = "SupplierNumber";
        }
}
