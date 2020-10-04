package com.example.feedmeappjava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedmeappjava.Common.Common;
import com.example.feedmeappjava.Common.Config;
import com.example.feedmeappjava.Database.Database;
import com.example.feedmeappjava.Model.Order;
import com.example.feedmeappjava.Model.Request;
import com.example.feedmeappjava.Model.UserProfile;
import com.example.feedmeappjava.ViewHolder.CartAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserOrderActivity extends AppCompatActivity {

    private static final int PAYPAL_REQUEST_CODE = 9999;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrice;
    Button btnPlace;

    List<Order> cart = new ArrayList<>();

    CartAdapter adapter;

    static PayPalConfiguration config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);

    String address, comment ;

    String mobile , name;

    FirebaseDatabase databaseUser;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order);

        Toolbar toolbar = findViewById(R.id.toolbarOrder);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Your Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //toolbar.setTitleTextColor(getResources().getColor(android.R.color.holo_red_dark));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Init Paypal
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("requests");

        recyclerView = (RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView)findViewById(R.id.total);
        btnPlace = (Button)findViewById(R.id.btnPlaceOrder);

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create new request
                showAlertDialog();
            }
        });

        loadListFood();

    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserOrderActivity.this);
        alertDialog.setTitle("One More Step");
        //alertDialog.setMessage("Please enter your address: ");
        alertDialog.setMessage("Please enter as follow: ");

        LayoutInflater inflater = this.getLayoutInflater();
        View order_address_comment = inflater.inflate(R.layout.order_address_comment,null);

        final MaterialEditText edtAddress = (MaterialEditText)order_address_comment.findViewById(R.id.edtAddress);
        final MaterialEditText edtComment = (MaterialEditText)order_address_comment.findViewById(R.id.edtComment);

        alertDialog.setView(order_address_comment);
        alertDialog.setIcon(R.drawable.ic_baseline_shopping_cart_24);

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Show Paypal to payment

                //first, get address and
                address = edtAddress.getText().toString();
                comment = edtComment.getText().toString();

                String formatAmount = txtTotalPrice.getText().toString()
                        .replace("$","")
                        .replace(",","");
                //float amount = Float.parseFloat(formatAmount);

                PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(formatAmount),
                        "USD",
                        "FeedMe App Order",
                        PayPalPayment.PAYMENT_INTENT_SALE);

                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
                startActivityForResult(intent,PAYPAL_REQUEST_CODE);

                //FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        databaseUser = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        DatabaseReference myRef = databaseUser.getReference("Users").child(firebaseAuth.getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);

                name = userProfile.getUserName();
                mobile = userProfile.getUserMobile();

                if (requestCode == PAYPAL_REQUEST_CODE) {
                    if (resultCode == RESULT_OK) {
                        PaymentConfirmation paymentConfirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                        if (paymentConfirmation != null) {
                            try {
                                String paymentDetail = paymentConfirmation.toJSONObject().toString(4);
                                JSONObject jsonObject = new JSONObject(paymentDetail);

                                Request request = new Request(
                                        address,
                                        cart,
                                        name,
                                        jsonObject.getJSONObject("response").getString("state"),
                                        mobile,
                                        "0",
                                        //edtPhone.getText().toString(), //< ------- PROBLEM AS GG AS FF (PRESS F TO PAY RESPECT)
                                        //edtName.getText().toString(), // <------- PROBLEM AS GG AS FF (PRESS F TO PAY RESPECT)
                                        //We figure it out on how to get address from input user in another activity
                                        txtTotalPrice.getText().toString(),
                                        comment
                                );
                                requests.child(String.valueOf(System.currentTimeMillis()))
                                        .setValue(request);
                                new Database(getBaseContext()).cleanCart();
                                Toast.makeText(UserOrderActivity.this, "Thank you, Your order has been placed", Toast.LENGTH_SHORT).show();
                                finish();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        Toast.makeText(UserOrderActivity.this, "Payment cancel ", Toast.LENGTH_SHORT).show();
                    }
                    else if(resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
                        Toast.makeText(UserOrderActivity.this,"Invalid Payment", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UserOrderActivity.this, databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });


    }

    //Problem
    private void loadListFood() {

        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);

        //Calculate total price
        int total = 0;
        for(Order order:cart)
            total +=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if(item.getTitle().equals(Common.DELETE))
            deleteCart(item.getOrder());
        return true;
    }

    private void deleteCart(int position) {

        cart.remove(position);

        new Database(this).cleanCart();

        for(Order item:cart)
            new Database(this).addToCart(item);
    }
}