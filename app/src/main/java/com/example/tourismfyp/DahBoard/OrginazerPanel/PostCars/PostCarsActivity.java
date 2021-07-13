package com.example.tourismfyp.DahBoard.OrginazerPanel.PostCars;

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
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
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
import com.example.tourismfyp.DahBoard.OrginazerPanel.OrganizerPanel;
import com.example.tourismfyp.DahBoard.OrginazerPanel.PostHotels.Geolocation;
import com.example.tourismfyp.DahBoard.OrginazerPanel.PostHotels.PostHotelActivity;
import com.example.tourismfyp.DahBoard.OrginazerPanel.PostHotels.UploadRecordHotel;
import com.example.tourismfyp.DahBoard.OrginazerPanel.PostTours.PostToursActivity;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PostCarsActivity extends AppCompatActivity implements OnMapReadyCallback {
    Toolbar toolbar;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    String LoginUserEmail;
    DrawerLayout drawerLayout;
    TextInputEditText city;
    private GoogleMap mMap;
    Button gettbn;
    String item, id, SaveCurrentTime, saveCurrentDate,postdate;
    TextView DateTV;
    private Uri imageURI;
    ImageView imageView;
    CardView image;
    TextInputEditText title, desc, Date,Phonenumber,cnic,Rent;
    Button upload;
    private ProgressDialog loadingbar;
    Spinner PersonCapicity;
    int Year, month, day;
    List<String> list;
    //Date
    String DeadlineDate;
    public int Startday, Startmonth, StartYear;
    String latiude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_cars);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Post");
        SharedPreferences getShared = getApplicationContext().getSharedPreferences("loginName", MODE_PRIVATE);
        LoginUserEmail = getShared.getString("Email", null);

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

                        startActivity(new Intent(getApplicationContext(), PostToursActivity.class));
                        finish();
                        return true;

                    case R.id.postcars:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(getApplicationContext(),PostCarsActivity.class));
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
                        drawerLayout.closeDrawer(GravityCompat.START);

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

        image = findViewById(R.id.product);
        title = findViewById(R.id.carname);
        desc = findViewById(R.id.carDescription);
        upload = findViewById(R.id.Post);
        imageView = findViewById(R.id.image);
        Date = findViewById(R.id.selectlastbookingdate);
        Rent = findViewById(R.id.Rent);
        Phonenumber = findViewById(R.id.Phonenumber);
        cnic = findViewById(R.id.CNIC);
        DateTV = findViewById(R.id.textView3);
        loadingbar = new ProgressDialog(this);
        city = findViewById(R.id.address);
        gettbn = findViewById(R.id.get);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(PostCarsActivity.this);
        gettbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                longitude=null;
                latiude=null;

                String address = city.getText().toString();
                if (address != null) {
                    Geolocation geolocation = new Geolocation();
                    geolocation.getAdress(address, getApplicationContext(), new GeoHandler());

                }else {
                    city.setText("");
                    city.setError("Enter Valid Name");

                }


            }
        });



        //Spinner code
        PersonCapicity = findViewById(R.id.personCapicity);

        list = new ArrayList<>();
        list.add(0, "PersonCapacity");
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

        PersonCapicity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!parent.getItemAtPosition(position).equals("PersonCapacity")) {

                    item = parent.getItemAtPosition(position).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(getApplicationContext(), "please select any one", Toast.LENGTH_SHORT).show();
            }
        });
        PersonCapicity.setAdapter(arr);

        //spineer2


        //DateCode

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        saveCurrentDate = dateFormat.format(calendar.getTime());
        SimpleDateFormat TimeFormat = new SimpleDateFormat("HH:mm:ss a");
        SaveCurrentTime = TimeFormat.format(calendar.getTime());
        id = saveCurrentDate + SaveCurrentTime;
        DateTV.setText("Post Date :\t " + saveCurrentDate.toString());
        postdate = DateTV.getText().toString();
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

                final DatePickerDialog datePickerDialog = new DatePickerDialog(PostCarsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String finalmonth = null;
                        int month1 = month + 1;
                        Startday = (dayOfMonth);
                        Startmonth = (month1);
                        StartYear = year;
                        if (month1<=9)
                        {
                            finalmonth="0"+(month1);
                        }
                        else {
                            finalmonth= String.valueOf(month1);
                        }
                        DeadlineDate = dayOfMonth + "/" + (finalmonth) + "/" + year;

                        if (getday > Startday && getmonth> Startmonth && getyear > StartYear) {

                            Date.setText("");
                            Toast.makeText(PostCarsActivity.this, "You're Selecting the Previous date", Toast.LENGTH_SHORT).show();
                        } else if (getday == Startday && getmonth== Startmonth && getyear == StartYear) {
                            Date.setText("");
                            Toast.makeText(PostCarsActivity.this, "You're Selecting the current date", Toast.LENGTH_SHORT).show();
                        } else if (getday < Startday || (getday>Startday && (getmonth<Startmonth || getyear<StartYear)) || ( getday==Startday && (getmonth<Startmonth || getyear <Startmonth)) ) {

                            if (getyear<=StartYear) {
                                Date.setText(DeadlineDate);
                            }}
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
        String rent = Rent.getText().toString();
        String Cnic = cnic.getText().toString();
        String  contact = Phonenumber.getText().toString();
        String phonePattern="^\\+[0-9]{10,13}$";


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
            Toast.makeText(this, "select a Person Capacity", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Please enter city name", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(Cnic)) {
            cnic.setFocusable(true);
            cnic.setError("Please Enter CNIC");
        }
        else if (TextUtils.isEmpty(contact)) {
            Phonenumber.setFocusable(true);
            Phonenumber.setError("Please Enter Your Number");

        }

        else if (TextUtils.isEmpty(rent)) {
            Rent.setFocusable(true);
            Rent.setError("Please Enter Rent Amount");        }
        else {
            if (longitude!=null && latiude!=null)
            {

                uploadPDFile();
            }else {
                Toast.makeText(this, "Please Set location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadPDFile() {


        final ProgressDialog progressDialog = new ProgressDialog(PostCarsActivity.this);
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
                        String Categoriy="PostCars";
                        UploadRecordHotel uploadrecord = new UploadRecordHotel(title.getText().toString(), url.toString(), desc.getText().toString(), item, id,saveCurrentDate,LoginUserEmail,Phonenumber.getText().toString(),cnic.getText().toString(),Rent.getText().toString(),longitude,latiude,Categoriy,Date.getText().toString(),city.getText().toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(uploadrecord);
                        Toast.makeText(PostCarsActivity.this, "uploading", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        title.setText("");
                        Intent intent = new Intent(getApplicationContext(), OrganizerPanel.class);
                        startActivity(intent);
                        finish();

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

                Toast.makeText(PostCarsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void selectImageFromGallery() {

        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i,"BrowseImage"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {


            imageURI = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageURI);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }


//                uploadPDFile(data.getData());

        }
    }



  /*  private void uploadPDFile() {


        final ProgressDialog progressDialog = new ProgressDialog(PostAssignmentQuiz.this);
        progressDialog.setTitle("Loading");
        progressDialog.show();
        StorageReference reference = storageReference.child("uploads/" + System.currentTimeMillis() + ".pdf");
        reference.putFile(imageURI)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uri.isComplete()) ;
                        Uri url = uri.getResult();
                        pdfload uploadpdf = new pdfload(title.getText().toString(), url.toString(), desc.getText().toString(), item, id,DeadlineDate,saveCurrentDate,LoginUserEmail,categoryOfPost);
                        databaseReference.child(databaseReference.push().getKey()).setValue(uploadpdf);
                        Toast.makeText(PostAssignmentQuiz.this, "uploading", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        title.setText("");
                        Intent intent = new Intent(getApplicationContext(), dashboard.class);
                        startActivity(intent);
                        finish();

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

                Toast.makeText(PostAssignmentQuiz.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/
    class GeoHandler extends Handler implements OnMapReadyCallback {

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

                LatLng sydney = new LatLng(latitud, longitud);
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
        mMap=googleMap;

    }

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(PostCarsActivity.this,OrganizerPanel.class);
        startActivity(intent);
        finish();

    }
}
