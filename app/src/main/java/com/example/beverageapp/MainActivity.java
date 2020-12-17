package com.example.beverageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView user;
    LinearLayout logInOut, cart, wallet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        user = findViewById(R.id.tvUser);
        logInOut = findViewById(R.id.linearLogInOut);
        cart = findViewById(R.id.linearCart);
        wallet = findViewById(R.id.linearWallet);

    }
}