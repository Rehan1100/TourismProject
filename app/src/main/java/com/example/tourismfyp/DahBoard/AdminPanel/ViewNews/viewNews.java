package com.example.tourismfyp.DahBoard.AdminPanel.ViewNews;

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
import android.widget.EditText;

import com.example.tourismfyp.Authentication.Login_Activity;
import com.example.tourismfyp.DahBoard.AdminPanel.AddOrginizer.AddOrginizers;
import com.example.tourismfyp.DahBoard.AdminPanel.ViewOrganizer.Adapter;
import com.example.tourismfyp.DahBoard.AdminPanel.ViewOrganizer.ModelCLass;
import com.example.tourismfyp.DahBoard.AdminPanel.ViewOrganizer.ViewOragnizer;
import com.example.tourismfyp.DahBoard.AdminPanel.adminPanel;
import com.example.tourismfyp.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class viewNews extends AppCompatActivity {

    Toolbar toolbar;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    private String LoginUserEmail;
    private String Name;
    Intent intent2;
    RecyclerView recyclerView;
    ArrayList<NewsModelCLass> modelCLassList= new ArrayList<>();
    ViewNewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news);
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
                    case R.id.home1:

                        intent2 = new Intent(getApplicationContext(), adminPanel.class);
                        startActivity(intent2);
                        finish();
                        return true;

                    case R.id.AddOrganizer:

                        intent2 = new Intent(getApplicationContext(), AddOrginizers.class);
                        startActivity(intent2);
                        finish();
                        return true;

                    case R.id.ViewOrganizer:

                        intent2 = new Intent(getApplicationContext(), ViewOragnizer.class);
                        startActivity(intent2);
                        finish();
                        return true;


                    case R.id.Viewnews:

                        intent2 = new Intent(getApplicationContext(), viewNews.class);
                        startActivity(intent2);
                        finish();
                        return true;



                    case R.id.logout:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        intent2 = new Intent(getApplicationContext(), Login_Activity.class);
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
        SharedPreferences getShared = getApplicationContext().getSharedPreferences("loginName", MODE_PRIVATE);
        LoginUserEmail = getShared.getString("Email", null);
        Name=getShared.getString("fname",null);
        recyclerView=findViewById(R.id.newRecyclerView);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference().child("News");
        Query query = reference.orderByChild("loginUserEmail").equalTo(LoginUserEmail);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    for (DataSnapshot d : snapshot.getChildren()) {
                        String key = d.getKey();

                        String id = snapshot.child(key).child("id").getValue(String.class);
                        String name = snapshot.child(key).child("name").getValue(String.class);
                        String loginUserEmail = snapshot.child(key).child("loginUserEmail").getValue(String.class);
                        String news = snapshot.child(key).child("news").getValue(String.class);
                        String postDate = snapshot.child(key).child("postDate").getValue(String.class);
                        String postTime = snapshot.child(key).child("postTime").getValue(String.class);

                        NewsModelCLass modelCLass = new NewsModelCLass(id, loginUserEmail, name,news,postDate,postTime);
                        modelCLassList.add(modelCLass);
                    }
                    adapter = new ViewNewsAdapter(modelCLassList, viewNews.this);

                    if(modelCLassList.isEmpty())
                    {
                        adapter.notifyDataSetChanged();
                        recyclerView.setVisibility(View.GONE);
                    }
                    else
                    {
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
        Intent intent= new Intent(viewNews.this,adminPanel.class);
        startActivity(intent);
        finish();
    }
}