package com.example.tourismfyp.Authentication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tourismfyp.DahBoard.AdminPanel.adminPanel;
import com.example.tourismfyp.DahBoard.OrginazerPanel.OrganizerPanel;
import com.example.tourismfyp.DahBoard.UserPanel.UserPanel;
import com.example.tourismfyp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login_Activity extends AppCompatActivity {
    TextInputEditText email,password;
    Button LoginBtn,RegisterBtn;
    String key;
    TextView ForgetPassword;

    private ProgressDialog loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        email=findViewById(R.id.SignEmail);
        password=findViewById(R.id.passTb);
        LoginBtn=findViewById(R.id.loginBtn);
        RegisterBtn=findViewById(R.id.RegiesterBtn);
        ForgetPassword=findViewById(R.id.ForgetPass);
        loadingbar = new ProgressDialog(Login_Activity.this);



        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingbar.setTitle("Login");
                loadingbar.setMessage("Please wait for a while...");
                loadingbar.setCanceledOnTouchOutside(false);
                loadingbar.show();
                loginuser(v);


            }
        });
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(getApplicationContext(), SignUp_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });

        ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(getApplicationContext(),ForgetPassActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loginuser(View v) {
        if (!valideUserEmail() | !validePassword()) {
            return;
        } else {

            //Here is the Action
            isUser();


        }
    }

    private boolean valideUserEmail() {

        String val = email.getText().toString();
        if (val.isEmpty()) {
            loadingbar.dismiss();
            email.setError("Field can't be Empty");
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            loadingbar.dismiss();
            email.setError("Please provide valid email");
            email.setFocusable(true);
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private boolean validePassword() {
        String val = password.getText().toString();
        if (val.isEmpty()) {
            password.setError("Password can't be Empty");
            password.setFocusable(true);
            loadingbar.dismiss();
            return false;
        } else {
            password.setError(null);
            return true;
        }

    }

    private void isUser() {


        String UserenterEmail = email.getText().toString();
        String UserenterPassword = password.getText().toString();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users");
        Query query = db.orderByChild("email").equalTo(UserenterEmail);


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    loadingbar.setTitle("Login");
                    loadingbar.setMessage("Please wait while for a while...");
                    loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();


                    for (DataSnapshot ds : snapshot.getChildren()) {
                        key = ds.getKey();
                        Log.d("Tag", key);
                    }
                    String emailFromDb = snapshot.child(key).child("email").getValue(String.class);
                    String name = snapshot.child(key).child("firstName").getValue(String.class);
                    String lastname = snapshot.child(key).child("lastName").getValue(String.class);
                    String nickName = snapshot.child(key).child("nickName").getValue(String.class);
                    String Roll = snapshot.child(key).child("role").getValue(String.class);
                    String PASS = snapshot.child(key).child("password").getValue(String.class);

                    //LoginUserEmail
                    SharedPreferences shrd = getSharedPreferences("loginName", MODE_PRIVATE);
                    SharedPreferences.Editor editor = shrd.edit();
                    editor.putString("Email", emailFromDb);
                    editor.putString("fname", name);
                    editor.putString("lname", lastname);
                    editor.putString("nickName", nickName);
                    editor.putString("Roll", Roll);
                    editor.putString("password", PASS);
                    editor.apply();

                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        loadingbar.dismiss();
                                        if (auth.getCurrentUser().isEmailVerified()) {
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

                                        } else {
                                            Toast.makeText(getApplicationContext(), "Please Verify Your Email Address", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        loadingbar.dismiss();
                                        password.setError("Enter the correct Password");
                                        password.setFocusable(true);
                                        // Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    //                      intent.putExtra("fullpassword", passFromDb);
                    //                    intent.putExtra("genderFromDb", genderFromDb);


                } else {
                    email.setError("No such email exist");
                    email.requestFocus();
                    loadingbar.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getApplicationContext(), "Check your Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });


    }

}
