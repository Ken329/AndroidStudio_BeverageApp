package com.example.beverageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainSignUp extends AppCompatActivity {
    ImageButton back;
    TextView id, register;
    EditText password, phone, address, name;
    DatabaseReference ref;
    String myId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sign_up);
        getSupportActionBar().hide();

        back = findViewById(R.id.ibRegisterBack);
        id = findViewById(R.id.tvRegisterId);
        register = findViewById(R.id.tvRegisterSubmit);
        password = findViewById(R.id.etRegisterPassword);
        phone = findViewById(R.id.etRegisterPhone);
        address = findViewById(R.id.etRegisterAddress);
        name = findViewById(R.id.etRegisterName);

        myId = getIntent().getStringExtra("id");
        id.setText(myId);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myPassword = password.getText().toString();
                String myName = name.getText().toString();
                String myPhone = phone.getText().toString();
                String myAddress = address.getText().toString();

                if(myPassword.equals("") || myPhone.equals("") || myAddress.equals("") || myName.equals("")){
                    Toast.makeText(MainSignUp.this, "Do not leave any field empty", Toast.LENGTH_LONG).show();
                }else{
                    UserDetail myUser = new UserDetail(myId, myPassword, myPhone, myAddress, myName);
                    ref = FirebaseDatabase.getInstance().getReference("user");
                    ref.child(myId).setValue(myUser);
                    Toast.makeText(MainSignUp.this, "Successful created", Toast.LENGTH_LONG).show();
                    goMain(myId);
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void goMain(String id){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}