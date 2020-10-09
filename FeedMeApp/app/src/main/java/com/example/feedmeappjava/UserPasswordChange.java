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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class UserPasswordChange extends AppCompatActivity {

    Button btnSavePass, btnCancelPass;
    EditText newPassword, confPassword;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_password_change);

        Toolbar toolbar = findViewById(R.id.toolbarUserPassword);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Change Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        progressDialog =new ProgressDialog(this);

        newPassword = findViewById(R.id.newPass);

        btnSavePass = findViewById(R.id.btnSavePass);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        btnSavePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setMessage("Password is updating..");
                progressDialog.show();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    user.updatePassword(newPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(),"Your Password has been changes ",Toast.LENGTH_SHORT).show();
                                        firebaseAuth.signOut();
                                        finish();
                                        Intent intent = new Intent(UserPasswordChange.this, LoginActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(),"Please login as your new password to update ",Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(),"Password Update Failed ",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }


            }
        });

    }
}