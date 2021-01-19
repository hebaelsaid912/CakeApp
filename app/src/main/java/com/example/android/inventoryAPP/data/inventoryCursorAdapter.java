package com.example.android.inventoryAPP.data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.inventoryAPP.R;

public class inventoryCursorAdapter extends CursorAdapter {
    int q = 0;
    public inventoryCursorAdapter(Context context, Cursor c) {

        super(context, c, 0 );


    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
       // in overall
        TextView nameTextView = (TextView) view.findViewById(R.id.product_Name);
        TextView priceTextView = (TextView) view.findViewById(R.id.product_price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.product_quantity);
        LinearLayout sale = (LinearLayout) view.findViewById(R.id.SaleBTn);

        int idColumnIndex = cursor.getColumnIndex(inventoryConteract.ProductsEntry.column_id);
        int nameColumnIndex = cursor.getColumnIndex(inventoryConteract.ProductsEntry.column_Product_Name);
        int priceColumnIndex = cursor.getColumnIndex(inventoryConteract.ProductsEntry.column_Product_Price);
        int quantityColumnIndex = cursor.getColumnIndex(inventoryConteract.ProductsEntry.column_Product_Quantity);

        // Read the pet attributes from the Cursor for the current product
        int productid = cursor.getInt(idColumnIndex);
        String productName = cursor.getString(nameColumnIndex);
        String productPrice = cursor.getString(priceColumnIndex);
        String productquantity = cursor.getString(quantityColumnIndex);
        q = Integer.parseInt(productquantity);

        // Update the TextViews with the attributes for the current product
        nameTextView.setText(productName);
        priceTextView.setText(productPrice + " $");
        quantityTextView.setText(productquantity + " unit");
        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q--;
                quantityTextView.setText(q + " unit");
            }
        });


    }

}

