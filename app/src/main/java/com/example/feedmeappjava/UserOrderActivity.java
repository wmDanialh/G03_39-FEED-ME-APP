package com.example.feedmeappjava;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedmeappjava.Common.Common;
import com.example.feedmeappjava.Database.Database;
import com.example.feedmeappjava.Model.Order;
import com.example.feedmeappjava.Model.Request;
import com.example.feedmeappjava.ViewHolder.CartAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserOrderActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrice;
    Button btnPlace;

    List<Order> cart = new ArrayList<>();

    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order);

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

        final EditText edtName = new EditText(UserOrderActivity.this);
        //edtName.setHint("Enter your name");
        final EditText edtAddress = new EditText(UserOrderActivity.this);
        edtAddress.setHint("Enter your address");
        final EditText edtPhone = new EditText(UserOrderActivity.this);
        //edtPhone.setHint("Enter your mobile phone number");


        LinearLayout lp = new LinearLayout(UserOrderActivity.this);
        lp.setOrientation(LinearLayout.VERTICAL);
        lp.setPadding(8,8,8,8);

        /*LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

         */
        //lp.addView(edtName);
        //lp.addView(edtPhone);
        lp.addView(edtAddress);

        //edtPhone.setLayoutParams(lp);
        //edtAddress.setLayoutParams(lp);
        //alertDialog.setView(edtPhone);
        //alertDialog.setView(edtAddress);

        alertDialog.setView(lp);
        alertDialog.setIcon(R.drawable.ic_baseline_shopping_cart_24);

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

                Request request = new Request(

                        Common.currentUser.getUserMobile(),
                        Common.currentUser.getUserName(),
                        //edtPhone.getText().toString(), //< ------- PROBLEM AS GG AS FF (PRESS F TO PAY RESPECT)
                        //edtName.getText().toString(), // <------- PROBLEM AS GG AS FF (PRESS F TO PAY RESPECT)
                        //We figure it out on how to get address from input user in another activity
                        edtAddress.getText().toString(),
                        txtTotalPrice.getText().toString(),
                        cart
                );
                requests.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(UserOrderActivity.this,"Thank you, Your order has been placed", Toast.LENGTH_SHORT).show();
                finish();
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

}