package com.example.warehouseinventoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class LoginPage extends AppCompatActivity {
    private HashMap<String, String> usernameAndPassword = new HashMap<>();
    String username;
    String password;
    Context myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        myContext = this;

        // Assign password
        usernameAndPassword.put("Aaron", "123");
        usernameAndPassword.put("Ben", "456");
        usernameAndPassword.put("Catherine", "789");


        EditText editName = (EditText) findViewById(R.id.username);
        EditText editPassword = (EditText) findViewById(R.id.password);

        // start MainActivity when login button is pressed.
        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get username and password
                username = editName.getText().toString();
                password = editPassword.getText().toString();
                // validate username and password.
                // if username is a valid username.
                if(usernameAndPassword.containsKey(username)){
                    // get matching password to compare with input password.
                    if(usernameAndPassword.get(username).equals(password)){
                        // start MainActivity if both username and password is correct.
                        Intent startMainActivity = new Intent(myContext, MainActivity.class);
                        startActivity(startMainActivity);
                    }else{
                        Toast.makeText(myContext, "Invalid password for the username", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(myContext, "Invalid username", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
