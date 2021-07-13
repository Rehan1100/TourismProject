package com.example.tourismfyp.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tourismfyp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignUp_Activity extends AppCompatActivity {
    TextInputEditText firstname,lastname,pass,email,nickname;
    Button Register;
    TextView Loginhere;

    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_);
        firstname=findViewById(R.id.SignFirstName);
        lastname=findViewById(R.id.SignLastName);
        pass=findViewById(R.id.SignPass);
        email=findViewById(R.id.SignEmail);
        nickname=findViewById(R.id.SignNickName);
        Register=findViewById(R.id.Register);
        Loginhere =findViewById(R.id.loginhere);

        firstname.addTextChangedListener(CreateTextWatcher);
        lastname.addTextChangedListener(CreateTextWatcher);
        email.addTextChangedListener(CreateTextWatcher);
        pass.addTextChangedListener(CreateTextWatcher);
        nickname.addTextChangedListener(CreateTextWatcher);




        loadingbar = new ProgressDialog(SignUp_Activity.this);


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                SignUpUser(v);

            }
        });
        Loginhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(getApplicationContext(), Login_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();


            }
        });

    }



    private Boolean valideFirstName() {

        String val = firstname.getText().toString();
        //  String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            firstname.setError("First Name Can't be null");
            firstname.setFocusable(true);
            return false;
        }

        else {
            firstname.setError(null);
            return true;
        }
    }


    private Boolean valideLasttName() {

        String val = lastname.getText().toString();
        //  String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            lastname.setError("Last Name Can't be null");
            lastname.setFocusable(true);
            return false;
        }

        else {
            lastname.setError(null);
            return true;
        }
    }

    private Boolean valideNicktName() {

        String val = nickname.getText().toString();
        //  String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            nickname.setError("Nick Name Can't be null");
            nickname.setFocusable(true);
            return false;
        }

        else {
            nickname.setError(null);
            return true;
        }
    }



    private Boolean valideEmailAdress() {

        String val = email.getText().toString();
        //  String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailPattern="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        if (val.isEmpty()) {
            email.setError("Field can't be Empty");
            email.setFocusable(true);
            return false;
        } else if (!val.matches(emailPattern)) {
            email.setError("Thats Not Email Pattern");
            email.setFocusable(true);
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
        {
            email.setError("Please provide valid email");
            email.setFocusable(true);
            return false;

        }
        else {
            email.setError(null);
            return true;
        }
    }


    private Boolean validePassword() {
        String val = pass.getText().toString();
        String password = "^" +
                //   "(?=.*[0-9])" + //atLeast one Digit
                //   "(?=.*[a-z])" + //atleast one letter
                //   "(?=.*[A-Z])" + //atleast one capitalletter
                "(?=.*[a-zA-Z])" + //any letter
                "(?=.*[@#$%^&+=])" + //atleast 1 Speacial character
                //   "(?=\\$+$)" +    //no white space
                ".{4,}" +        //at lest 4 charachter
                "$";
        if (val.isEmpty()) {
            pass.setError("Field can't be Empty");
            return false;
        } else if (!val.matches(password)) {
            pass.setError("Password too weak");
            return false;

        } else {
            pass.setError(null);
            return true;
        }

    }

    public void SignUpUser(View v) {
        if (!valideFirstName() | !validePassword() | !valideEmailAdress() | !valideLasttName() | !valideNicktName()) {

            Register.setEnabled(false);
            loadingbar.dismiss();
            return;
        }
        else {
            Register.setEnabled(true);
            isUser();
        }
    }

 private void CreateAccount() {
        loadingbar.setTitle("SignUp");
        loadingbar.setMessage("Please wait while for a while...");
        loadingbar.setCanceledOnTouchOutside(false);
        loadingbar.show();

        isUser();

    }


    private TextWatcher CreateTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String FirstName = firstname.getText().toString().trim();
            String LastName = lastname.getText().toString().trim();
            String NickName = nickname.getText().toString().trim();
            String Email = email.getText().toString().trim();
            String Pass = pass.getText().toString().trim();
            //Drawable image= profileImage.getDrawable();


            Register.setEnabled(!FirstName.isEmpty() && !Email.isEmpty() && !Pass.isEmpty()&& !LastName.isEmpty()&& !NickName.isEmpty());


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    public void isUser() {
        loadingbar.setTitle("SignIn");
        loadingbar.setMessage("Please wait for Registration");
        loadingbar.setCanceledOnTouchOutside(false);
        loadingbar.show();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference node = db.getReference("Users");

        String fname = firstname.getText().toString();
        String lname = lastname.getText().toString();
        String mail = email.getText().toString();
        String password = pass.getText().toString();
        String nname = nickname.getText().toString();
        String role = "User";

        DatabaseReference db2 = FirebaseDatabase.getInstance().getReference("Users");
        Query query = db2.orderByChild("email").equalTo(mail);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    loadingbar.dismiss();
                    email.setError("This Mail is Already Exist Try Another One");
                    email.setFocusable(true);
                    Register.setEnabled(false);

                }
                if (!snapshot.exists()){


                    //DataInsert
                    Register.setEnabled(true);
                    Users users = new Users(fname, lname, mail, password, nname,role);
                    node.push().setValue(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                        loadingbar.dismiss();
                         Toast.makeText(getApplicationContext(), "Sucessfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            loadingbar.dismiss();
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });



                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseUser user = auth.getCurrentUser();
                    auth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        loadingbar.dismiss();
                                        Toast.makeText(SignUp_Activity.this, "Registered SucessFully", Toast.LENGTH_SHORT).show();

                                        auth.getCurrentUser().sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if (task.isSuccessful()) {
                                                            loadingbar.dismiss();
                                                            Toast.makeText(SignUp_Activity.this, "Registered Sucessfully Please Check Your Email for Registered", Toast.LENGTH_SHORT).show();

                                                            firstname.setText("");
                                                            lastname.setText("");
                                                            email.setText("");
                                                            pass.setText("");
                                                            nickname.setText("");


                                                        } else {

                                                            loadingbar.dismiss();
                                                            Toast.makeText(SignUp_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                                        }
                                                    }
                                                });
                                        //Sucessfully Msg
                                    } else {

                                        loadingbar.dismiss();
                                        Toast.makeText(SignUp_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });




                    Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}
