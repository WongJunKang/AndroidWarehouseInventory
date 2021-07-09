package com.example.warehouseinventoryapp.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WareHouseItemRepository {
    private WareHouseItemDao mWareHouseItemDao;
    private LiveData<List<WareHouseItem>> mAllWareHouseItem;

    WareHouseItemRepository(Application application) {
        WareHouseItemDatabase db = WareHouseItemDatabase.getDatabase(application);
        mWareHouseItemDao = db.WareHouseItemDao();
        mAllWareHouseItem = mWareHouseItemDao.getAllWareHouseItem();
    }
    LiveData<List<WareHouseItem>> getAllWareHouseItem() {
        return mAllWareHouseItem;
    }
    void insert(WareHouseItem wareHouseItem) {
        WareHouseItemDatabase.databaseWriteExecutor.execute(() -> mWareHouseItemDao.addWareHouseItem(wareHouseItem));
    }
    void deleteAll(){
        WareHouseItemDatabase.databaseWriteExecutor.execute(()->{
            mWareHouseItemDao.deleteAllWareHouseItem();
        });
    }

}
