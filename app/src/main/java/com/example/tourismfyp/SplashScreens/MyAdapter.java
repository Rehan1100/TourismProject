package com.example.tourismfyp.SplashScreens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class MyAdapter extends PagerAdapter {
   private LayoutInflater inflater;
   int[] layout;
   Context context;

    public MyAdapter(int[] layout, Context context) {
        this.layout = layout;
        this.context = context;
    }

    @Override
    public int getCount() {
        return layout.length;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view=(View) object;
        container.removeView(view);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view= inflater.inflate(layout[position],container,false);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

}
