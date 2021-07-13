package com.example.tourismfyp.DahBoard.UserPanel.Notification;

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
import com.example.tourismfyp.DahBoard.OrginazerPanel.NotificationPanel.Notification;
import com.example.tourismfyp.DahBoard.OrginazerPanel.NotificationPanel.NotificationModelClass;
import com.example.tourismfyp.DahBoard.UserPanel.Cart.CartActivity;
import com.example.tourismfyp.DahBoard.UserPanel.Cart.CartAdapter;
import com.example.tourismfyp.DahBoard.UserPanel.Cart.CartModelClass;
import com.example.tourismfyp.DahBoard.UserPanel.News.UserNews;
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

public class NotificationUser extends AppCompatActivity {
    RecyclerView recyclerView;
    //Drawer Work
    Toolbar toolbar;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    String LoginUserEmail;
    TextView msg;
    DatabaseReference reference;
    public NotificationUserAdapter adapter;
    ArrayList<NotificationModelClass> modelCLassList=new ArrayList<>();
    public NotificationModelClass modelCLass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_user);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        msg = findViewById(R.id.textView6);
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
                        startActivity(new Intent(getApplicationContext(), UserPanel.class));
                        finish();
                        return true;

                    case R.id.viewtour:

                        drawerLayout.closeDrawer(GravityCompat.START);

                        startActivity(new Intent(getApplicationContext(), ViewPosts.class).putExtra("value", "PostTour"));
                        finish();

                        return true;

                    case R.id.viewcars:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(getApplicationContext(), ViewPosts.class).putExtra("value", "PostCars"));
                        finish();


                        return true;

                    case R.id.viewhotels:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(getApplicationContext(), ViewPosts.class).putExtra("value", "PostHotel"));

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
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        SharedPreferences getShared = getApplicationContext().getSharedPreferences("loginName", MODE_PRIVATE);
        LoginUserEmail = getShared.getString("Email", null);

        reference = FirebaseDatabase.getInstance().getReference().child("Booking");
        Query query = reference.orderByChild("bookinguseremail").equalTo(LoginUserEmail);


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    for (DataSnapshot d : snapshot.getChildren()) {
                        String key = d.getKey();

                        String bookinguseremail = snapshot.child(key).child("bookinguseremail").getValue(String.class);
                        String bookinguserfirstname = snapshot.child(key).child("bookinguserfirstname").getValue(String.class);
                        String bookinguserlastname = snapshot.child(key).child("bookinguserlastname").getValue(String.class);
                        String category = snapshot.child(key).child("category").getValue(String.class);
                        String city = snapshot.child(key).child("city").getValue(String.class);
                        String cnic = snapshot.child(key).child("cnic").getValue(String.class);
                        String currentdate = snapshot.child(key).child("currentdate").getValue(String.class);
                        String desc = snapshot.child(key).child("desc").getValue(String.class);
                        String id = snapshot.child(key).child("id").getValue(String.class);
                        String item = snapshot.child(key).child("item").getValue(String.class);
                        String lastDate = snapshot.child(key).child("lastDate").getValue(String.class);
                        String latitude = snapshot.child(key).child("latitude").getValue(String.class);
                        String longitude = snapshot.child(key).child("longitude").getValue(String.class);
                        String owner = snapshot.child(key).child("owner").getValue(String.class);
                        String phoneNumber = snapshot.child(key).child("phoneNumber").getValue(String.class);
                        String perhead = snapshot.child(key).child("perhead").getValue(String.class);
                        String status = snapshot.child(key).child("status").getValue(String.class);
                        String title = snapshot.child(key).child("title").getValue(String.class);
                        String url = snapshot.child(key).child("url").getValue(String.class);
                        modelCLass = new NotificationModelClass(bookinguserfirstname, bookinguserlastname, bookinguseremail, category, cnic, currentdate, desc,perhead, latitude, longitude, phoneNumber, url, title, lastDate, city, item, owner, status, id);

                        modelCLassList.add(modelCLass);
                    }

                    adapter = new NotificationUserAdapter(modelCLassList, NotificationUser.this);
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
        Intent intent = new Intent(NotificationUser.this, UserPanel.class);
        startActivity(intent);
        finish();

    }
}