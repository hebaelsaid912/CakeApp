package com.example.android.inventoryAPP;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.android.inventoryAPP.data.DbBitmapUtility;
import com.example.android.inventoryAPP.data.detailsCursorAdapter;
import com.example.android.inventoryAPP.data.detailsList;
import com.example.android.inventoryAPP.data.detailsListAdapter;
import com.example.android.inventoryAPP.data.inventoryConteract;
import com.example.android.inventoryAPP.data.inventoryCursorAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final int PRODUCT_LOADER = 0 ;
    inventoryCursorAdapter CurserAdapter ;
    detailsCursorAdapter detailsCurserAdapter ;
    detailsListAdapter actionList;
    public static int pos = 0;
    public static Uri shareID ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overall_activity);
        // Write a message to the database
        // Setup FAB to open AddActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });
        Log.i("m", "iiiiiiiiiiiiiiiiiiiiii jjjjjjjjjjjjjjjj ");
        // Find the ListView which will be populated with the product data
        ListView productListView = (ListView) findViewById(R.id.list);
        View emptyView = findViewById(R.id.empty_view);
        productListView.setEmptyView(emptyView);
        CurserAdapter = new inventoryCursorAdapter(this,null);
        productListView.setAdapter(CurserAdapter);
     //   ArrayList<detailsList> detailsListArrayList = new ArrayList<detailsList>();
      //  actionList = new detailsListAdapter(detailsListArrayList,null);
        //productListView.setAdapter(actionList);
        getLoaderManager().initLoader(PRODUCT_LOADER,null,this);
       productListView.setOnItemClickListener((adapterView, view, position, id) -> {
           pos = position;
           Log.d("m", "onItemClick: "+ position + " klsdhf" + id);
           Intent intent = new Intent(MainActivity.this,Details.class);
           Uri currntURI = ContentUris.withAppendedId(inventoryConteract.ProductsEntry.CONTENT_URI,id);
           Log.d("m", "currntURI: "+ currntURI);
            shareID= currntURI;
           intent.setData(currntURI);
           startActivity(intent);
       });
       productListView.setSelection(1);
      //  displayDatabaseInfo();
        // Read from the database

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }
    public ContentValues insertDummyData(){
        ContentValues values = new ContentValues();
        values.put(inventoryConteract.ProductsEntry.column_id,2);
        values.put(inventoryConteract.ProductsEntry.column_Product_Name,"cake");
        values.put(inventoryConteract.ProductsEntry.column_Product_Price,10);
        values.put(inventoryConteract.ProductsEntry.column_Product_Quantity,90);
        values.put(inventoryConteract.ProductsEntry.column_Product_Supplier,"Birthday cake");
        values.put(inventoryConteract.ProductsEntry.column_product_Available,inventoryConteract.ProductsEntry.product_Available);
        ImageView im = new ImageView(this);
        im.setImageResource(R.drawable.cake);
        Bitmap img = ((BitmapDrawable)im.getDrawable()).getBitmap();
        byte[] imageByte=DbBitmapUtility.getBytes(img);;
        values.put(inventoryConteract.ProductsEntry.column_product_Picture,imageByte);
        return values;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                Uri newRowURI = getContentResolver().insert(inventoryConteract.ProductsEntry.CONTENT_URI,insertDummyData());
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                int rowID = getContentResolver().delete(inventoryConteract.ProductsEntry.CONTENT_URI,null,null);
                System.out.println("ppppppppppppppppppppppppppp" + rowID);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String [] Projection = {
                inventoryConteract.ProductsEntry.column_id,
                inventoryConteract.ProductsEntry.column_Product_Name,
                inventoryConteract.ProductsEntry.column_Product_Price,
                inventoryConteract.ProductsEntry.column_Product_Quantity,
                inventoryConteract.ProductsEntry.column_Product_Supplier,
                inventoryConteract.ProductsEntry.column_product_Available,
                inventoryConteract.ProductsEntry.column_product_Picture
        };
        return new CursorLoader(
                this,
                inventoryConteract.ProductsEntry.CONTENT_URI,
                Projection,
                null,
                null,
                null
        );
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        System.out.println("curser curser curser curser curser curser " + data.toString());
        CurserAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        CurserAdapter.swapCursor(null);
    }
}