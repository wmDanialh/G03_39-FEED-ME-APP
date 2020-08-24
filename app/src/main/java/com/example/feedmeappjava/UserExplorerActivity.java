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

import com.example.feedmeappjava.Interface.ItemClickListener;
import com.example.feedmeappjava.Model.FoodCategoryModel;
import com.example.feedmeappjava.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class UserExplorerActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference category;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<FoodCategoryModel, MenuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_explorer);

        Toolbar toolbarProfile = findViewById(R.id.toolbarExplorer);
        setSupportActionBar(toolbarProfile);
        getSupportActionBar().setTitle("Explore");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbarProfile.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Init Firebase
        database = FirebaseDatabase.getInstance();
        category = database.getReference("categories");

        //Load Menu
        recycler_menu = (RecyclerView)findViewById(R.id.recycler_Menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        loadMenu();


    }

    private void loadMenu() {
        FirebaseRecyclerOptions<FoodCategoryModel> options = new FirebaseRecyclerOptions.Builder<FoodCategoryModel>().setQuery(category, FoodCategoryModel.class).build();

        adapter = new FirebaseRecyclerAdapter<FoodCategoryModel, MenuViewHolder>(options) {

            @Override
            public void onBindViewHolder(@NonNull MenuViewHolder holder, int position, @NonNull FoodCategoryModel model) {
                holder.txtMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).fit().into(holder.imageView);

                final FoodCategoryModel clickItem = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Get Category Id and send to new activity
                        Intent foodlist = new Intent(UserExplorerActivity.this,FoodList.class);
                        foodlist.putExtra("CategoryId",adapter.getRef(position).getKey());
                        startActivity(foodlist);
                        //Because CategoryId is key, so we just get key of this item
                        Toast.makeText(UserExplorerActivity.this, "" + clickItem.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @NonNull
            @Override
            public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
                return new MenuViewHolder(view);
            }
        };
        adapter.startListening();
        recycler_menu.setAdapter(adapter);

    }

}
