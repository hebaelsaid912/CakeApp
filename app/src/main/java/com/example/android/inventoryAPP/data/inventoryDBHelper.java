package com.example.android.inventoryAPP.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class inventoryDBHelper extends SQLiteOpenHelper {
    public static final int DBversion = 1;
    public static final String DBName = "inventoryAPP";
    public inventoryDBHelper( Context context) {
        super(context, DBName, null, DBversion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE products ("+ inventoryConteract.ProductsEntry.column_id+" INTEGER,"
                +inventoryConteract.ProductsEntry.column_Product_Name+" TEXT,"
                +inventoryConteract.ProductsEntry.column_Product_Price+" TEXT,"
                +inventoryConteract.ProductsEntry.column_Product_Quantity+" TEXT,"
                +inventoryConteract.ProductsEntry.column_Product_Supplier+" INTEGER,"
                +inventoryConteract.ProductsEntry.column_product_Available+" INTEGER,"
                +inventoryConteract.ProductsEntry.column_product_Picture+" BLOB);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
