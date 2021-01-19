/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.inventoryAPP;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.android.inventoryAPP.data.DbBitmapUtility;
import com.example.android.inventoryAPP.data.ImageModel;
import com.example.android.inventoryAPP.data.inventoryConteract;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Allows user to create a new pet or edit an existing one.
 */
public class AddActivity extends AppCompatActivity {
    private static final int PRODUCT_Loader = 0;
    private static int RESULT_LOAD_IMAGE = 1;
    public static String IMAGE_PATH = "";
    private EditText mNameEditText;

    private EditText mPriceEditText;

    private EditText mquantityEditText;
    private EditText msupplierEditText;

    private Spinner mAvailableSpinner;
    private ImageView imageView;
    private  Button buttonLoadImage;

    private int mAvailable = 0;
    public static int id = 1;
    byte[] b ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle(getString(R.string.add_activity_title));

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_product_name);
        mPriceEditText = (EditText) findViewById(R.id.edit_product_price);
        mquantityEditText = (EditText) findViewById(R.id.edit_product_quantity);
        msupplierEditText = (EditText) findViewById(R.id.edit_product_suppliers);
        mAvailableSpinner = (Spinner) findViewById(R.id.spinner_Available);
        imageView = (ImageView) findViewById(R.id.imgView);
        setupSpinner();
         buttonLoadImage = (Button) findViewById(R.id.UploadBtn);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //Choose
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.setType("image/*");
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                //Recent
              /*  Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);*/
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            imageView.setImageURI(selectedImage);
            //  imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }


    /**
     * Setup the dropdown spinner that allows the user to select the gender of the product.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mAvailableSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mAvailableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.Available_Available))) {
                        mAvailable = inventoryConteract.ProductsEntry.product_Available;
                    } else if (selection.equals(getString(R.string.Available_notAvailable))) {
                        mAvailable = inventoryConteract.ProductsEntry.product_Not_Available;
                    } else {
                        mAvailable = inventoryConteract.ProductsEntry.product_unKnowm_Available;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mAvailable = 0; // Unknown
            }
        });
    }

    public ContentValues saveProductData() throws IOException {
       Bitmap img = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        b = DbBitmapUtility.getBytes(img);
        byte[] imageByte= b;
        String name = mNameEditText.getText().toString().trim();
        String price = mPriceEditText.getText().toString().trim();
        int pricex = Integer.parseInt(price);
        String quantity = mquantityEditText.getText().toString().trim();
        int quantityx = Integer.parseInt(quantity);
        String supplier = msupplierEditText.getText().toString().trim();
        System.out.println("name:"+name+" price: " + pricex + " quantity: " +quantityx + " supplier: " + supplier+" aaaaaaaa" + mAvailable);
        ContentValues values = new ContentValues();
        values.put(inventoryConteract.ProductsEntry.column_id,id);
        values.put(inventoryConteract.ProductsEntry.column_Product_Name, name);
        values.put(inventoryConteract.ProductsEntry.column_Product_Price, pricex);
        values.put(inventoryConteract.ProductsEntry.column_Product_Quantity, quantityx);
        values.put(inventoryConteract.ProductsEntry.column_Product_Supplier, supplier);
        values.put(inventoryConteract.ProductsEntry.column_product_Available, mAvailable);
        values.put(inventoryConteract.ProductsEntry.column_product_Picture,imageByte);
        System.out.println("Byte Array" + imageByte);
        return values;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                Uri newRowURI = null;

                try {
                    ContentValues res = saveProductData();
                    System.out.println("Save Button" + res );
                    newRowURI = getContentResolver().insert(inventoryConteract.ProductsEntry.CONTENT_URI, saveProductData());

                    id = (int) ContentUris.parseId(newRowURI);

                } catch (IOException e) {
                    Log.i("mm", "Error in Load Image to database" );
                    e.printStackTrace();
                }
                Log.i("mm", "uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu      " + newRowURI + "the id   " + id);
                if (newRowURI == null) {
                    // If the new content URI is null, then there was an error with insertion.
                    Toast.makeText(this, getString(R.string.editor_insert_product_failed),
                            Toast.LENGTH_SHORT).show();
                } else {
                    // Otherwise, the insertion was successful and we can display a toast.
                    Toast.makeText(this, getString(R.string.editor_insert_product_successful) + newRowURI,
                            Toast.LENGTH_SHORT).show();
                }
                finish();
                return true;

            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
