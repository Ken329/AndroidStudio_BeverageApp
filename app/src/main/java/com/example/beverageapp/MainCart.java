package com.example.beverageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainCart extends AppCompatActivity {
    TextView oItem, eItem, sItem, iItem, cockItem, coffItem, oPrice, ePrice, sPrice, iPrice, cockPrice, coffPrice,  total, minus1, minus2, minus3, minus4, minus5, minus6, plus1, plus2 ,plus3 ,plus4 ,plus5 ,plus6;
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

        minus1 = findViewById(R.id.tcCartItem1Minus);
        minus2 = findViewById(R.id.tcCartItem2Minus);
        minus3 = findViewById(R.id.tcCartItem3Minus);
        minus4 = findViewById(R.id.tcCartItem4Minus);
        minus5 = findViewById(R.id.tcCartItem5Minus);
        minus6 = findViewById(R.id.tcCartItem6Minus);
        plus1 = findViewById(R.id.tcCartItem1Plus);
        plus2 = findViewById(R.id.tcCartItem2Plus);
        plus3 = findViewById(R.id.tcCartItem3Plus);
        plus4 = findViewById(R.id.tcCartItem4Plus);
        plus5 = findViewById(R.id.tcCartItem5Plus);
        plus6 = findViewById(R.id.tcCartItem6Plus);


        myId = getIntent().getStringExtra("id");

        ref = FirebaseDatabase.getInstance().getReference("cart").child(myId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int orange = Integer.parseInt(snapshot.child("orange").child("amount").getValue().toString());
                int energy = Integer.parseInt(snapshot.child("energy").child("amount").getValue().toString());
                int soda = Integer.parseInt(snapshot.child("soda").child("amount").getValue().toString());
                int ice = Integer.parseInt(snapshot.child("ice_cream").child("amount").getValue().toString());
                int cock = Integer.parseInt(snapshot.child("cock_tail").child("amount").getValue().toString());
                int coffee = Integer.parseInt(snapshot.child("coffee").child("amount").getValue().toString());
                calculatePrice cal = new calculatePrice(orange, energy, soda, ice, cock, coffee);
                oItem.setText(String.valueOf(orange));
                eItem.setText(String.valueOf(energy));
                sItem.setText(String.valueOf(soda));
                iItem.setText(String.valueOf(ice));
                cockItem.setText(String.valueOf(cock));
                coffItem.setText(String.valueOf(coffee));
                oPrice.setText(cal.getOrange());
                ePrice.setText(cal.getEnergy());
                sPrice.setText(cal.getSoda());
                iPrice.setText(cal.getIce());
                cockPrice.setText(cal.getCock());
                coffPrice.setText(cal.getCoffee());
                total.setText(cal.getTotal());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageItem(myId, "orange", "-");
            }
        });
        plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageItem(myId, "orange", "+");
            }
        });
        minus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageItem(myId, "energy", "-");
            }
        });
        plus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageItem(myId, "energy", "+");
            }
        });
        minus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageItem(myId, "soda", "-");
            }
        });
        plus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageItem(myId, "soda", "+");
            }
        });
        minus4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageItem(myId, "ice_cream", "-");
            }
        });
        plus4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageItem(myId, "ice_cream", "+");
            }
        });
        minus5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageItem(myId, "cock-tail", "-");
            }
        });
        plus5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageItem(myId, "cock-tail", "+");
            }
        });
        minus6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageItem(myId, "coffee", "-");
            }
        });
        plus6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageItem(myId, "coffee", "+");
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
    public void manageItem(String id, String item, String action){
        Query query = FirebaseDatabase.getInstance().getReference("cart")
                .orderByChild("id")
                .equalTo(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int amount = 0;
                    if(action.equals("+")){
                        amount = Integer.parseInt(snapshot.child(id).child(item).child("amount").getValue().toString()) + 1;
                    }else{
                        amount = Integer.parseInt(snapshot.child(id).child(item).child("amount").getValue().toString()) - 1;
                        if(amount < 0){
                            Toast.makeText(MainCart.this, "Item should not be less than 0", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    ref = FirebaseDatabase.getInstance().getReference("cart").child(id);
                    ref.child(item).child("amount").setValue(String.valueOf(amount));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}