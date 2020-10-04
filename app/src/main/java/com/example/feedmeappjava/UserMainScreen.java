package com.example.feedmeappjava;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.feedmeappjava.Adapter.ImageCardAdapter;
import com.example.feedmeappjava.Model.CardMainScreen;
import com.example.feedmeappjava.Model.UserProfile;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserMainScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth firebaseAuth;

    private FirebaseDatabase firebaseDatabase;
    private NavigationView navigationView;

    ViewPager viewPager;
    ImageCardAdapter adapter;
    List<CardMainScreen> models;
    ViewFlipper v_flipper;

    TextView textFullName, getTextFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child(firebaseAuth.getUid());

        models = new ArrayList<>();
        models.add(new CardMainScreen(R.drawable.jjcm_one, "JJCM | Projekt Makan | 11 Feb 2018", "Check us on JJCM @Youtube Channel to Discover More #ProjeckMakanKuantan #JJCM #MalaysianFood"));
        models.add(new CardMainScreen(R.drawable.file, "ProjektMakan : Cut The Crab", "Follow us more on @projektmakanMY #ProjeckMakanKuantan #Kuantan #MalaysianFood"));
        models.add(new CardMainScreen(R.drawable.alaska, "Projekt Makan | Shellout Kuantan", "Follow us more on @Instagram #ProjeckMakanKuantan #Kuantan #MalaysianFood"));
        models.add(new CardMainScreen(R.drawable.makan, "Projekt Makan | VMO ", "Read more on us at our website for more information @VMO #ProjeckMakanKuantan #MalaysianFood"));

        adapter = new ImageCardAdapter(models, this);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(100, 0, 100, 0);

        int images[] = {R.drawable.ai, R.drawable.bi, R.drawable.ci,R.drawable.di,R.drawable.ei};
        v_flipper = findViewById(R.id.viewFlipper);

        for (int image : images) {
            setV_flipper(image);
        }


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
                switch (id) {
                    case R.id.drawer_explorer:
                        Intent i = new Intent(UserMainScreen.this, UserExplorerActivity.class);
                        startActivity(i);
                        break;
                    case R.id.drawer_myOrder:
                        Intent i2 = new Intent(UserMainScreen.this, UserOrderActivity.class);
                        startActivity(i2);
                        break;
                    case R.id.drawer_myOrderStatus:
                        Intent i2_i = new Intent(UserMainScreen.this, UserOrderStatusActivity.class);
                        startActivity(i2_i);
                        break;
                    case R.id.drawer_myProfile:
                        Intent i3 = new Intent(UserMainScreen.this, UserProfileActivity.class);
                        startActivity(i3);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(UserMainScreen.this);

                        builder.setTitle("Log Out");
                        builder.setMessage("Are you sure you would like to log out the app?");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing but close the dialog
                                firebaseAuth.signOut();
                                startActivity(new Intent(UserMainScreen.this, LoginActivity.class));
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
                        return  true;
                }
                return false;
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                View headerView = navigationView.getHeaderView(0);
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);

                textFullName = headerView.findViewById(R.id.fullnameHead);
                getTextFullName = (TextView)findViewById(R.id.userMainScreenName);

                textFullName.setText(userProfile.getUserName());
                getTextFullName.setText(userProfile.getUserName());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UserMainScreen.this, databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setV_flipper(int imageS) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(imageS);
        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(5000);
        v_flipper.setAutoStart(true);
        v_flipper.setInAnimation(this, android.R.anim.slide_in_left);
        v_flipper.setInAnimation(this, android.R.anim.slide_out_right);

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