package com.example.android.inventoryAPP.data;

public class detailsList {
    private String Quantity;
    private int numQuantity;

    public detailsList(String quantity, int numQuantity) {
        Quantity = quantity;
        this.numQuantity = numQuantity;
    }

    public String getQuantity() {
        return Quantity;
    }

    public int getNumQuantity() {
        return numQuantity;
    }
}
