package com.example.android.inventoryAPP.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;


public class inventoryAPP_Provider extends ContentProvider {
    inventoryDBHelper mDbHelper;
    public static final String LOG_TAG = inventoryAPP_Provider.class.getSimpleName();
    private static final int Products = 100;

    private static final int Products_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(inventoryConteract.CONTENT_AUTHORITY, "products", Products);


        sUriMatcher.addURI(inventoryConteract.CONTENT_AUTHORITY, "products/#", Products_ID);

    }

    @Override
    public boolean onCreate() {
        mDbHelper = new inventoryDBHelper(getContext());
        return true;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case Products:
                cursor = db.query(inventoryConteract.ProductsEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case Products_ID:
                selection = inventoryConteract.ProductsEntry.column_id + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = db.query(inventoryConteract.ProductsEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        Log.e("main","match is    " + match);

        switch (match) {
            case Products:
                return insertProduct(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertProduct(Uri uri, ContentValues contentValues) {
        if (contentValues.containsKey(inventoryConteract.ProductsEntry.column_Product_Name)) {
            String name = contentValues.getAsString(inventoryConteract.ProductsEntry.column_Product_Name);
            if (name == null) {
                throw new IllegalArgumentException("Product requires a name");
            }
        }

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Long newRowID = db.insert(inventoryConteract.ProductsEntry.TABLE_NAME,null,contentValues);
        if (newRowID == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri, newRowID);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case Products:
                return updatePet(uri, contentValues, selection , selectionArgs);
            case Products_ID:
                selection = inventoryConteract.ProductsEntry.column_id + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updatePet(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private int updatePet(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Perform the update on the database and get the number of rows affected
        int result = db.update(inventoryConteract.ProductsEntry.TABLE_NAME, contentValues, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (result != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return result;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsDeleted;
        final int match = sUriMatcher.match(uri);
        System.out.println("match is    "+match);
        switch (match) {
            case Products:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(inventoryConteract.ProductsEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case Products_ID:
                // Delete a single row given by the ID in the URI
                selection = inventoryConteract.ProductsEntry.column_id + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(inventoryConteract.ProductsEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;

    }
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case Products:
                return inventoryConteract.CONTENT_LIST_TYPE;
            case Products_ID:
                return inventoryConteract.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}

