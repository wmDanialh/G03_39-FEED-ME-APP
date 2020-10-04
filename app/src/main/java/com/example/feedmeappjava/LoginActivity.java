package com.example.feedmeappjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedmeappjava.Common.Common;
import com.example.feedmeappjava.Model.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class LoginActivity extends AppCompatActivity {

    private EditText Email;
    private EditText Password;
    private FloatingActionButton Login;
    private TextView userSignUp;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Email = (EditText)findViewById(R.id.etEmail);
        Password = (EditText)findViewById(R.id.etPassword);
        Login = findViewById(R.id.btnLogin);
        userSignUp = (TextView)findViewById(R.id.tvUserSignUp);
        forgotPassword = (TextView)findViewById(R.id.tvForgotPassword);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("Users");

        if(user != null){

            if((user.getEmail()).equals("nursyahiraamira@gmail.com")){
                finish();
                startActivity(new Intent(LoginActivity.this, AdminMainScreen.class));
            }else{
                finish();
                startActivity(new Intent(LoginActivity.this, UserMainScreen.class));
            }

        }


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Email.getText().toString().isEmpty() || Password.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    validate(Email.getText().toString(),Password.getText().toString());
                }
            }
        });


        userSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, UserForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

    }


    private void validate (String userEmail, String userPassword){

        progressDialog.setMessage("Please wait ...");
        progressDialog.show();

        if(userEmail.equals("nursyahiraamira@gmail.com")) {

            firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        //checkEmailVerification1();
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(LoginActivity.this, AdminMainScreen.class));

                    } else {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }else{
            firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        if(firebaseAuth.getCurrentUser().isEmailVerified()){
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(LoginActivity.this, UserMainScreen.class));
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Email is not Verified", Toast.LENGTH_SHORT).show();
                        }
                        //checkEmailVerification1();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void checkEmailVerification1(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        if(emailflag){
            finish();
            //Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(MainActivity.this, SecondActivity.class));
        }else{
            Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

}
