package com.example.feedmeappjava.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;

import com.example.feedmeappjava.Model.FoodCategoryModel;
import com.example.feedmeappjava.R;

import java.util.List;

public class FoodCategoryAdapter extends PagerAdapter {

    private List<FoodCategoryModel> model;
    private LayoutInflater layoutInflater;
    private Context context;

    public FoodCategoryAdapter(List<FoodCategoryModel> model, Context context) {
        this.model = model;
        this.context = context;
    }

    public FoodCategoryAdapter(FragmentManager childFragmentManager) {
    }

    @Override
    public int getCount(){
        return model.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        layoutInflater = layoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.food_category_swipe_pager, container,false);

        ImageView imageView;
        TextView title, kinds;

        imageView = view.findViewById(R.id.image);
        title = view.findViewById(R.id.title);
        kinds = view.findViewById(R.id.kinds);

        imageView.setImageResource(model.get(position).getImage());
        title.setText(model.get(position).getTitle());
        kinds.setText(model.get(position).getKinds());

        container.addView(view,0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
