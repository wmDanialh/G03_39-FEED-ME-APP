package com.example.feedmeappjava;
// 1. We gonna set the UID as our phone number
// 2.the register will be our email and password authentication


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.WindowManager;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class RegisterActivity extends AppCompatActivity {

    private EditText userName, userPassword, userEmail, userMobile;
    private FloatingActionButton regButton;
    private TextView userLogin;
    FirebaseAuth firebaseAuth;

    String email, name, password, mobile;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        userName = (EditText)findViewById(R.id.etUserName);
        userPassword = (EditText)findViewById(R.id.etUserPassword);
        userEmail = (EditText)findViewById(R.id.etUserEmail);
        userMobile = (EditText)findViewById(R.id.etUserMobile);
        regButton = (FloatingActionButton) findViewById(R.id.btnSignUp);
        userLogin = (TextView) findViewById(R.id.tvUserLogin);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

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



                if (validate()) {

                    String privacy_pol = "<a href='https://feedmeapptermsandconditions.blogspot.com/2020/09/feed-me-appprivacy-policy-this-privacy.html'> Privacy Policy </a>";
                    String toc = "<a href='https://feedmeapptermsandconditions.blogspot.com/2020/09/feed-me-appt-published2020-terms-ofuse.html'> Terms and Conditions </a>";

                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage(Html.fromHtml("By using this application, you agree to " + privacy_pol + " and " + toc + " of this application."));
                    builder.setPositiveButton("ACCEPT", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            progressDialog.setMessage("Please wait ...");
                            progressDialog.show();

                            // Do nothing but close the dialog
                            String user_email = userEmail.getText().toString().trim();
                            String user_password = userPassword.getText().toString().trim();
                            firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        sendUserData();
                                        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    //sendEmailVerification();
                                                    progressDialog.dismiss();
                                                    Toast.makeText(RegisterActivity.this, "Successfully Sign Up, Verification Email is being sent!", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                                    finish();
                                                }
                                                else {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                                    Toast.makeText(RegisterActivity.this, "Verification Email Failed to Send", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                        Toast.makeText(RegisterActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    });
                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing
                            dialog.dismiss();
                        }
                    });

                    builder.show();


                    //Upload data to the database

                }




            }

            private Boolean validate(){
                Boolean result = false;

                name = userName.getText().toString();
                password = userPassword.getText().toString();
                email = userEmail.getText().toString();
                mobile = userMobile.getText().toString();

                if(name.isEmpty() || password.isEmpty() || email.isEmpty() || mobile.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
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

            private void sendEmailVerification(){
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                sendUserData();
                                Toast.makeText(RegisterActivity.this, "Successfully Sign Up, Verification Email is being sent!", Toast.LENGTH_SHORT).show();
                                firebaseAuth.signOut();
                                finish();
                                startActivity(new Intent(RegisterActivity.this, UserMainScreen.class));
                            }else{
                                Toast.makeText(RegisterActivity.this, "Verification Email hasn't been sent!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            private void sendUserData(){
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference myRef = firebaseDatabase.getReference("Users").child(firebaseAuth.getUid());
                UserProfile userProfile = new UserProfile(mobile,name,email);
                myRef.setValue(userProfile);
            }

        });
    }
            }