package com.example.tourismfyp.DahBoard.OrginazerPanel.news;

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
import android.widget.TextView;

import com.example.tourismfyp.Authentication.Login_Activity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.NotificationPanel.NotificationAdapter;
import com.example.tourismfyp.DahBoard.OrginazerPanel.NotificationPanel.NotificationModelClass;
import com.example.tourismfyp.DahBoard.OrginazerPanel.OrganizerPanel;
import com.example.tourismfyp.DahBoard.OrginazerPanel.PostCars.PostCarsActivity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.PostHotels.PostHotelActivity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.PostTours.PostToursActivity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.ViewPosts.ViewPostActivity;
import com.example.tourismfyp.DahBoard.UserPanel.News.UserNews;
import com.example.tourismfyp.DahBoard.UserPanel.News.UserNewsAdapter;
import com.example.tourismfyp.DahBoard.UserPanel.News.UserNewsModelClass;
import com.example.tourismfyp.DahBoard.UserPanel.UserPanel;
import com.example.tourismfyp.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrganizerNews extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView msg;
    FirebaseDatabase reference;

    //Drawer Work
    Toolbar toolbar;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    public String LoginUserFirstName,LoginUserLastName,LoginUserEmail;

    public UserNewsAdapter adapter;
    ArrayList<UserNewsModelClass> modelCLassList=new ArrayList<>();
    public UserNewsModelClass modelCLass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_news);
        msg = findViewById(R.id.textView6);

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
                        startActivity(new Intent(getApplicationContext(), OrganizerPanel.class));
                        finish();
                        return true;

                    case R.id.posttour:

                        drawerLayout.closeDrawer(GravityCompat.START);

                        startActivity(new Intent(getApplicationContext(), PostToursActivity.class));
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
                        startActivity(new Intent(getApplicationContext(), ViewPostActivity.class));
                        finish();


                        return true;

                    case R.id.logout:
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
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        SharedPreferences getShared = getApplicationContext().getSharedPreferences("loginName", MODE_PRIVATE);
        LoginUserEmail = getShared.getString("Email", null);
        LoginUserFirstName = getShared.getString("fname", null);
        LoginUserLastName = getShared.getString("lname", null);

        reference = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference =    reference.getReference("News");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    for (DataSnapshot d : snapshot.getChildren()) {
                        String key = d.getKey();

                        String id = snapshot.child(key).child("id").getValue(String.class);
                        String loginUserEmail = snapshot.child(key).child("loginUserEmail").getValue(String.class);
                        String name = snapshot.child(key).child("name").getValue(String.class);
                        String news = snapshot.child(key).child("news").getValue(String.class);
                        String postDate = snapshot.child(key).child("postDate").getValue(String.class);
                        String postTime = snapshot.child(key).child("postTime").getValue(String.class);

                        modelCLass= new UserNewsModelClass(id,loginUserEmail,name,news,postDate,postTime);
                        modelCLassList.add(modelCLass);
                    }
                    adapter = new UserNewsAdapter(modelCLassList, OrganizerNews.this,LoginUserEmail,LoginUserFirstName,LoginUserLastName);
                    if (modelCLassList.isEmpty()) {
                        msg.setVisibility(View.VISIBLE);
                        adapter.notifyDataSetChanged();
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        msg.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                        recyclerView.setVisibility(View.VISIBLE);
                    }


                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(OrganizerNews.this, OrganizerPanel.class);
        startActivity(intent);
        finish();

    }
}