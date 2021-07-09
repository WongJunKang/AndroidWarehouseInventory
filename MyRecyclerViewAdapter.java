package com.example.warehouseinventoryapp;

import android.os.TestLooperManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouseinventoryapp.provider.WareHouseItem;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    List<WareHouseItem>data = new ArrayList<>();

    public MyRecyclerViewAdapter(){
    }

    public void setData(List<WareHouseItem> newData) {
        this.data = newData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false); //CardView inflated as RecyclerView list item
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.itemName.setText(data.get(position).getItemName());
        holder.quantity.setText(data.get(position).getQuantity());
        holder.cost.setText(data.get(position).getCost());
        holder.description.setText(data.get(position).getDescription());
        holder.freeze.setText(data.get(position).getFrozen());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView itemName, cost, quantity, description, freeze;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

            itemName = itemView.findViewById(R.id.setName);
            cost = itemView.findViewById(R.id.setCost);
            quantity = itemView.findViewById(R.id.setQuantity);
            description = itemView.findViewById(R.id.setDescription);
            freeze = itemView.findViewById(R.id.setFreeze);
        }
    }
}
