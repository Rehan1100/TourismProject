package com.example.tourismfyp.DahBoard.OrginazerPanel.NotificationPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tourismfyp.Authentication.Login_Activity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.OrganizerPanel;
import com.example.tourismfyp.DahBoard.OrginazerPanel.PostCars.PostCarsActivity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.PostHotels.PostHotelActivity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.PostTours.PostToursActivity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.ViewPosts.ViewPostActivity;
import com.example.tourismfyp.DahBoard.UserPanel.Cart.CartActivity;
import com.example.tourismfyp.DahBoard.UserPanel.Cart.CartAdapter;
import com.example.tourismfyp.DahBoard.UserPanel.Cart.CartModelClass;
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

public class Notification extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView msg;

    //Drawer Work
    Toolbar toolbar;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    TextView tv;
    String LoginUserEmail;
    DatabaseReference reference;
    public NotificationAdapter adapter;
    ArrayList<NotificationModelClass> modelCLassList=new ArrayList<>();
    public NotificationModelClass modelCLass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
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
        msg = findViewById(R.id.textView6);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        SharedPreferences getShared = getApplicationContext().getSharedPreferences("loginName", MODE_PRIVATE);
        LoginUserEmail = getShared.getString("Email", null);

        reference = FirebaseDatabase.getInstance().getReference().child("Booking");
        Query query = reference.orderByChild("owner").equalTo(LoginUserEmail);


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
                        if (status.equals("Booked"))
                        {
                        modelCLassList.add(modelCLass);

                        }
                    }
                    adapter = new NotificationAdapter(modelCLassList, Notification.this);
                    if (modelCLassList.isEmpty()) {
                        msg.setVisibility(View.VISIBLE);
                        msg.setGravity(View.TEXT_ALIGNMENT_CENTER);
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
        Intent intent = new Intent(Notification.this, OrganizerPanel.class);
        startActivity(intent);
        finish();

    }
}