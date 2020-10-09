package com.example.feedmeappjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class UserForgotPasswordActivity extends AppCompatActivity {

    EditText newEmailToUserForgotPassword;

    Button btnSavePass, btnCancelPass;
    EditText newPassword, confPassword;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_forgot_password);

        Toolbar toolbar = findViewById(R.id.toolbarUserForgotPassword);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Forgot Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //toolbar.setTitleTextColor(getResources().getColor(android.R.color.holo_red_dark));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        newEmailToUserForgotPassword = findViewById(R.id.newUserForgotPassword);
        progressDialog =new ProgressDialog(this);
        btnSavePass = findViewById(R.id.btnSendReset);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        btnSavePass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                progressDialog.setMessage("Password is updating..");
                progressDialog.show();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                firebaseAuth.sendPasswordResetEmail(newEmailToUserForgotPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Your email has been sent",Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(),"Please check your email for resetting your password ",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UserForgotPasswordActivity.this, LoginActivity.class);
                            startActivity(intent);

                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Your email is failed to send ",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });


    }
}