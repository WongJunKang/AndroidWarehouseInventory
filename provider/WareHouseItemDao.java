package com.example.warehouseinventoryapp.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WareHouseItemDao {
    @Query("select * from warehouseItem")
    LiveData<List<WareHouseItem>> getAllWareHouseItem();

    @Insert
    void addWareHouseItem(WareHouseItem wareHouseItem);

    @Query("delete from warehouseItem where itemName= :name")
    void deleteWareHouseItem(String name);

    @Query("delete FROM warehouseItem")
    void deleteAllWareHouseItem();
}
