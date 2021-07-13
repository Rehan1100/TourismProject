package com.example.tourismfyp.DahBoard.AdminPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourismfyp.Authentication.Login_Activity;
import com.example.tourismfyp.DahBoard.AdminPanel.AddNews.AddDailyNews;
import com.example.tourismfyp.DahBoard.AdminPanel.AddOrginizer.AddOrginizers;
import com.example.tourismfyp.DahBoard.AdminPanel.ViewNews.viewNews;
import com.example.tourismfyp.DahBoard.AdminPanel.ViewOrganizer.ViewOragnizer;
import com.example.tourismfyp.R;
import com.google.android.material.navigation.NavigationView;

public class adminPanel extends AppCompatActivity {

    CardView addAdmin,ViewOrginizer,Addnews,ViewNews;
    TextView textView;
    String mytext;
    //Drawer Work
    Toolbar toolbar;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    private String Name;
    private String LoginUserEmail;
    Intent intent2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
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

        textView=findViewById(R.id.textView4);
        textView.setText("Welcome Admin" + " " + Name.toString());
        addAdmin = findViewById(R.id.AddOrginizer);
        ViewOrginizer = findViewById(R.id.ViewOrginizer);
        addAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(adminPanel.this, AddOrginizers.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();



     //           Toast.makeText(adminPanel.this, "Add Orginizer", Toast.LENGTH_SHORT).show();
            }
        });

        ViewOrginizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(adminPanel.this, ViewOragnizer.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();

            }
        });

        Addnews=findViewById(R.id.AddNews);
        Addnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(adminPanel.this, AddDailyNews.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();


            }
        });


        ViewNews=findViewById(R.id.ViewNews);
        ViewNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(adminPanel.this, viewNews.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();


            }
        });
    }

}