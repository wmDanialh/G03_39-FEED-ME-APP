package com.example.feedmeappjava.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.feedmeappjava.Model.CardMainScreen;
import com.example.feedmeappjava.R;

import java.util.List;

public class ImageCardAdapter extends PagerAdapter {

    private List<CardMainScreen> models;
    private LayoutInflater layoutInflater;
    private Context context;

    public ImageCardAdapter(List<CardMainScreen> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.card_item,container,false);

        ImageView imageView;
        TextView title, desc;

        imageView = view.findViewById(R.id.imageCard);
        title = view.findViewById(R.id.titleCard);
        desc = view.findViewById(R.id.descCard);

        imageView.setImageResource(models.get(position).getImageCard());
        title.setText(models.get(position).getTitleCard());
        desc.setText(models.get(position).getDescCard());

        container.addView(view,0);

        return view;
    }
}
