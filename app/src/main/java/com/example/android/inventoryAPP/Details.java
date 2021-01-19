package com.example.android.inventoryAPP;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.inventoryAPP.data.detailsCursorAdapter;
import com.example.android.inventoryAPP.data.inventoryConteract;

public class Details extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    Cursor cursor;
    private static final int PRODUCT_LOADER = 0 ;
    detailsCursorAdapter CursorAdapter;
    TextView DproductName;
    TextView DproductPrice;
    TextView DproductSupplier;
    TextView DproductAvailable;
    TextView numQuantity;
    ImageView productImage;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        productImage = (ImageView) findViewById(R.id.product_image);
        DproductName = (TextView) findViewById(R.id.Dproduct_Name);
        DproductPrice = (TextView) findViewById(R.id.Dproduct_price);
        DproductSupplier = (TextView) findViewById(R.id.Dproduct_suppliers);
        DproductAvailable = (TextView) findViewById(R.id.Dedit_product_Available);
        numQuantity = (TextView) findViewById(R.id.numQuantity);
        ImageButton increase = (ImageButton) findViewById(R.id.increase);
        ImageButton decrease = (ImageButton) findViewById(R.id.decrease);
        increase.setOnClickListener(v -> {
            String increase1 = numQuantity.getText().toString().trim();
            int res = Integer.parseInt(increase1);
            int finalres = res+1;
            System.out.println("final result is : " + finalres);
            numQuantity.setText(String.valueOf(finalres));
        });

        decrease.setOnClickListener(v -> {
            String decrease1 = numQuantity.getText().toString().trim();
            int res = Integer.parseInt(decrease1);
            if(res>0){
                int finalres = res-1;
                numQuantity.setText(String.valueOf(finalres));
            }else{
                numQuantity.setText("0");
            }

        });

        Button order = (Button) findViewById(R.id.order);
        order.setOnClickListener(v -> {
            String decrease12 = numQuantity.getText().toString().trim();
            int res = Integer.parseInt(decrease12);
            if(res>0){
                int finalres = res-1;
                numQuantity.setText(String.valueOf(finalres));
            }else{
                numQuantity.setText("0");
            }
        });

        Button deleteRow = (Button) findViewById(R.id.deleteRow);
        deleteRow.setOnClickListener(v -> {
            int c = getContentResolver().delete(MainActivity.shareID,null,null);
        });
        CursorAdapter = new detailsCursorAdapter(this, null);
        getLoaderManager().initLoader(PRODUCT_LOADER,null, this );

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        this.id = id;
        Log.e("main","the loader id is " + id);
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
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToPosition(MainActivity.pos)) {
            Log.e("main","the MainActivity pos is " + MainActivity.pos);
            // Find the columns of products attributes that we're interested in
            int idColumnIndex = cursor.getColumnIndex(inventoryConteract.ProductsEntry.column_id);
            int nameColumnIndex = cursor.getColumnIndex(inventoryConteract.ProductsEntry.column_Product_Name);
            int priceColumnIndex = cursor.getColumnIndex(inventoryConteract.ProductsEntry.column_Product_Price);
            int quantityColumnIndex = cursor.getColumnIndex(inventoryConteract.ProductsEntry.column_Product_Quantity);
            int availableColumnIndex = cursor.getColumnIndex(inventoryConteract.ProductsEntry.column_product_Available);
            int supplierColumnIndex = cursor.getColumnIndex(inventoryConteract.ProductsEntry.column_Product_Supplier);
            int imageColumnIndex = cursor.getColumnIndex(inventoryConteract.ProductsEntry.column_product_Picture);
            Log.e("main","the id Column Index is " + idColumnIndex);
            Log.e("main","the name Column Index is " + nameColumnIndex);
            Log.e("main","the price Column Index is " + priceColumnIndex);
            Log.e("main","the quantity Column Index is " + quantityColumnIndex);
            Log.e("main","the supplier Column Index is " + supplierColumnIndex);
            Log.e("main","the available Column Index is " + availableColumnIndex);
            Log.e("main","the image Column Index is " + imageColumnIndex);

            // Extract out the value from the Cursor for the given column index
            String id = cursor.getString(idColumnIndex);
            String name = cursor.getString(nameColumnIndex);
            String price = cursor.getString(priceColumnIndex);
            int quantity = cursor.getInt(quantityColumnIndex);
            String supplier = cursor.getString(supplierColumnIndex);
            Log.e("main","the id is " + id);
            Log.e("main","the name is " + name);
            Log.e("main","the price is " + price);
            Log.e("main","the quantity is " + quantity);
            Log.e("main","the supplier is " + supplier);
            byte[] image = cursor.getBlob(imageColumnIndex);
            Bitmap bmp= BitmapFactory.decodeByteArray(image, 0 , image.length);
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            productImage.setMinimumHeight(dm.heightPixels);
            productImage.setMinimumWidth(dm.widthPixels);
            productImage.setImageBitmap(bmp);
            int available = cursor.getInt(availableColumnIndex);

            if(available == inventoryConteract.ProductsEntry.product_Available){
                DproductAvailable.setText("Available");
            }else if(available == inventoryConteract.ProductsEntry.product_Not_Available){
                DproductAvailable.setText("Not Available");
            }else {
                DproductAvailable.setText("Unknown");
            }
            Log.e("main","the supplier is " + supplier);
            Log.e("main","the available is " + available);
            // Update the views on the screen with the values from the database
            DproductName.setText(name);
            DproductPrice.setText(price);
            numQuantity.setText(Integer.toString(quantity));
            DproductSupplier.setText(supplier);


        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        CursorAdapter.swapCursor(null);

    }
}