package com.example.android.inventoryAPP.data;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.inventoryAPP.R;

public class detailsCursorAdapter extends CursorAdapter {

    public detailsCursorAdapter(Context context, Cursor c) {

        super(context, c, 0 );
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.activity_details, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //in details
        ImageView imageView = (ImageView) view.findViewById(R.id.product_image);
        TextView  numQuantity = (TextView) view.findViewById(R.id.numQuantity);
        TextView dnameTextView = (TextView) view.findViewById(R.id.Dproduct_Name);
        TextView dpriceTextView = (TextView) view.findViewById(R.id.Dproduct_price);
        TextView dsuppliersTextView = (TextView) view.findViewById(R.id.Dproduct_suppliers);
        TextView dAvailavleTextView = (TextView) view.findViewById(R.id.Dedit_product_Available);
        // ImageView pictureImageView = (ImageView) view.findViewById(R.id.Product_picture);
        // Find the columns of pet attributes that we're interested in
        int idColumnIndex = cursor.getColumnIndex(inventoryConteract.ProductsEntry.column_id);
        int nameColumnIndex = cursor.getColumnIndex(inventoryConteract.ProductsEntry.column_Product_Name);
        int priceColumnIndex = cursor.getColumnIndex(inventoryConteract.ProductsEntry.column_Product_Price);
        int quantityColumnIndex = cursor.getColumnIndex(inventoryConteract.ProductsEntry.column_Product_Quantity);
        int supplierColumnIndex = cursor.getColumnIndex(inventoryConteract.ProductsEntry.column_Product_Supplier);
        int availableColumnIndex = cursor.getColumnIndex(inventoryConteract.ProductsEntry.column_product_Available);
        int imageColumnIndex = cursor.getColumnIndex(inventoryConteract.ProductsEntry.column_product_Picture);
        // Extract out the value from the Cursor for the given column index
        String id = cursor.getString(idColumnIndex);
        String name = cursor.getString(nameColumnIndex);
        String price = cursor.getString(priceColumnIndex);
        int quantity = cursor.getInt(quantityColumnIndex);
        String supplier = cursor.getString(supplierColumnIndex);
        byte[] image = cursor.getBlob(imageColumnIndex);
        Bitmap bmp= BitmapFactory.decodeByteArray(image, 0 , image.length);
        imageView.setImageBitmap(bmp);
        int Dproductavailable = cursor.getInt(availableColumnIndex);

        if(Dproductavailable == inventoryConteract.ProductsEntry.product_Available){
            dAvailavleTextView.setText("Available");
        }else if(Dproductavailable == inventoryConteract.ProductsEntry.product_Not_Available){
            dAvailavleTextView.setText("Not Available");

        }else {
            dAvailavleTextView.setText("Unknown");
        }

        // Update the views on the screen with the values from the database
        Log.e("main","the id is " + id);
        dnameTextView.setText(name);
        Log.e("main","the name is " + name);
        dpriceTextView.setText(price);
        Log.e("main","the price is " + price);
        numQuantity.setText(Integer.toString(quantity));
        Log.e("main","the quantity is " + quantity);
        dsuppliersTextView.setText(supplier);
        Log.e("main","the supplier is " + supplier);
        Log.e("main","the available is " + Dproductavailable);

    }

}

