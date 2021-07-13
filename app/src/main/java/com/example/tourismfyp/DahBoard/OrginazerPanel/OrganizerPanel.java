package com.example.tourismfyp.DahBoard.OrginazerPanel;

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
import com.example.tourismfyp.DahBoard.OrginazerPanel.NotificationPanel.Notification;
import com.example.tourismfyp.DahBoard.OrginazerPanel.PostCars.PostCarsActivity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.PostHotels.PostHotelActivity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.PostTours.PostToursActivity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.ViewPosts.ViewPostActivity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.news.OrganizerNews;
import com.example.tourismfyp.DahBoard.UserPanel.News.UserNews;
import com.example.tourismfyp.DahBoard.UserPanel.UserPanel;
import com.example.tourismfyp.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class OrganizerPanel extends AppCompatActivity {

    CardView PostTours,PostCars,PostHotls, Viewpost;
    TextView textView;

    //Drawer Work
    Toolbar toolbar;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    BackgroundThread bg;
    public TextView notificationtv,newstv;;
    List datanode = new ArrayList();
    List datanodenews = new ArrayList();
    BackgroundThreadNews bgnews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_panel);


        bg= new BackgroundThread(getApplicationContext());
        bg.execute("Booking");

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
                        startActivity(new Intent(getApplicationContext(),OrganizerPanel.class));
                        finish();
                        return true;

                    case R.id.posttour:

                        drawerLayout.closeDrawer(GravityCompat.START);

                        startActivity(new Intent(getApplicationContext(),PostToursActivity.class));
                        finish();

                        return true;

                    case R.id.postcars:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(getApplicationContext(),PostCarsActivity.class));
                        finish();


                        return true;

                    case R.id.posthotels:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(getApplicationContext(),PostHotelActivity.class));

                        finish();

                        return true;

                    case R.id.viewpost:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(getApplicationContext(),ViewPostActivity.class));
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

        PostCars=findViewById(R.id.AddCars);
        PostCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(OrganizerPanel.this, PostCarsActivity.class));
                finish();
            }
        });
        PostHotls=findViewById(R.id.Addhotels);
        PostHotls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrganizerPanel.this, PostHotelActivity.class));
                finish();

            }
        });
        PostTours=findViewById(R.id.AddTour);
        PostTours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrganizerPanel.this, PostToursActivity.class));
                finish();

            }
        });
        Viewpost=findViewById(R.id.ViewPosts);
        Viewpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrganizerPanel.this, ViewPostActivity.class));
                finish();

            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.organizeroptionmenu, menu);
        MenuItem item = menu.findItem(R.id.badge);
        MenuItem item3 = menu.findItem(R.id.newsupdate1);
        MenuItemCompat.setActionView(item, R.layout.bademenulayout);
        MenuItemCompat.setActionView(item3, R.layout.bademenulayoutnews1);
        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);
        RelativeLayout notifCount3 = (RelativeLayout) MenuItemCompat.getActionView(item3);

        notificationtv = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);
        newstv = (TextView) notifCount3.findViewById(R.id.newstextview1);

        notifCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrganizerPanel.this, Notification.class);
                startActivity(intent);
                finish();
            }
        });

        notifCount3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(OrganizerPanel.this, OrganizerNews.class);
                startActivity(intent);
                finish();
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    class BackgroundThread extends AsyncTask<String, Void, String> {

        public Context context1;
        public String key;

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
          this.context1=context;
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
                        {
                            newstv.setVisibility(View.VISIBLE);
                            newstv.setText(datanodenews.size()+"");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            return null;

        }


    }
}


