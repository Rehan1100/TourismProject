package com.example.tourismfyp.DahBoard.UserPanel.ViewPosts;

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
import android.widget.Toast;

import com.example.tourismfyp.Authentication.Login_Activity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.OrganizerPanel;
import com.example.tourismfyp.DahBoard.OrginazerPanel.PostCars.PostCarsActivity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.PostHotels.PostHotelActivity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.PostTours.PostToursActivity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.ViewPosts.Further.PostAdapter;
import com.example.tourismfyp.DahBoard.OrginazerPanel.ViewPosts.Further.PostModelCLass;
import com.example.tourismfyp.DahBoard.OrginazerPanel.ViewPosts.Further.ViewAllOnCategory;
import com.example.tourismfyp.DahBoard.OrginazerPanel.ViewPosts.ViewPostActivity;
import com.example.tourismfyp.DahBoard.UserPanel.BookingPost.DetailsPostActivity;
import com.example.tourismfyp.DahBoard.UserPanel.News.UserNews;
import com.example.tourismfyp.DahBoard.UserPanel.UserPanel;
import com.example.tourismfyp.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewPosts extends AppCompatActivity {
    RecyclerView recyclerView;
    Toolbar toolbar;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    ArrayList<PostModelCLass> modelCLassList= new ArrayList<>();
    DatabaseReference reference;
    ViewPostAdapter adapter;
    TextView msg;
    String perhead;
    String LoginUserEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_posts);
        String value=getIntent().getStringExtra("value").toString();
//        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
        toolbar = findViewById(R.id.toolbar);
        if (value.equals("PostHotel"))
        {
            toolbar.setTitle("Hotels Post");

        }
        else if (value.equals("PostCars"))
        {
            toolbar.setTitle("Cars Post");

        }
        else if (value.equals("PostTour"))
        {
            toolbar.setTitle("Tour Posts");

        }
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
                        startActivity(new Intent(ViewPosts.this,UserPanel.class));
                        finish();
                        return true;

                    case R.id.viewtour:

                        drawerLayout.closeDrawer(GravityCompat.START);

                        startActivity(new Intent(ViewPosts.this, ViewPosts.class).putExtra("value","PostTour"));
                        finish();

                        return true;

                    case R.id.viewcars:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(ViewPosts.this,ViewPosts.class).putExtra("value","PostCars"));
                        finish();


                        return true;

                    case R.id.viewhotels:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(ViewPosts.this,ViewPosts.class).putExtra("value","PostHotel"));

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

        recyclerView=findViewById(R.id.viewAllPosts);
        msg = findViewById(R.id.textView6);

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        SharedPreferences getShared = getApplicationContext().getSharedPreferences("loginName", MODE_PRIVATE);
        LoginUserEmail = getShared.getString("Email", null);

        reference = FirebaseDatabase.getInstance().getReference().child("Post");
        Query query = reference.orderByChild("category").equalTo(value);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    for (DataSnapshot d : snapshot.getChildren()) {
                        String key = d.getKey();
                        String cnic = snapshot.child(key).child("cnic").getValue(String.class);
                        String currentdate = snapshot.child(key).child("currentdate").getValue(String.class);
                        String desc = snapshot.child(key).child("desc").getValue(String.class);
                        String id = snapshot.child(key).child("id").getValue(String.class);
                        String item = snapshot.child(key).child("item").getValue(String.class);
                        String latitude = snapshot.child(key).child("latitude").getValue(String.class);
                        String loginuser = snapshot.child(key).child("loginuser").getValue(String.class);
                        String longitude = snapshot.child(key).child("longitude").getValue(String.class);
                        String deadlineDate = snapshot.child(key).child("deadlinedate").getValue(String.class);
                        String city = snapshot.child(key).child("city").getValue(String.class);
                        if (value.equals("PostTour"))
                        {
                            perhead = snapshot.child(key).child("perhead").getValue(String.class);
                        }else {
                            perhead = snapshot.child(key).child("rent").getValue(String.class);

                        }
                        String phoneNumber = snapshot.child(key).child("phoneNumber").getValue(String.class);
                        String title = snapshot.child(key).child("title").getValue(String.class);
                        String url = snapshot.child(key).child("url").getValue(String.class);
                        String category = snapshot.child(key).child("category").getValue(String.class);


                            PostModelCLass modelCLass = new PostModelCLass(cnic, currentdate, desc,id,item,latitude,loginuser,longitude,perhead,phoneNumber,title,url,deadlineDate,city,category);
                            modelCLassList.add(modelCLass);

                    }
                    adapter = new ViewPostAdapter(modelCLassList, ViewPosts.this);

                    if(modelCLassList.isEmpty())
                    {
                        msg.setVisibility(View.VISIBLE);
                        adapter.notifyDataSetChanged();
                        recyclerView.setVisibility(View.GONE);
                    }
                    else
                    {
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
        Intent intent= new Intent(ViewPosts.this, UserPanel.class);
        startActivity(intent);
        finish();

    }
}