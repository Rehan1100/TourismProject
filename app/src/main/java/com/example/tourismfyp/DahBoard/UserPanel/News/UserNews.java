package com.example.tourismfyp.DahBoard.UserPanel.News;

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
import com.example.tourismfyp.DahBoard.UserPanel.Cart.CartActivity;
import com.example.tourismfyp.DahBoard.UserPanel.Cart.CartAdapter;
import com.example.tourismfyp.DahBoard.UserPanel.Cart.CartModelClass;
import com.example.tourismfyp.DahBoard.UserPanel.UserPanel;
import com.example.tourismfyp.DahBoard.UserPanel.ViewPosts.ViewPosts;
import com.example.tourismfyp.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserNews extends AppCompatActivity {
    //Drawer Work
    Toolbar toolbar;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    TextView msg;

    public String LoginUserFirstName,LoginUserLastName,LoginUserEmail;
    FirebaseDatabase reference;
    UserNewsModelClass modelCLass;
    List<UserNewsModelClass> modelCLassList= new ArrayList<>();
    UserNewsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_news);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nav = findViewById(R.id.navMenu);
        msg = findViewById(R.id.textView6);

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
                        startActivity(new Intent(getApplicationContext(), UserPanel.class));
                        finish();
                        return true;

                    case R.id.viewtour:

                        drawerLayout.closeDrawer(GravityCompat.START);

                        startActivity(new Intent(getApplicationContext(), ViewPosts.class).putExtra("value","PostTour"));
                        finish();

                        return true;

                    case R.id.viewcars:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(getApplicationContext(),ViewPosts.class).putExtra("value","PostCars"));
                        finish();


                        return true;

                    case R.id.viewhotels:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(getApplicationContext(),ViewPosts.class).putExtra("value","PostHotel"));

                        finish();

                        return true;

                    case R.id.viewNews:

                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent = new Intent(getApplicationContext(), UserNews.class);
                        startActivity(intent);
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

        recyclerView = findViewById(R.id.Recyclernews);
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
                         adapter = new UserNewsAdapter(modelCLassList, UserNews.this,LoginUserEmail,LoginUserFirstName,LoginUserLastName);
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
        Intent intent = new Intent(UserNews.this, UserPanel.class);
        startActivity(intent);
        finish();

    }
}