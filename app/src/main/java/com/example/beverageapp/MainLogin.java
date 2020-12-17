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

public class MainLogin extends AppCompatActivity {
    ImageButton back;
    TextView id, name, enter;
    EditText password;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        getSupportActionBar().hide();

        back = findViewById(R.id.ibLoginback);
        id = findViewById(R.id.tvLoginId);
        name = findViewById(R.id.tvLoginName);
        enter = findViewById(R.id.tvLoginEnter);
        password = findViewById(R.id.etLoginPassword);

        String myId = getIntent().getStringExtra("id");
        id.setText("ID: " + myId);

        ref = FirebaseDatabase.getInstance().getReference("user").child(myId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String myName = snapshot.child("nickname").getValue().toString();
                name.setText("Name: " + myName);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myPassword = password.getText().toString();
                if(myPassword.equals("")){
                    Toast.makeText(MainLogin.this, "Do not leave any field empty", Toast.LENGTH_SHORT).show();
                }else{
                    ref = FirebaseDatabase.getInstance().getReference("user").child(myId);
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String dataPass = snapshot.child("password").getValue().toString();
                            if(dataPass.equals(myPassword)){
                                Toast.makeText(MainLogin.this, "Successful Login", Toast.LENGTH_SHORT).show();
                                goMain(myId, v);
                            }else{
                                Toast.makeText(MainLogin.this, "Wrong password, please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
    public void goMain(String id, View v){
        Intent intent = new Intent(v.getContext(), MainActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}