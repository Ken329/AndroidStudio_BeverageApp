package com.example.beverageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainUserDetail extends AppCompatActivity {
    ImageButton back;
    TextInputEditText id, password, nickname, phone, address;
    TextView update;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_detail);
        getSupportActionBar().hide();

        back = findViewById(R.id.ibUserDetailBack);
        id = findViewById(R.id.textInputUserDetailId);
        password = findViewById(R.id.textInputUserDetailPassword);
        nickname = findViewById(R.id.textInputUserDetailNickname);
        phone = findViewById(R.id.textInputUserDetailPhone);
        address = findViewById(R.id.textInputUserDetailAddress);
        update = findViewById(R.id.tvUserDetailUpdate);

        String myId = getIntent().getStringExtra("id");
        id.setEnabled(false);
        ref = FirebaseDatabase.getInstance().getReference("user").child(myId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String myPass = snapshot.child("password").getValue().toString();
                String myName = snapshot.child("nickname").getValue().toString();
                String myPhone = snapshot.child("phone").getValue().toString();
                String myAddress = snapshot.child("address").getValue().toString();
                id.setText(myId);
                password.setText(myPass);
                nickname.setText(myName);
                phone.setText(myPhone);
                address.setText(myAddress);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("id",  myId);
                startActivity(intent);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myPass = password.getText().toString();
                String myName = nickname.getText().toString();
                String myPhone = phone.getText().toString();
                String myAddress = address.getText().toString();
                UserDetail user = new UserDetail(myId, myPass, myPhone, myAddress, myName);
                ref = FirebaseDatabase.getInstance().getReference("user");
                ref.child(myId).setValue(user);
                Toast.makeText(MainUserDetail.this, "Successful update", Toast.LENGTH_SHORT).show();
            }
        });
    }
}