package com.example.feedmeappjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.feedmeappjava.Model.UserAddress;
import com.example.feedmeappjava.Model.UserProfile;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserAddAddressActivity extends AppCompatActivity {

    EditText labelHome, labelAddress;
    Button btnAddressSave;
    UserAddress userAddress;
    DatabaseReference reff;

    String address, label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_address);

        Toolbar toolbarProfile = findViewById(R.id.toolbarUserAddAddress);
        setSupportActionBar(toolbarProfile);
        getSupportActionBar().setTitle("Add a new Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        labelAddress = (EditText)findViewById(R.id.labelAddress);
        labelHome = (EditText)findViewById(R.id.labelHome);

        btnAddressSave = (Button)findViewById(R.id.btnSaveAddress);

        btnAddressSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userAddress.setAddress(labelAddress.getText().toString().trim());
                userAddress.setLabel(labelHome.getText().toString().trim());
                reff.child("UserAddress").setValue(userAddress);
                Toast.makeText(UserAddAddressActivity.this,"Address Added Successfuly",Toast.LENGTH_LONG).show();
            }
        });

        toolbarProfile.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    /*private Boolean validate(){
        Boolean result = false;

        label = labelHome.getText().toString();
        address = labelAddress.getText().toString();

        if(label.isEmpty() || address.isEmpty()){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
            takeInput();
        }else{
            result = true;
        }

        return result;
    }

    private void takeInput() {
        String label = labelHome.getText().toString();
        String address = labelAddress.getText().toString();
    }

    private void sendUserData(){

        String user_label = labelHome.getText().toString().trim();
        String user_address = labelAddress.getText().toString().trim();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("UserAddress");
        UserAddress userProfile = new UserAddress(label, address);


        myRef.setValue(userProfile);
    }
     */

}