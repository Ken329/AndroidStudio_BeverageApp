package com.example.beverageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements UserDialog.ExampleDialogListener{
    TextView user;
    LinearLayout logInOut, cart, wallet;
    ImageView orange, energy, soda, iceCream, cockTail, coffee;

    DatabaseReference ref;
    String myName, myId, myUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        user = findViewById(R.id.tvUser);
        logInOut = findViewById(R.id.linearLogInOut);
        cart = findViewById(R.id.linearCart);
        wallet = findViewById(R.id.linearWallet);
        orange = findViewById(R.id.ivOrange);
        energy = findViewById(R.id.ivEnergy);
        soda = findViewById(R.id.ivSoda);
        iceCream = findViewById(R.id.ivIceCream);
        cockTail = findViewById(R.id.ivCockTail);
        coffee = findViewById(R.id.ivCoffee);

        myId = getIntent().getStringExtra("id");
        Query query = FirebaseDatabase.getInstance().getReference("user")
                .orderByChild("id")
                .equalTo(myId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    myName = snapshot.child(myId).child("nickname").getValue().toString();
                    user.setText(myName);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myUser = user.getText().toString();
                if(myUser.equals("User")){
                    Toast.makeText(MainActivity.this, "Login first", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(v.getContext(), MainUserDetail.class);
                    intent.putExtra("id", myId);
                    startActivity(intent);
                }
            }
        });
        logInOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myUser = user.getText().toString();
                if(myUser.equals("User")){
                    goDialog();
                }else{
                    user.setText("");
                    myName = "";
                    myId = "";
                    user.setText("User");
                    Toast.makeText(MainActivity.this, "Successful Log Out", Toast.LENGTH_SHORT).show();
                }

            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Successful added Orange Juice to cart", Toast.LENGTH_LONG).show();
            }
        });
        energy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Successful added Energy Drink to cart", Toast.LENGTH_LONG).show();
            }
        });
        soda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Successful added Soda Water to cart", Toast.LENGTH_LONG).show();
            }
        });
        iceCream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Successful added Ice Cream to cart", Toast.LENGTH_LONG).show();
            }
        });
        cockTail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Successful added Cocktail to cart", Toast.LENGTH_LONG).show();
            }
        });
        coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Successful added Coffee to cart", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void goDialog(){
        UserDialog dialog = new UserDialog();
        dialog.show(getSupportFragmentManager(), "Example Dialog");
    }
    public void goSignUp(String id){
        Intent intent = new Intent(getApplicationContext(), MainSignUp.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
    public void goLogin(String id){
        Intent intent = new Intent(getApplicationContext(), MainLogin.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void applyTexts(String id) {
        Query query = FirebaseDatabase.getInstance().getReference("user")
                .orderByChild("id")
                .equalTo(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    goLogin(id);
                }else{
                    goSignUp(id);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}