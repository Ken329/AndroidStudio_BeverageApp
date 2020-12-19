package com.example.beverageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainCart extends AppCompatActivity {
    TextView oItem, eItem, sItem, iItem, cockItem, coffItem, oPrice, ePrice, sPrice, iPrice, cockPrice, coffPrice,  total;
    ImageButton back;
    DatabaseReference ref;
    String myId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cart);
        getSupportActionBar().hide();

        back = findViewById(R.id.ibCartBack);
        oItem = findViewById(R.id.tvCartItem1);
        eItem = findViewById(R.id.tvCartItem2);
        sItem = findViewById(R.id.tvCartItem3);
        iItem = findViewById(R.id.tvCartItem4);
        cockItem = findViewById(R.id.tvCartItem5);
        coffItem = findViewById(R.id.tvCartItem6);
        oPrice = findViewById(R.id.tvCartPrice1);
        ePrice = findViewById(R.id.tvCartPrice2);
        sPrice = findViewById(R.id.tvCartPrice3);
        iPrice = findViewById(R.id.tvCartPrice4);
        cockPrice = findViewById(R.id.tvCartPrice5);
        coffPrice = findViewById(R.id.tvCartPrice6);
        total = findViewById(R.id.tvCartPriceTotal);

        myId = getIntent().getStringExtra("id");

        ref = FirebaseDatabase.getInstance().getReference("cart").child(myId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String orange = snapshot.child("orange").child("amount").getValue().toString();
                String energy = snapshot.child("energy").child("amount").getValue().toString();
                String soda = snapshot.child("soda").child("amount").getValue().toString();
                String ice = snapshot.child("ice_cream").child("amount").getValue().toString();
                String cock = snapshot.child("cock_tail").child("amount").getValue().toString();
                String coffee = snapshot.child("coffee").child("amount").getValue().toString();
                oItem.setText(orange);
                eItem.setText(energy);
                sItem.setText(soda);
                iItem.setText(ice);
                cockItem.setText(cock);
                coffItem.setText(coffee);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("id", myId);
                startActivity(intent);
            }
        });
    }
}