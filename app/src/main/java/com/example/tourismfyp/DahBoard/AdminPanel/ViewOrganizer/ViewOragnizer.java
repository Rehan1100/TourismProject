package com.example.tourismfyp.DahBoard.AdminPanel.ViewOrganizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourismfyp.Authentication.Login_Activity;
import com.example.tourismfyp.DahBoard.AdminPanel.AddOrginizer.AddOrginizers;
import com.example.tourismfyp.DahBoard.AdminPanel.ViewNews.viewNews;
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

public class ViewOragnizer extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;
    DatabaseReference reference;
    Toolbar AdminToolbar;
    NavigationView nav;
    ActionBarDrawerToggle adminToogle;
    DrawerLayout AdminrawerLayout;
    TextView msg;
    Intent intent2;

    ArrayList<ModelCLass> modelCLassList= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_oragnizer);
        recyclerView=findViewById(R.id.cartRecyclerview);
        msg = findViewById(R.id.textView6);

        AdminToolbar = findViewById(R.id.adminToolbar);
        setSupportActionBar(AdminToolbar);
        nav = findViewById(R.id.studentnavMenu);
        AdminrawerLayout = findViewById(R.id.admintDrawer);
        adminToogle = new ActionBarDrawerToggle(this, AdminrawerLayout, AdminToolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        AdminrawerLayout.addDrawerListener(adminToogle);
        adminToogle.syncState();

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);


        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        Query query = reference.orderByChild("role").equalTo("Organizer");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    for (DataSnapshot d : snapshot.getChildren()) {
                        String key = d.getKey();
                       String name = snapshot.child(key).child("firstName").getValue(String.class);
                       String email = snapshot.child(key).child("email").getValue(String.class);
                       String image = snapshot.child(key).child("image").getValue(String.class);
                       String pass = snapshot.child(key).child("password").getValue(String.class);

                        ModelCLass modelCLass = new ModelCLass(name, email, image,pass);
                        modelCLassList.add(modelCLass);
                    }
                    adapter = new Adapter(modelCLassList, ViewOragnizer.this);

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
                        AdminrawerLayout.closeDrawer(GravityCompat.START);
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_option_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.admin_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
             /*   if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }*/
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

        }

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(ViewOragnizer.this,adminPanel.class);
        startActivity(intent);
        finish();
    }
}