package com.example.feedmeappjava;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class UserMainScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth firebaseAuth;

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();

        //Init Firebase

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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

        //Set Name For User
        View headerView = navigationView.getHeaderView(0);

        //Load Menu

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