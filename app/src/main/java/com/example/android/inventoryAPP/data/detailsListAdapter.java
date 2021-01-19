package com.example.android.inventoryAPP.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.android.inventoryAPP.R;

import java.util.ArrayList;

public class detailsListAdapter extends BaseAdapter {
    private ArrayList<detailsList> list = new ArrayList<detailsList>();
    private View context;
    int qq = 0;
    int pos =0;

    public detailsListAdapter( View context , int pos) {
        this.pos = pos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        //Handle TextView and display string from your list
        TextView quantity= (TextView)view.findViewById(R.id.product_quantity);
        String q = quantity.getText().toString().trim();
         qq = Integer.parseInt(q);
        LinearLayout sale = (LinearLayout) view.findViewById(R.id.SaleBTn);
        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qq--;
                quantity.setText(qq +" unit" );
            }
        });

        return view;
    }
}