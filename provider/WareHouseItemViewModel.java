package com.example.warehouseinventoryapp.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


public class WareHouseItemViewModel extends AndroidViewModel {
    private WareHouseItemRepository mRepository;
    private LiveData<List<WareHouseItem>> mAllWareHouseItem;

    public WareHouseItemViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WareHouseItemRepository(application);
        mAllWareHouseItem = mRepository.getAllWareHouseItem();
    }
    public LiveData<List<WareHouseItem>> getAllWareHouseItem() {
        return mAllWareHouseItem;
    }
    public void insert(WareHouseItem wareHouseItem) {
        mRepository.insert(wareHouseItem);
    }
    public void deleteAll(){
        mRepository.deleteAll();
    }

}
