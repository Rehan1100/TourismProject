package com.example.tourismfyp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourismfyp.Authentication.Login_Activity;
import com.example.tourismfyp.SplashScreens.MyAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    MyAdapter myAdapter;
    ActionBar actionBar;
    ViewPager viewPager;
    LinearLayout linearLayout;
    TextView[]  dotsTv;
    int[] layouts;
    ImageButton nextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        actionBar=getSupportActionBar();
        if (actionBar!=null)
        {
            actionBar.hide();
        }

        viewPager=findViewById(R.id.viewpagger);
        linearLayout=findViewById(R.id.dotslayout);
        nextBtn=findViewById(R.id.nextBtn);
        /*
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAppStartstatus(false);

                startActivity(new Intent(MainActivity.this, homeActivity.class));
            }
        });
*/
        statusBarTranscript();
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPage=viewPager.getCurrentItem()+1;
                if (currentPage<layouts.length)
                {
                    viewPager.setCurrentItem(currentPage);

                }else {
                    setAppStartstatus(false);
                    startActivity(new Intent(MainActivity.this, Login_Activity.class));
                    finish();
                }

            }
        });
        layouts = new int[]{R.layout.slide1,
                R.layout.slide2,
                R.layout.slide3};
            myAdapter=new MyAdapter(layouts,getApplicationContext());
            viewPager.setAdapter(myAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==layouts.length-1)
                {
                    nextBtn.setVisibility(View.VISIBLE);
                    // skipBtn.setVisibility(View.GONE);
                }

                setDots(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setDots(0);

    }

    private void statusBarTranscript() {
        if (Build.VERSION.SDK_INT>=21)
        {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            Window window=getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
    private void setDots(int page)
    {
        linearLayout.removeAllViews();
        dotsTv=new TextView[layouts.length];
        for (int i=0;i<dotsTv.length;i++)
        {
            dotsTv[i]=new TextView(this);
            dotsTv[i].setText(Html.fromHtml("&#8226;"));
            dotsTv[i].setTextSize(30);
            dotsTv[i].setTextColor(Color.parseColor("#a9b4bb"));
            linearLayout.addView(dotsTv[i]);
        }
        if (dotsTv.length>0)
        {
            dotsTv[page].setTextColor(Color.parseColor("#FFFFFF"));
        }

    }
    private boolean isFirstTimeAppStart()
    {
        SharedPreferences preferences=getApplication().getSharedPreferences("SLIDE", Context.MODE_PRIVATE);
        return preferences.getBoolean("APPSTART",true);


    }
    private void setAppStartstatus(boolean status)
    {
        SharedPreferences preferences=getApplication().getSharedPreferences("SLIDE", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean("APPSTART",status);
        editor.apply();

    }
}