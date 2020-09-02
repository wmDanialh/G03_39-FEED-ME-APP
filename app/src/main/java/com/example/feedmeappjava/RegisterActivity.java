package com.example.feedmeappjava;
// 1. We gonna set the UID as our phone number
// 2.the register will be our email and password authentication


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedmeappjava.Model.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private EditText userName, userPassword, userEmail, userMobile;
    private FloatingActionButton regButton;
    private TextView userLogin;

    FirebaseAuth firebaseAuth;

    String email, name, password, mobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regButton = (FloatingActionButton)findViewById(R.id.btnSignUp);
        userLogin = (TextView) findViewById(R.id.tvUserLogin);
        userPassword = (EditText)findViewById(R.id.etUserPassword);
        userEmail = (EditText)findViewById(R.id.etUserEmail);
        userName = (EditText)findViewById(R.id.etUserName);
        userMobile = (EditText)findViewById(R.id.etUserMobile);


        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //final DatabaseReference table_user = database.getReference("Users");
        //firebaseAuth = table_user.getDatabase().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });


        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference table_user = database.getReference("User");

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        final ProgressDialog mDialog = new ProgressDialog(RegisterActivity.this);
                        mDialog.setMessage("Please Wait...");
                        mDialog.show();

                        //check if user not exist in database
                        if (dataSnapshot.child(userMobile.getText().toString()).exists()) {
                            //get user information
                            mDialog.dismiss();
                            UserProfile user = dataSnapshot.child(userMobile.getText().toString()).getValue(UserProfile.class);
                            Toast.makeText(RegisterActivity.this, "Phone number already registered !", Toast.LENGTH_SHORT).show();

                        } else {
                            mDialog.dismiss();

                            String user_email = userEmail.getText().toString().trim();
                            String user_password = userPassword.getText().toString().trim();
                            final String user_phoneNumber = userMobile.getText().toString().trim();

                            if(validate()){
                                //Upload data to the database
                                firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){

                                            //Bundle bundle = new Bundle();
                                            //bundle.putString(FirebaseAnalytics.Param.ITEM_ID, user_phoneNumber);
                                            Toast.makeText(RegisterActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                                            //sendEmailVerification();
                                            //Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            //startActivity(intent);
                                            //finish();

                                        }else{
                                            Toast.makeText(RegisterActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                UserProfile user = new UserProfile(userEmail.getText().toString(),userMobile.getText().toString(),userName.getText().toString(), userPassword.getText().toString());
                                table_user.child(userMobile.getText().toString()).setValue(user).equals(firebaseAuth);
                                Toast.makeText(RegisterActivity.this, "Sign Up Successfully !", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    }
                    @Override
                    public void onCancelled (DatabaseError databaseError){
                    }
                });
            }
        });

    }
    private Boolean validate(){
        Boolean result = false;

        name = userName.getText().toString();
        password = userPassword.getText().toString();
        email = userEmail.getText().toString();
        mobile = userMobile.getText().toString();

        if(name.isEmpty() || password.isEmpty() || email.isEmpty() || mobile.isEmpty()){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
            takeInput();
        }else{
            result = true;
        }
        return result;
    }

    private void takeInput() {
        String name = userName.getText().toString();
        String password = userPassword.getText().toString();
        String email = userEmail.getText().toString();
        String mobile = userMobile.getText().toString();
    }

    /*

    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        //sendUserData();
                        Toast.makeText(RegisterActivity.this, "Successfully Sign Up, Verification Email is being sent!", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }else{
                        Toast.makeText(RegisterActivity.this, "Verification Email hasn't been sent!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /*
    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("User").child(firebaseAuth.getUid());
        UserProfile userProfile = new UserProfile(name, email, mobile);
        myRef.setValue(userProfile);
    }
     */
}