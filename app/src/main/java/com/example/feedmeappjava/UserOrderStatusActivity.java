package com.example.feedmeappjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.feedmeappjava.Common.Common;
import com.example.feedmeappjava.Interface.ItemClickListener;
import com.example.feedmeappjava.Model.Request;
import com.example.feedmeappjava.Model.UserProfile;
import com.example.feedmeappjava.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserOrderStatusActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;

    String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_status);

        Toolbar toolbar = findViewById(R.id.toolbarOrderStatus);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Your Orders List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("requests");

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        DatabaseReference myRef = firebaseDatabase.getReference("Users").child(firebaseAuth.getUid());


        recyclerView = findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //If we start OrderStatus activity from Home Activity
        //We will not put any extra, so we just loadOrder by phone from Common
        if(getIntent() == null)
        {

        }
        else

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                    mobile = (userProfile.getUserMobile());

                    loadOrders(getIntent().getStringExtra("userPhone"));
                    loadOrders(mobile);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(UserOrderStatusActivity.this, databaseError.getCode(),Toast.LENGTH_SHORT).show();
                }
            });



    }

    private void loadOrders(String phone) {
        Query searchByPhone = requests.orderByChild("phone").equalTo(phone);
        // create options with query
        FirebaseRecyclerOptions<Request> foodOptions = new FirebaseRecyclerOptions.Builder<Request>().setQuery(searchByPhone, Request.class).build();

        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(foodOptions) {
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull final Request model) {
                holder.txtOrderId.setText(adapter.getRef(position).getKey());
                holder.txtOrderStatus.setText(Common.convertCodeToStatus(model.getStatus()));
                holder.txtOrderAddress.setText(model.getAddress());
                holder.txtOrderPhone.setText(model.getPhone());

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent orderDetail = new Intent(UserOrderStatusActivity.this, UserOrderFoodDetailActivity.class);
                        Common.currentRequest = model;
                        orderDetail.putExtra("OrderId",adapter.getRef(position).getKey());
                        startActivity(orderDetail);                    }
                });
            }



            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false);
                return new OrderViewHolder(itemView);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

}