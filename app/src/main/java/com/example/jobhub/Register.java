package com.example.jobhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    ImageView toolbar_arrow;
    Drawable[] password_lock;
    Drawable lock;
    EditText register_fname, register_lname, register_email, register_password, register_phone, register_about;
    Button register_btn;
    TextView register_text, toolbar_title;
    RadioGroup register_group, register_type;
    RadioButton selected_gender, selected_type;
    Database db;
    private boolean isPasswordVisible = false;
    private ProgressDialog progressDialog;

    FirebaseAuth auth;
    DatabaseReference reference;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar_arrow = findViewById(R.id.toolbar_arrow);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Register");

        auth = FirebaseAuth.getInstance();

        register_fname = findViewById(R.id.register_fname);
        register_lname = findViewById(R.id.register_lname);
        register_email = findViewById(R.id.register_email);
        register_password = findViewById(R.id.register_password);
        register_phone = findViewById(R.id.register_phone);
        register_about = findViewById(R.id.register_about);
        register_btn = findViewById(R.id.register_btn);
        register_text = findViewById(R.id.register_text);
        register_group = findViewById(R.id.register_gender);
        register_type = findViewById(R.id.register_type);
        progressDialog=new ProgressDialog(this);



        db = new Database(this);


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = register_email.getText().toString().trim();
                String password = register_password.getText().toString().trim();
                String fname = register_fname.getText().toString().trim();
                String lname = register_lname.getText().toString().trim();
                String phone = register_phone.getText().toString().trim();
                String about = register_about.getText().toString().trim();
                register(email, password, db, fname, lname, phone, about);
            }
        });

        register_password.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                int drawableEndIndex = 2; // Assuming drawableEnd is at index 2
                Drawable[] drawables = register_password.getCompoundDrawablesRelative();
                Drawable drawableEnd = drawables[drawableEndIndex];

                // Check if the touch is within the bounds of the drawableEnd
                if (event.getRawX() >= (register_password.getRight() - register_password.getPaddingEnd() - drawableEnd.getBounds().width())) {
                    togglePasswordVisibility();
                    return true;
                }
            }
            return false;
        });









        register_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();
            }
        });
        toolbar_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    public void register(String email, String pass, Database db, String fname, String lname,String phone, String about){
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    assert firebaseUser != null;
                    String userId = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", userId);

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Register.this, "firebase register successfull", Toast.LENGTH_SHORT).show();
                                String skills  = "";
                                int grad = 0;
                                int post_grad = 0;
                                String specs = "";
                                int exp = 0;


                                int gender_id = register_group.getCheckedRadioButtonId();
                                int type_id = register_type.getCheckedRadioButtonId();

                                if (fname.equals("") || lname.equals("") || email.equals("") || pass.equals("") || phone.equals("") || about.equals("") || gender_id ==-1 || type_id ==-1 ){
                                    Toast.makeText(Register.this, "All fields are Empty", Toast.LENGTH_SHORT).show();
                                } else{
                                    selected_gender = findViewById(gender_id);
                                    String gender = selected_gender.getText().toString().trim();
                                    selected_type = findViewById(type_id);
                                    String type = selected_type.getText().toString().trim();

                                    Boolean checkEmail = db.checkEmail(email);
                                    if (checkEmail == false){
                                        Boolean insert = db.register(fname, lname, gender, email, phone,
                                                pass, about, type, grad, post_grad, exp, specs, skills, userId);
                                        if (insert == true){
                                            //progressDialog.dismiss();
                                            Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                            Toast.makeText(Register.this, userId, Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getApplicationContext(), Login.class);
                                            startActivity(i);
                                            finish();
                                        } else {
                                            Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    } else{
                                        Toast.makeText(Register.this, "Email Already Exists!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                } else{
                    Toast.makeText(Register.this, "firebase register not successfull", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible;

        // Toggle password visibility using a TransformationMethod
        register_password.setTransformationMethod(isPasswordVisible ? null : new PasswordTransformationMethod());

        // Change the drawableEnd based on password visibility
        Drawable drawable = ContextCompat.getDrawable(this, isPasswordVisible ? R.drawable.lock_open_ic : R.drawable.lock_closed_ic);
        register_password.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null);
    }
}