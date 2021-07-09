package com.example.warehouseinventoryapp.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "warehouseItem")
public class WareHouseItem {
    public final static String TABLE_NAME = "warehouseItem";

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "itemId")
    private int itemId;

    @ColumnInfo(name = "itemName")
    private String itemName;

    @ColumnInfo(name = "quantity")
    private String quantity;

    @ColumnInfo(name = "cost")
    private String cost;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "frozen")
    private String frozen;

    public WareHouseItem(String itemName, String quantity, String cost, String description, String frozen) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.cost = cost;
        this.description = description;
        this.frozen = frozen;
    }

    public int getItemId() {
        return itemId;
    }
    public String getItemName() {
        return itemName;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public String getFrozen() {
        return frozen;
    }

    public void setItemId(@NonNull int id) {
        this.itemId = id;
    }
}


