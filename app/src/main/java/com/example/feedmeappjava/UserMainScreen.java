package com.example.feedmeappjava;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.feedmeappjava.Model.UserProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserMainScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    private FirebaseDatabase firebaseDatabase;
    private NavigationView navigationView;

    private CircleImageView imgHeader;
    //TextView txtHeader;

    WebView wv, youtube;

    String url= "https://www.facebook.com/projektmakanMY/?rf=174171870170011";

    String url1= "https://www.youtube.com/watch?v=ET3_mLy2Hs8";

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawerLayout;

    TextView textFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_screen);

        wv = (WebView)findViewById(R.id.wbe);
        wv.setWebViewClient(new WebViewClient());
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl(url);

        youtube = (WebView)findViewById(R.id.wbeYoutube);
        youtube.setWebViewClient(new WebViewClient());
        youtube.getSettings().setJavaScriptEnabled(true);
        youtube.loadUrl(url1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);

        //Init Firebase
        //firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseUser = firebaseAuth.getCurrentUser();
        //FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        //Toast.makeText(this, "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();
        //firebaseAuth = FirebaseAuth.getInstance();

        //DatabaseReference databaseReference = firebaseDatabase.getReference("User").child(firebaseAuth.getUid());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference("User");
        //textFullName = (TextView)navigationView.getHeaderView(0).findViewById(R.id.fullnameHead);

        //txtHeader   = (TextView)navigationView.getHeaderView(0).findViewById(R.id.fullnameHead);
        //imgHeader = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);


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
                    case R.id.drawer_myOrderStatus:
                        Intent i2_i = new Intent(UserMainScreen.this, UserOrderStatusActivity.class);
                        startActivity(i2_i);
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
                        Intent intent  = new Intent(UserMainScreen.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });

        /*
        StorageReference mImageRef = FirebaseStorage.getInstance().getReference(firebaseAuth.getUid()).child("Profile Pic");
        final long ONE_MEGABYTE = 1024 * 1024;

        mImageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);

                imgHeader.setMinimumHeight(dm.heightPixels);
                imgHeader.setMinimumWidth(dm.widthPixels);
                imgHeader.setImageBitmap(bm);

            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


         */
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                //txtHeader.setText(userProfile.getUserName());
                //textFullName.setText(dataSnapshot.child("userName").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UserMainScreen.this, databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*

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

        FirebaseUser currentFirebaseUser = firebaseAuth.getCurrentUser();

        if(currentFirebaseUser.getUid() != null){
            startActivity(new Intent(this,UserMainScreen.class));
            finish();
        }
        else {

        }
    }

     */
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