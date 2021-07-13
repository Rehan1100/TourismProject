package com.example.tourismfyp.DahBoard.UserPanel.BookingPost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourismfyp.Authentication.Login_Activity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.ViewPosts.Further.Description.DescriptioFragment;

import com.example.tourismfyp.DahBoard.OrginazerPanel.ViewPosts.Further.Details.DetailFragment;
import com.example.tourismfyp.DahBoard.OrginazerPanel.ViewPosts.Further.Locations.LocationFragment;
import com.example.tourismfyp.DahBoard.UserPanel.News.UserNews;
import com.example.tourismfyp.DahBoard.UserPanel.UserPanel;
import com.example.tourismfyp.DahBoard.UserPanel.ViewPosts.ViewPosts;
import com.example.tourismfyp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.Picasso;

import static android.graphics.Color.WHITE;

public class DetailsPostActivity extends AppCompatActivity {
    String cnic;
    String currentdate;
    String desc;
    String id;
    String item;
    String owner;
    String latitude;
    String loginuser;
    String longitude;
    String perhead;
    String phoneNumber;
    String title;
    String url;
    String deadlinedate;
    String category;
    ProgressDialog progressDialog;
    View main;
    ImageView imageView;
    Button descriptionbtn, detailsbtn, locationsbtn, call, AddToCart;
    public String city;
    Toolbar toolbar;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    public String LoginUserEmail, LoginUserFirstName, LoginUserLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nav = findViewById(R.id.navMenu);
        drawerLayout = findViewById(R.id.drawer);
        progressDialog = new ProgressDialog(DetailsPostActivity.this);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(DetailsPostActivity.this,UserPanel.class));
                        finish();
                        return true;

                    case R.id.viewtour:

                        drawerLayout.closeDrawer(GravityCompat.START);

                        startActivity(new Intent(DetailsPostActivity.this, ViewPosts.class).putExtra("value","PostTour"));
                        finish();

                        return true;

                    case R.id.viewcars:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(DetailsPostActivity.this,ViewPosts.class).putExtra("value","PostCars"));
                        finish();


                        return true;

                    case R.id.viewhotels:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(DetailsPostActivity.this,ViewPosts.class).putExtra("value","PostHotel"));

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

        TextView Title = findViewById(R.id.textView2);
        detailsbtn = findViewById(R.id.btn_Details);
        descriptionbtn = findViewById(R.id.btn_Descriptions);
        locationsbtn = findViewById(R.id.btn_Location);
        call = findViewById(R.id.callbtn);
        AddToCart = findViewById(R.id.AddtoCart);
        main = findViewById(R.id.main);
        imageView = findViewById(R.id.imageView2);


        category = getIntent().getStringExtra("category");
        cnic = getIntent().getStringExtra("cnic");
        currentdate = getIntent().getStringExtra("currentdate");
        desc = getIntent().getStringExtra("desc");
        id = getIntent().getStringExtra("id");
        latitude = getIntent().getStringExtra("latitude");
        loginuser = getIntent().getStringExtra("loginuser");
        longitude = getIntent().getStringExtra("longitude");
        perhead = getIntent().getStringExtra("perhead");
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        deadlinedate = getIntent().getStringExtra("lastDate");
        city = getIntent().getStringExtra("city");
        item = getIntent().getStringExtra("item");
        owner = getIntent().getStringExtra("owner");
        id = getIntent().getStringExtra("id");

        Picasso.get().load(url).into(imageView);


        Title.setText(title);
        SharedPreferences getShared = getApplicationContext().getSharedPreferences("loginName", MODE_PRIVATE);
        LoginUserEmail = getShared.getString("Email", null);
        LoginUserFirstName = getShared.getString("fname", null);
        LoginUserLastName = getShared.getString("lname", null);

        if (!loginuser.equals(LoginUserEmail)) {
            call.setEnabled(true);
            call.setBackgroundColor(WHITE);
            AddToCart.setEnabled(true);
        } else {
            call.setEnabled(false);
            AddToCart.setEnabled(false);

        }
        //initilizing the segment button or its fregment;


        Fragment selectFragment = null;
        selectFragment = new DetailFragment(cnic, currentdate, desc, id, latitude, longitude, perhead, phoneNumber, url, title, deadlinedate, city, category, item

        );
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectFragment).commit();

        detailsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectFragment = null;
                selectFragment = new DetailFragment(cnic, currentdate, desc, id, latitude, longitude, perhead, phoneNumber, url, title, deadlinedate, city, category, item);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectFragment).commit();

            }
        });
        descriptionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment selectFragment = null;
                selectFragment = new DescriptioFragment(desc);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectFragment).commit();
            }
        });
        locationsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Fragment selectFragment = null;
                selectFragment = new LocationFragment(longitude, latitude, city);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectFragment).commit();


            }
        });


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + Uri.encode(phoneNumber.trim())));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callIntent);
            }
        });


        AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setTitle("Add To Cart");
                progressDialog.setMessage("Please wait  for a while...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                DatabaseReference db2 = FirebaseDatabase.getInstance().getReference("Cart");
                Query query = db2.orderByChild("id").equalTo(id);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            progressDialog.dismiss();
                            Toast.makeText(DetailsPostActivity.this, "You Already Add this on Cart", Toast.LENGTH_SHORT).show();

                        }
                        if (!snapshot.exists()) {
                            //DataInsert
                            AddToCartClass users = new AddToCartClass(LoginUserFirstName, LoginUserLastName, LoginUserEmail,category,cnic,currentdate,desc,perhead,latitude,longitude,phoneNumber,url,title,deadlinedate,city,item,owner,"Booked",id);

                            db2.push().setValue(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Sucessfully Add on Cart", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }) ;
                        }
                        }

                    @Override
                    public void onCancelled(@NonNull  DatabaseError error) {

                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(DetailsPostActivity.this,UserPanel.class);
        startActivity(intent);
    }
}

