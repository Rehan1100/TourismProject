package com.example.tourismfyp.SplashScreens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tourismfyp.DahBoard.AdminPanel.adminPanel;
import com.example.tourismfyp.DahBoard.OrginazerPanel.OrganizerPanel;
import com.example.tourismfyp.DahBoard.UserPanel.UserPanel;
import com.example.tourismfyp.MainActivity;
import com.example.tourismfyp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class frontsplash extends AppCompatActivity {

    Animation topanim,bottomanim,lefttoright,righttoleft;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    ImageView imageView;
    TextView textView2,textView;
    private String email;
    private String pass;
    private String Roll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontsplash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_frontsplash);

        topanim= AnimationUtils.loadAnimation(frontsplash.this,R.anim.top_animation);
        bottomanim= AnimationUtils.loadAnimation(frontsplash.this,R.anim.bottom_animation);
        lefttoright=AnimationUtils.loadAnimation(frontsplash.this,R.anim.left_to_right);
        righttoleft=AnimationUtils.loadAnimation(frontsplash.this,R.anim.righhttoleft);
        //Hooks
        imageView=findViewById(R.id.imageView);
        //Tourism
        textView2=findViewById(R.id.textView);
        textView=findViewById(R.id.text);
        imageView.setAnimation(lefttoright);
        textView2.setAnimation(bottomanim);
        textView.setAnimation(lefttoright);







        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                //On here check the Post that is Valid or not

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String saveCurrentDate = dateFormat.format(calendar.getTime());


                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference node = db.getReference("Post");
                Query query = node.orderByChild("deadlinedate").equalTo(saveCurrentDate);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()) {
                            for (DataSnapshot datas : snapshot.getChildren()) {
                                String key = datas.getKey();
                                node.child(key).removeValue();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                FirebaseDatabase db2 = FirebaseDatabase.getInstance();
                DatabaseReference node2 = db2.getReference("Cart");
                Query query2 = node2.orderByChild("lastDate").equalTo(saveCurrentDate);
                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()) {
                            for (DataSnapshot datas : snapshot.getChildren()) {
                                String key = datas.getKey();
                                node2.child(key).removeValue();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                FirebaseDatabase db3 = FirebaseDatabase.getInstance();
                DatabaseReference node3 = db3.getReference("News");
                Query query3 = node3.orderByChild("deadlinedate").equalTo(saveCurrentDate);
                query3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()) {
                            for (DataSnapshot datas : snapshot.getChildren()) {
                                String key = datas.getKey();
                                node3.child(key).removeValue();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });






                SharedPreferences getShared = getApplicationContext().getSharedPreferences("loginName", MODE_PRIVATE);
                email = getShared.getString("Email", null);
                pass = getShared.getString("password", null);
                Roll = getShared.getString("Roll", null);
                if (email!=null && pass!=null )
                {
                    if (Roll.equals("Organizer")) {
                        Intent intent = new Intent(getApplicationContext(), OrganizerPanel.class);
                        startActivity(intent);
                        finish();

                    } else if (Roll.equals("User")){
                        Intent intent = new Intent(getApplicationContext(), UserPanel.class);
                        startActivity(intent);
                        finish();
                    }else if (Roll.equals("admin")){
                        Intent intent = new Intent(getApplicationContext(), adminPanel.class);
                        startActivity(intent);
                        finish();
                    }


                }else {

                    Intent intent= new Intent(frontsplash.this, MainActivity.class);
                    startActivity(intent);
                    finish();


                }



            }
        },3000);


    }
}