package com.example.tourismfyp.DahBoard.OrginazerPanel.ViewPosts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tourismfyp.Authentication.Login_Activity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.OrganizerPanel;
import com.example.tourismfyp.DahBoard.OrginazerPanel.PostCars.PostCarsActivity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.PostHotels.PostHotelActivity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.PostTours.PostToursActivity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.ViewPosts.Further.ViewAllOnCategory;
import com.example.tourismfyp.R;
import com.google.android.material.navigation.NavigationView;

public class ViewPostActivity extends AppCompatActivity {

    Toolbar toolbar;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Button hotelposts,carsposts,tourposts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nav = findViewById(R.id.navMenu);
        drawerLayout = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.home:

                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(getApplicationContext(),OrganizerPanel.class));
                        finish();
                        return true;

                    case R.id.posttour:

                        drawerLayout.closeDrawer(GravityCompat.START);

                        startActivity(new Intent(getApplicationContext(), PostToursActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));

                        finish();
                        return true;

                    case R.id.postcars:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(getApplicationContext(), PostCarsActivity.class));
                        finish();


                        return true;

                    case R.id.posthotels:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(getApplicationContext(), PostHotelActivity.class));
                        finish();


                        return true;

                    case R.id.viewpost:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(getApplicationContext(),ViewPostActivity.class));
                        finish();


                        return true;

                    case R.id.logout:
                        drawerLayout.closeDrawer(GravityCompat.START);

                        Intent intent2 = new Intent(getApplicationContext(), Login_Activity.class);
                        startActivity(intent2);
                        SharedPreferences settings = getSharedPreferences("name", Context.MODE_PRIVATE);
                        settings.edit().remove("Name").commit();
                        SharedPreferences settings1 = getSharedPreferences("loginName", Context.MODE_PRIVATE);
                        settings1.edit().remove("Email").commit();
                        finish();
                        return true;



                }
                return true;
            }
        });

        hotelposts=findViewById(R.id.hotelposts);
        carsposts=findViewById(R.id.carsposts);
        tourposts=findViewById(R.id.toursposts);

        hotelposts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ViewPostActivity.this, ViewAllOnCategory.class).putExtra("value","PostHotel").setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                finish();
            }
        });


        carsposts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewPostActivity.this,ViewAllOnCategory.class).putExtra("value","PostCars").setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                finish();

            }
        });

        tourposts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewPostActivity.this,ViewAllOnCategory.class).putExtra("value","PostTour").setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                finish();

            }
        });


    }
    @Override
    public void onBackPressed() {
        Intent intent= new Intent(ViewPostActivity.this,OrganizerPanel.class);
        startActivity(intent);
        finish();

    }
}