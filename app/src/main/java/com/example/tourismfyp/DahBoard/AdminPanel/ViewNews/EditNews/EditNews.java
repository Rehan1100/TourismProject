package com.example.tourismfyp.DahBoard.AdminPanel.ViewNews.EditNews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tourismfyp.Authentication.Login_Activity;
import com.example.tourismfyp.DahBoard.AdminPanel.AddNews.AddDailyNews;
import com.example.tourismfyp.DahBoard.AdminPanel.AddNews.UploadNews;
import com.example.tourismfyp.DahBoard.AdminPanel.AddOrginizer.AddOrginizers;
import com.example.tourismfyp.DahBoard.AdminPanel.ViewNews.viewNews;
import com.example.tourismfyp.DahBoard.AdminPanel.ViewOrganizer.ViewOragnizer;
import com.example.tourismfyp.DahBoard.AdminPanel.adminPanel;
import com.example.tourismfyp.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditNews extends AppCompatActivity {

    TextInputEditText news;
    Intent intent2;
    Toolbar toolbar;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    private String LoginUserEmail;
    private String Name;
    Button Post;
    String  id, SaveCurrentTime, saveCurrentDate;
    String deadlinedate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_news);
        news=findViewById(R.id.news);
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
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("News");

        SharedPreferences getShared = getApplicationContext().getSharedPreferences("loginName", MODE_PRIVATE);
        LoginUserEmail = getShared.getString("Email", null);
        Name=getShared.getString("fname",null);

        Post=findViewById(R.id.post);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        saveCurrentDate = dateFormat.format(calendar.getTime());
        SimpleDateFormat TimeFormat = new SimpleDateFormat("HH:mm:ss a");
        SaveCurrentTime = TimeFormat.format(calendar.getTime());


        id= getIntent().getStringExtra("id");
        news.setText(getIntent().getStringExtra("news"));
        saveCurrentDate=getIntent().getStringExtra("postDate");
        SaveCurrentTime=getIntent().getStringExtra("postTime");

        String data[]=saveCurrentDate.split("/");
        String day=data[0];
        String month=data[1];
        String year=data[2];

        int finaldate=Integer.parseInt(day);
        finaldate=finaldate+1;

        deadlinedate=String.valueOf(finaldate)+"/"+month+"/"+year;

        Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (news.getText().toString().isEmpty())
                {
                    Toast.makeText(EditNews.this, "Empty Post is not Allowed", Toast.LENGTH_SHORT).show();
                }
                else {
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference node = db.getReference("News");
                    Query query = node.orderByChild("id").equalTo(id);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {
                                for (DataSnapshot datas : snapshot.getChildren()) {
                                    String key = datas.getKey();

                                    UploadNews uploadNews= new UploadNews(news.getText().toString(),LoginUserEmail,Name,id,saveCurrentDate,SaveCurrentTime,deadlinedate);
                                    node.child(key).setValue(uploadNews);
                                    Toast.makeText(EditNews.this, "your post is Sucessfully updated", Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(EditNews.this,viewNews.class));
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(EditNews.this, viewNews.class);
        startActivity(intent);
        finish();
    }
    }