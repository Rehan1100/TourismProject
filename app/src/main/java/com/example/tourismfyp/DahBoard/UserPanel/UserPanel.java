package com.example.tourismfyp.DahBoard.UserPanel;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourismfyp.Authentication.Login_Activity;
import com.example.tourismfyp.DahBoard.UserPanel.Cart.CartActivity;
import com.example.tourismfyp.DahBoard.UserPanel.News.UserNews;
import com.example.tourismfyp.DahBoard.UserPanel.Notification.NotificationUser;
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

public class UserPanel extends AppCompatActivity {

    CardView ViewTours,ViewCars,ViewHotels;
    BackgroundThread bg;
    BackgroundThreadNotification bgNotification;
    BackgroundThreadNews bgnews;
    //Drawer Work
    Toolbar toolbar;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    TextView notificationtv,carttv,newstv;
    List datanode = new ArrayList();
    List datanodenotification = new ArrayList();
    List datanodenews = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panel);
        bg= new BackgroundThread(getApplicationContext());
        bg.execute("Cart");
        bgNotification = new BackgroundThreadNotification(getApplicationContext());
        bgNotification.execute("Booking");
        bgnews = new BackgroundThreadNews(getApplicationContext());
        bgnews.execute("News");



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
                        startActivity(new Intent(getApplicationContext(),UserPanel.class));
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

        ViewCars=findViewById(R.id.AddCars);
        ViewCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),ViewPosts.class).putExtra("value","PostCars"));
                finish();
            }
        });
        ViewHotels=findViewById(R.id.Addhotels);
        ViewHotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ViewPosts.class).putExtra("value","PostHotel"));

                finish();

            }
        });
        ViewTours=findViewById(R.id.AddTour);
        ViewTours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewPosts.class).putExtra("value","PostTour"));
                finish();


            }
        });






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.optionmenu, menu);
        MenuItem item = menu.findItem(R.id.badge);
        MenuItem item2 = menu.findItem(R.id.cart);
        MenuItem item3 = menu.findItem(R.id.newsupdate);
        MenuItemCompat.setActionView(item, R.layout.bademenulayout);
        MenuItemCompat.setActionView(item2, R.layout.badgemenulayoutt);
        MenuItemCompat.setActionView(item3, R.layout.bademenulayoutnews);
        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);
        RelativeLayout notifCount2 = (RelativeLayout) MenuItemCompat.getActionView(item2);
        RelativeLayout notifCount3 = (RelativeLayout) MenuItemCompat.getActionView(item3);
        notificationtv = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);
        carttv = (TextView) notifCount2.findViewById(R.id.actionbarcart);
        newstv = (TextView) notifCount3.findViewById(R.id.newstextview);

        notifCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), NotificationUser.class);
                startActivity(intent);
                finish();
            }
        });
       notifCount2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
                finish();
            }
        });
       notifCount3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), UserNews.class);
                startActivity(intent);
                finish();
            }
        });
        return super.onCreateOptionsMenu(menu);
    }



    class BackgroundThread extends AsyncTask<String, Void, String> {

        private Context context1;
        private String key;

        public BackgroundThread(Context context) {
            this.context1 = context;
        }

        @Override
        protected String doInBackground(String... data) {
            DatabaseReference db;
            db = FirebaseDatabase.getInstance().getReference(data[0]);
            Query query = db.orderByChild("status").equalTo("Booked");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            key = data.getKey();
                            datanodenotification.add(key);
                        }
                        Log.d("Tag", datanodenotification.size()+"");
                        int size=datanodenotification.size();
                        if (size>=99){
                            carttv.setVisibility(View.VISIBLE);
                            carttv.setText("99");
                        }else if (size<=99)
                        {   carttv.setVisibility(View.VISIBLE);
                            carttv.setText(datanodenotification.size()+"");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;

        }


    }
    class BackgroundThreadNotification extends AsyncTask<String, Void, String> {

        private Context context1;
        private String key;

        public BackgroundThreadNotification(Context context) {
            this.context1 = context;
        }

        @Override
        protected String doInBackground(String... data) {
            DatabaseReference db;
            db = FirebaseDatabase.getInstance().getReference(data[0]);
            Query query = db.orderByChild("status").equalTo("Accepted");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            key = data.getKey();
                            datanode.add(key);
                        }
                        Log.d("Tag", datanode.size()+"");
                        int size=datanode.size();
                        if (size>=99){
                            notificationtv.setVisibility(View.VISIBLE);
                            notificationtv.setText("99");
                        }else if (size<=99)
                        {   notificationtv.setVisibility(View.VISIBLE);
                            notificationtv.setText(datanode.size()+"");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;

        }


    }

    class BackgroundThreadNews extends AsyncTask<String, Void, String> {

        private Context context1;
        private String key;

        public BackgroundThreadNews(Context context) {
            this.context1 = context;
        }

        @Override
        protected String doInBackground(String... data) {
            DatabaseReference db;
            db = FirebaseDatabase.getInstance().getReference(data[0]);
            Query query = db.orderByChild("name");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            key = data.getKey();
                            datanodenews.add(key);
                        }
                        Log.d("Tag", datanodenews.size()+"");
                        int size=datanodenews.size();
                        if (size>=99){
                            newstv.setVisibility(View.VISIBLE);
                            newstv.setText("99");
                        }else if (size<=99)
                        {   newstv.setVisibility(View.VISIBLE);
                            newstv.setText(datanodenews.size()+"");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;

        }


    }
}

