package com.example.android.inventoryAPP.data;

import android.content.ContentResolver;
import android.net.Uri;

public class inventoryConteract {
    public static final String CONTENT_AUTHORITY = "com.example.android.inventoryAPP";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PRODUCTS = "products";

    // when return multi row
    public static final String CONTENT_LIST_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;
    // when return single row
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS ;


    public static final class ProductsEntry {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCTS);
        public static final Uri CONTENT_URI_ID = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCTS);
        public static final String TABLE_NAME = "products";
        public static final String column_id = "_id";
        public static final String column_Product_Name = "name";
        public static final String column_Product_Price = "price";
        public static final String column_Product_Supplier = "supplier";
        public static final String column_Product_Quantity  = "quantity";
        public static final String column_product_Picture = "picture";
        public static final String column_product_Available = "available";
        public static final int product_Available = 1;
        public static final int product_Not_Available = 0;
        public static final int product_unKnowm_Available = 2;



    }
}
