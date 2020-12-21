package com.example.beverageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainWallet extends AppCompatActivity {
    TextView name, balance, enter;
    ImageButton back;
    EditText topUp;
    String myId;
    DatabaseReference ref;
    double myBalance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wallet);
        getSupportActionBar().hide();

        name = findViewById(R.id.tvWalletName);
        balance = findViewById(R.id.tvWalletBalance);
        enter = findViewById(R.id.tvWalletEnter);
        back = findViewById(R.id.ibWalletBack);
        topUp = findViewById(R.id.etWalletTopup);

        myId = getIntent().getStringExtra("id");

        ref = FirebaseDatabase.getInstance().getReference("wallet").child(myId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String myName = snapshot.child("name").getValue().toString();
                    myBalance = Double.parseDouble(snapshot.child("balance").getValue().toString());

                    name.setText(myName);
                    balance.setText(String.valueOf(myBalance));
                }
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
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myTopUp = topUp.getText().toString();

                double newTopUp = Double.parseDouble(myTopUp);
                newTopUp += myBalance;
                myTopUp = String.format("%.2f", newTopUp);
                ref = FirebaseDatabase.getInstance().getReference("wallet").child(myId);
                ref.child("balance").setValue(myTopUp);
                topUp.setText("");
                Toast.makeText(MainWallet.this, "Successful top up wallet", Toast.LENGTH_LONG).show();
            }
        });
    }
}