package com.example.warehouseinventoryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

// Import Libraries
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;
// instead of importing import android.widget.Toolbar, import this,
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;


import com.example.warehouseinventoryapp.provider.WareHouseItem;
import com.example.warehouseinventoryapp.provider.WareHouseItemViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class MainActivity extends AppCompatActivity {

    // text view
    private EditText itemName;
    private EditText quantity;
    private EditText cost;
    private EditText description;
    private ToggleButton toggle;
    // drawer layout and navigation view and tool bar
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    Toolbar toolbar;
    Context myContext;

    // week 10
    private int x_down;
    private View constraintLayout;

    // week 7
    private WareHouseItemViewModel mItemViewModel;
    MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_drawer);

        mItemViewModel = new ViewModelProvider(this).get(WareHouseItemViewModel.class);
        mItemViewModel.getAllWareHouseItem().observe(this, newData -> {
            adapter.setData(newData);
            adapter.notifyDataSetChanged();
        });

        // references to text views
        itemName = findViewById(R.id.inputItemName);
        quantity = findViewById(R.id.inputQuantity);
        cost = findViewById(R.id.inputCost);
        description = findViewById(R.id.inputDescription);
        toggle = findViewById(R.id.yesNoButton);
        myContext = this;

        /* Request permissions to access SMS */
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);

        /* Create and instantiate local broadcast receiver.
           This class listens to messages come from class SMSReceiver
         */
        MyBroadcastReceiver newBroadcastReceiver = new MyBroadcastReceiver();
        registerReceiver(newBroadcastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER));

        // week 5 (Hook the drawer and the toolbar)
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        // set up action bar drawer trigger action
        ActionBarDrawerToggle actionBarToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        // add actionBarToggle as drawerLayout's listener.
        drawerLayout.addDrawerListener(actionBarToggle);
        actionBarToggle.syncState();

        // create a new object of MyNavigationListener() and set it as the Listener of navigationView.
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());


        // FAB Button
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);
                Snackbar snackbar = Snackbar.make(view, "A new item is added!", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });

        constraintLayout = findViewById(R.id.activity_main_id);
        constraintLayout.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN) :
                        // get x coordinate when touch
                        System.out.println("hello");
                        x_down = (int)event.getX();
                        return true;
                    case (MotionEvent.ACTION_MOVE) :
                        return true;
                    case (MotionEvent.ACTION_UP) :
                        // do only if the movement is greater than 40 pixels
                        int x_up = (int)event.getX();
                        if(abs(x_down - x_up) > 40){
                            // swipe from right to left
                            if((x_down -  x_up) > 0){
                                clearAllMessage(v);
                            }else{// swipe from left to right
                                addItem(v);
                            }
                        }
                        return true;
                    default :
                        return false;
                }
            }
        });

        Log.i("MY_LIFECYCLE", "onCreate");
        }


    // Life Cycle
    @Override
    protected void onStart() {
        super.onStart();
        restoreSharedPreferences();
        Log.i("MY_LIFECYCLE", "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MY_LIFECYCLE", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MY_LIFECYCLE", "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MY_LIFECYCLE", "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MY_LIFECYCLE", "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("MY_LIFECYCLE", "onRestart");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("MY_LIFECYCLE", "onSaveInstanceState");

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("MY_LIFECYCLE", "onRestoreInstanceState");
    }

    // This method, clear all amd reset the status of the activity when the button is pressed.
    public void clearAllMessage(View view){
        // Set item in editText to be empty strings
        itemName.setText(null);
        quantity.setText(null);
        cost.setText(null);
        description.setText(null);

        // clear persistent data
        SharedPreferences myData = getSharedPreferences("file1", 0);
        SharedPreferences.Editor myEditor = myData.edit();
        myEditor.clear();
        myEditor.commit();

        // turn toggle button off
        toggle.setChecked(false);;
        mItemViewModel.deleteAll();
    }

    // This method helps display a default default toast, when the button is pressed.
    public void addItem(View view){
        Editable item = itemName.getText();

        // saved item
        savedSharedPreferences();

        // Display message
        String text = "New item (" + item + ") has been added.";
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    private void savedSharedPreferences(){
        // get item in each edit Text object
        String itemNameText = itemName.getText().toString();
        String quantityText = quantity.getText().toString();
        String costText = cost.getText().toString();
        String descriptionText = description.getText().toString();


        // get state of toggle button.
        String toggleString = "false";
        Boolean toggleState = false;
        if (toggle.isChecked()){
            toggleState = true;
            toggleString = "true";
        }
        // save data into ArrayList
        WareHouseItem newItem = new WareHouseItem(itemNameText, quantityText, costText, descriptionText, toggleString);
        mItemViewModel.insert(newItem);

        // create a private SharedPreferences object name file1
        SharedPreferences myData = getSharedPreferences("file1", 0);

        // Edit file 1
        SharedPreferences.Editor myEditor = myData.edit();

        // put string with key value pairs into file1
        myEditor.putString("key1", itemNameText);
        myEditor.putString("key2", quantityText);
        myEditor.putString("key3", costText);
        myEditor.putString("key4", descriptionText);
        // put boolean state with key value pair into file1
        myEditor.putBoolean("key5", toggleState);
        // commit
       myEditor.commit();
    }

    private void restoreSharedPreferences(){
        SharedPreferences myData = getSharedPreferences("file1", 0);

        // get respective string in file1 with matching key value
        String itemNameText  = myData.getString("key1", "");
        String quantityText = myData.getString("key2", "0");
        String costText = myData.getString("key3", "0.00");
        String descriptionText = myData.getString("key4", "");

        //  set String back into respective editText.
        itemName.setText(itemNameText);
        quantity.setText(quantityText);
        cost.setText(costText);
        description.setText(descriptionText);

        // Toggle Button
        // retrieve state of toggle button from myData, key5
        Boolean toggleState = myData.getBoolean("key5", false);
        // setCheck state back
        toggle.setChecked(toggleState);

    }

    class MyBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            // Retrieve the message from the intent
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);

            // limit is set to negative number because we want to be able to have empty String in textView
            String[] splitString = msg.split(";", -1);


            itemName.setText(splitString[0]);
            quantity.setText(splitString[1]);
            cost.setText(splitString[2]);
            description.setText(splitString[3]);
            toggle.setChecked(Boolean.parseBoolean(splitString[4]));

            Log.i("MY_LIFECYCLE", "onReceive");

        }
    }

    // implement the navigation items listener
    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // get the id of the Add Item Bar of drawer
            int id = item .getItemId();

            if(id == R.id.add_item_drawer){
                addItem(findViewById(id));
            }else if(id == R.id.clear_fields_drawer){
                clearAllMessage(findViewById(id));
            }else if(id == R.id.list_all_item_drawer){
                Intent startListAllItemActivity = new Intent(myContext, ListAllItems.class);
                startActivity(startListAllItemActivity);
            }else if(id == R.id.log_out_drawer){
                finish();
            }
            // close the drawer
            drawerLayout.closeDrawers();
            return true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // inflates a menu hierarchy from XML resource.
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        // get the id of the Add Item Bar of drawer
        int id = item .getItemId();

        if(id == R.id.add_item_options_menu){
            addItem(findViewById(id));
        }else if(id == R.id.clear_fields_options_menu){
            clearAllMessage(findViewById(id));
        }
        return true;
    }

}
