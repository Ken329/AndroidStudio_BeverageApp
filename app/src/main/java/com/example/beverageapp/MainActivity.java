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
    int amount;
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
                    showError();
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
                myUser = user.getText().toString();
                if(myUser.equals("User")){
                    showError();
                }else{
                    Intent intent = new Intent(v.getContext(), MainCart.class);
                    intent.putExtra("id", myId);
                    startActivity(intent);
                }
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
                myUser = user.getText().toString();
                if(myUser.equals("User")){
                    showError();
                }else{
                    getItem(myId, "orange");
                    showOrder("Orange Juice");
                }
            }
        });
        energy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myUser = user.getText().toString();
                if(myUser.equals("User")){
                    showError();
                }else{
                    getItem(myId, "energy");
                    showOrder("Energy Drink");
                }
            }
        });
        soda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myUser = user.getText().toString();
                if(myUser.equals("User")){
                    showError();
                }else{
                    getItem(myId, "soda");
                    showOrder("Soda Water");
                }
            }
        });
        iceCream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myUser = user.getText().toString();
                if(myUser.equals("User")){
                    showError();
                }else{
                    getItem(myId, "ice_cream");
                    showOrder("Ice Cream");
                }
            }
        });
        cockTail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myUser = user.getText().toString();
                if(myUser.equals("User")){
                    showError();
                }else{
                    getItem(myId, "cock_tail");
                    showOrder("Cocktail");
                }
            }
        });
        coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myUser = user.getText().toString();
                if(myUser.equals("User")){
                    showError();
                }else{
                    getItem(myId, "coffee");
                    showOrder("Coffee");
                }
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
    public void showError(){
        Toast.makeText(MainActivity.this, "Login first", Toast.LENGTH_SHORT).show();
    }
    public void showOrder(String drink){
        Toast.makeText(MainActivity.this, drink + " added to cart", Toast.LENGTH_LONG).show();
    }
    public void getItem(String id, String item){
        Query query1 = FirebaseDatabase.getInstance().getReference("cart")
                .orderByChild("id")
                .equalTo(id);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    amount = Integer.parseInt(snapshot.child(id).child(item).child("amount").getValue().toString()) + 1;
                    ref = FirebaseDatabase.getInstance().getReference("cart").child(id);
                    ref.child(item).child("amount").setValue(String.valueOf(amount));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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