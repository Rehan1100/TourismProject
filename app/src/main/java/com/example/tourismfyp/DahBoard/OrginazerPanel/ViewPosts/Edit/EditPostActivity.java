package com.example.tourismfyp.DahBoard.OrginazerPanel.ViewPosts.Edit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourismfyp.Authentication.Login_Activity;
import com.example.tourismfyp.DahBoard.AdminPanel.AddNews.UploadNews;
import com.example.tourismfyp.DahBoard.AdminPanel.ViewNews.EditNews.EditNews;
import com.example.tourismfyp.DahBoard.AdminPanel.ViewNews.viewNews;
import com.example.tourismfyp.DahBoard.OrginazerPanel.OrganizerPanel;
import com.example.tourismfyp.DahBoard.OrginazerPanel.PostCars.PostCarsActivity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.PostHotels.Geolocation;
import com.example.tourismfyp.DahBoard.OrginazerPanel.PostHotels.PostHotelActivity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.PostTours.PostToursActivity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.PostTours.UploadRecord;
import com.example.tourismfyp.DahBoard.OrginazerPanel.ViewPosts.Further.ViewAllOnCategory;
import com.example.tourismfyp.DahBoard.OrginazerPanel.ViewPosts.ViewPostActivity;
import com.example.tourismfyp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditPostActivity extends AppCompatActivity implements OnMapReadyCallback {

    Toolbar toolbar;
    NavigationView nav;
    ImageView camera;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    TextInputEditText city;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    private GoogleMap mMap;
    Button gettbn;
    String item, id, SaveCurrentTime, saveCurrentDate, postdate;
    TextView DateTV;
    public Uri imageURI;
    ImageView imageView;
    CardView image;
    TextInputEditText title, desc, Date, Phonenumber, cnic, Perhead;
    Button upload;
    private ProgressDialog loadingbar;
    Spinner TourDay;
    int Year, month, day;
    List<String> list;
    //Date
    String DeadlineDate;
    public int Startday, Startmonth, StartYear;
    String latiude, longitude;
    private String LoginUserEmail;
    String Categoriy;
    String valueCheck;
    public String latitudeOfPost;
    public String longitudeOfPost;
    public String cityOfPost;
    Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String CategoryOfPost = getIntent().getStringExtra("category");
        String CnicOfPost = getIntent().getStringExtra("cnic");
        String CurrentDateOfPost = getIntent().getStringExtra("currentdate");
        String DescOfPost = getIntent().getStringExtra("desc");
        String IdOfPost = getIntent().getStringExtra("id");
        String ItemOfPost = getIntent().getStringExtra("item");
        latitudeOfPost = getIntent().getStringExtra("latitude");
        String loginuserOfPost = getIntent().getStringExtra("loginuser");
        longitudeOfPost = getIntent().getStringExtra("longitude");
        String perheadOfPost = getIntent().getStringExtra("perhead");
        String phoneNumberOfPost = getIntent().getStringExtra("phoneNumber");
        String urlOfPost = getIntent().getStringExtra("url");
        String titleOfPost = getIntent().getStringExtra("title");
        String lastDateOfPost = getIntent().getStringExtra("lastDate");
        cityOfPost = getIntent().getStringExtra("city");

        Categoriy = CategoryOfPost;
        id=IdOfPost;
        setContentView(R.layout.activity_edit_post2);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (CategoryOfPost.equals("PostHotel")) {
            toolbar.setTitle("Hotel Post Edit");
            valueCheck = "PersonCapacity";
        } else if (CategoryOfPost.equals("PostCars")) {
            toolbar.setTitle("Car Post Edit");
            valueCheck = "PersonCapacity";

        } else if (CategoryOfPost.equals("PostTour")) {
            toolbar.setTitle("Tour Post Edit");
            valueCheck = "TourDays";
        }
        nav = findViewById(R.id.navMenu);
        drawerLayout = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        SharedPreferences getShared = getApplicationContext().getSharedPreferences("loginName", MODE_PRIVATE);
        LoginUserEmail = getShared.getString("Email", null);

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
        Geolocation geolocation = new Geolocation();
        geolocation.getAdress(cityOfPost, getApplicationContext(), new GeoHandler());

        image = findViewById(R.id.product);
        title = findViewById(R.id.placeName);
        title.setText(titleOfPost);
        desc = findViewById(R.id.hotelDescription);
        desc.setText(DescOfPost);
        upload = findViewById(R.id.Post);
        imageView = findViewById(R.id.image);
        Picasso.get().load(urlOfPost).into(imageView);
        Date = findViewById(R.id.selectlastdate);
        Date.setText(lastDateOfPost);
        Perhead = findViewById(R.id.Perhead);
        Perhead.setText(perheadOfPost);
        Phonenumber = findViewById(R.id.Phonenumber);
        Phonenumber.setText(phoneNumberOfPost);
        cnic = findViewById(R.id.CNIC);
        cnic.setText(CnicOfPost);
        DateTV = findViewById(R.id.textView3);
        DateTV.setText(CurrentDateOfPost);
        loadingbar = new ProgressDialog(this);
        city = findViewById(R.id.address);
        city.setText(cityOfPost);
        gettbn = findViewById(R.id.get);
        camera = findViewById(R.id.camera);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Post");
         query = databaseReference.orderByChild("id").equalTo(id);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(EditPostActivity.this);

        gettbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMap.clear();
                longitude = null;
                latiude = null;

                String address = city.getText().toString();
                if (address != null) {
                    Geolocation geolocation = new Geolocation();
                    geolocation.getAdress(address, getApplicationContext(), new GeoHandler());

                } else {
                    city.setText("");
                    city.setError("Enter Valid Name");

                }


            }
        });


        //Spinner code
        TourDay = findViewById(R.id.tourday);


        list = new ArrayList<>();
        list.add(0, valueCheck);
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");


        final ArrayAdapter<String> arr = new ArrayAdapter<String>(this, R.layout.spinner, list);

        arr.setDropDownViewResource(R.layout.spinner);

        TourDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!parent.getItemAtPosition(position).equals("TourDays")) {

                    item = parent.getItemAtPosition(position).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(getApplicationContext(), "please select any one", Toast.LENGTH_SHORT).show();
            }
        });
        TourDay.setAdapter(arr);

        //spineer2


        //DateCode

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        saveCurrentDate = dateFormat.format(calendar.getTime());
        SimpleDateFormat TimeFormat = new SimpleDateFormat("HH:mm:ss a");
        SaveCurrentTime = TimeFormat.format(calendar.getTime());

        DateTV.setText("Post Date :\t " + CurrentDateOfPost);

        postdate = CurrentDateOfPost;
        String[] dateParts = saveCurrentDate.split("/");
        int getday = Integer.parseInt(dateParts[0]);
        int getmonth = Integer.parseInt(dateParts[1]);
        int getyear = Integer.parseInt(dateParts[2]);

        final Calendar lastCalender = Calendar.getInstance();
        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Year = lastCalender.get(Calendar.YEAR);
                month = lastCalender.get(Calendar.MONTH);
                day = lastCalender.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog datePickerDialog = new DatePickerDialog(EditPostActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String finalmonth = null;
                        int month1 = month + 1;
                        Startday = (dayOfMonth);
                        Startmonth = (month1);
                        StartYear = year;
                        if (month1 <= 9) {
                            finalmonth = "0" + (month1);
                        } else {
                            finalmonth = String.valueOf(month1);
                        }
                        DeadlineDate = dayOfMonth + "/" + (finalmonth) + "/" + year;

                        if (getday > Startday && getmonth > Startmonth && getyear > StartYear) {

                            Date.setText("");
                            Toast.makeText(EditPostActivity.this, "You're Selecting the Previous date", Toast.LENGTH_SHORT).show();
                        } else if (getday == Startday && getmonth == Startmonth && getyear == StartYear) {
                            Date.setText("");
                            Toast.makeText(EditPostActivity.this, "You're Selecting the current date", Toast.LENGTH_SHORT).show();
                        } else if (getday < Startday || (getday > Startday && (getmonth < Startmonth || getyear < StartYear)) || (getday == Startday && (getmonth < Startmonth || getyear < Startmonth))) {

                            if (getyear <= StartYear) {
                                Date.setText(DeadlineDate);
                            }
                        }
                    }
                }, Year, month, day);
                datePickerDialog.show();
            }
        });
       /* storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");*/
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageFromGallery();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                post();
            }
        });
    }

    public void post() {
        vaildateProduct();
    }

    public void vaildateProduct() {
        String name = title.getText().toString();
        String description = desc.getText().toString();
        String finaldate = Date.getText().toString();
        String address = city.getText().toString();
        String perhead = Perhead.getText().toString();
        String Cnic = cnic.getText().toString();
        String contact = Phonenumber.getText().toString();
        String phonePattern = "^\\+[0-9]{10,13}$";


        if (imageURI == null) {
            Toast.makeText(this, "image is Menidatry...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(name)) {
            title.setError("Please write hotel name");
            title.setFocusable(true);
        } else if (TextUtils.isEmpty(description)) {
            desc.setError("Please hotel description");
            desc.setFocusable(true);
        } else if (TextUtils.isEmpty(finaldate)) {
            Date.setError("Select Date");
            Date.setFocusable(true);
        } else if (TextUtils.isEmpty(item)) {

            Toast.makeText(this, "Please Select ToursDay", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Please enter city name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Cnic)) {
            cnic.setFocusable(true);
            cnic.setError("Please Enter CNIC");
        } else if (TextUtils.isEmpty(contact)) {
            Phonenumber.setFocusable(true);
            Phonenumber.setError("Please Enter Your Number");

        } else if (TextUtils.isEmpty(perhead)) {
            Perhead.setFocusable(true);
            Perhead.setError("Please Enter PerHead Amount");
        } else {
            if (longitude != null && latiude != null) {

                uploadPDFile();
            } else {
                Toast.makeText(this, "Please Set location", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void selectImageFromGallery() {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                imageURI = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageURI);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
                camera.setVisibility(View.GONE);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(EditPostActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(EditPostActivity.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }


    private void uploadPDFile() {


        final ProgressDialog progressDialog = new ProgressDialog(EditPostActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Post is Uploadin");
        progressDialog.show();
        StorageReference reference = storageReference.child("Post/" + System.currentTimeMillis() + ".jgp");
        reference.putFile(imageURI)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uri.isComplete()) ;
                        Uri url = uri.getResult();


                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if (snapshot.exists()) {
                                    for (DataSnapshot datas : snapshot.getChildren()) {
                                        String key = datas.getKey();

                                        UploadRecord uploadrecord = new UploadRecord(title.getText().toString(), url.toString(), desc.getText().toString(), item, id, DeadlineDate, postdate, LoginUserEmail, Phonenumber.getText().toString(), cnic.getText().toString(), Perhead.getText().toString(), longitude, latiude, Categoriy, Date.getText().toString(), city.getText().toString());
                                        databaseReference.child(key).setValue(uploadrecord);
                                        Toast.makeText(EditPostActivity.this, "uploading", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                        title.setText("");
                                        Intent intent = new Intent(getApplicationContext(), OrganizerPanel.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded: " + (int) progress + "%");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(EditPostActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    class GeoHandler extends Handler implements OnMapReadyCallback {

        LatLng sydney;

        @Override
        public void handleMessage(@NonNull Message msg) {


            switch (msg.what) {
                case 1:
                    Bundle bundle = msg.getData();
                    latiude = bundle.getString("lati");
                    longitude = bundle.getString("longi");


                    break;
                default:
                    latiude = null;
                    longitude = null;
            }

            if (latiude != null || longitude != null) {


                float latitud = Float.parseFloat(latiude);
                float longitud = Float.parseFloat(longitude);

                sydney = new LatLng(latitud, longitud);
                mMap.addMarker(new MarkerOptions()
                        .position(sydney)
                        .title(city.getText().toString()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            }
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditPostActivity.this, OrganizerPanel.class);
        startActivity(intent);
        finish();

    }
}