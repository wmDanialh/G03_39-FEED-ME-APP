package com.example.feedmeappjava;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedmeappjava.User.UserProfile;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserMainScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private NavigationView navigationView;

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawerLayout;

    TextView textFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);

        //Init Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference("User").child(firebaseAuth.getUid());

        //textFullName = (TextView)navigationView.getHeaderView(0).findViewById(R.id.fullnameHead);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.drawer_explorer:
                        Intent i = new Intent(UserMainScreen.this, UserExplorerActivity.class);
                        startActivity(i);
                        break;
                    case R.id.drawer_myOrder:
                        Intent i2 = new Intent(UserMainScreen.this, UserOrderActivity.class);
                        startActivity(i2);
                        break;
                    case R.id.drawer_myProfile:
                        Intent i3 = new Intent(UserMainScreen.this, UserProfileActivity.class);
                        startActivity(i3);
                        break;
                    case R.id.drawer_myAddresses:
                        Intent i4 = new Intent(UserMainScreen.this, UserAddressActivity.class);
                        startActivity(i4);
                        break;
                    case R.id.drawer_myHelp:
                        Intent i5 = new Intent(UserMainScreen.this, UserHelpCentreActivity.class);
                        startActivity(i5);
                        break;
                    case R.id.drawer_item_settings:
                        Intent i6 = new Intent(UserMainScreen.this, UserSettingsActivity.class);
                        startActivity(i6);
                        break;
                    case R.id.drawer_item_tAndC:
                        Intent i7 = new Intent(UserMainScreen.this, UserTermsAndConditions.class);
                        startActivity(i7);
                        break;
                    case R.id.drawer_items_logout:
                        logOut();
                        return true;
                }
                return false;
            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //textFullName.setText(dataSnapshot.child("userName").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UserMainScreen.this, databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void logOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Log Out");
        builder.setMessage("Are you sure you would like to log out the app?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog

                firebaseAuth.signOut();
                startActivity(new Intent(UserMainScreen.this,LoginActivity.class));
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser mFirebaseUser = firebaseAuth.getCurrentUser();
        if(mFirebaseUser != null){

        }
        else {
            startActivity(new Intent(this,UserMainScreen.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}