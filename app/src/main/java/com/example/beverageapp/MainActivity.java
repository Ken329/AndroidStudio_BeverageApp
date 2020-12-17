package com.example.beverageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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