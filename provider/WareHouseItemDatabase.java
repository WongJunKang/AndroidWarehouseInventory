package com.example.warehouseinventoryapp.provider;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities =  {WareHouseItem.class}, version = 1)
public abstract class WareHouseItemDatabase extends RoomDatabase {
    public static final String WAREHOUSE_ITEM_DATABASE_NAME = "warehouse_item_database";
    public abstract WareHouseItemDao WareHouseItemDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile WareHouseItemDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static WareHouseItemDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WareHouseItemDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WareHouseItemDatabase.class, WAREHOUSE_ITEM_DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
