package com.example.feedmeappjava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UserHelpCentreActivity extends AppCompatActivity {

    ListView listView;

    String mTitle[] = {"My Account","My Orders","Making Payment","FAQ"};
    int images[] = {R.drawable.ic_person, R.drawable.ic_baseline_notifications_black, R.drawable.ic_baseline_credit_card_24, R.drawable.ic_baseline_more_horiz_24};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_help_centre);

        Toolbar toolbar = findViewById(R.id.toolbarHelpCentre);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Help Centre");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        listView = findViewById(R.id.listviewHelp);

        MyAdapter adapter = new MyAdapter(this,mTitle,images);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    Toast.makeText(UserHelpCentreActivity.this, "My Account", Toast.LENGTH_SHORT).show();
                }
                if (i == 0 ){
                    Toast.makeText(UserHelpCentreActivity.this, "My Orders", Toast.LENGTH_SHORT).show();
                }
                if (i == 0){
                    Toast.makeText(UserHelpCentreActivity.this, "Making Payment", Toast.LENGTH_SHORT).show();
                }
                if (i == 0){
                    Toast.makeText(UserHelpCentreActivity.this, "FAQ", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

     class MyAdapter extends ArrayAdapter<String>{

        Context context;
        String rTitle[];
        int rImage[];

        MyAdapter(Context c, String title[], int img[]){
            super(c,R.layout.help_centre_row, R.id.texttexttext, title);
            this.context = c;
            this.rTitle = title;
            this.rImage = img;

        }

         @NonNull
         @Override
         public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

             LayoutInflater layoutInflater = (LayoutInflater)getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

             View row = layoutInflater.inflate(R.layout.help_centre_row, parent, false);
             ImageView imageView = row.findViewById(R.id.img_help);
             TextView textView = row.findViewById(R.id.texttexttext);

             imageView.setImageResource(rImage[position]);
             textView.setText(rTitle[position]);


             return row;
         }
     }

}