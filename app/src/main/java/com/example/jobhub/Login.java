package com.example.jobhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobhub.Recruiters.RecruiterJobs;
import com.example.jobhub.Candidates.UserDashboard;
import com.example.jobhub.models.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    ImageView toolbar_arrow;

    EditText login_email, login_password;
    Button login_btn;
    TextView toolbar_title, login_text;
    Database db;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    TextView unread_msgs;
    private boolean isPasswordVisible = false;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar_arrow = findViewById(R.id.toolbar_arrow);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Log In");
        db = new Database(this);
        auth = FirebaseAuth.getInstance();

        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        login_btn = findViewById(R.id.login_btn);
        login_text = findViewById(R.id.login_text);
        unread_msgs = findViewById(R.id.unread_msgs);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = login_email.getText().toString().trim();
                String password = login_password.getText().toString().trim();

                if(email.equals("") || password.equals("")){
                    Toast.makeText(Login.this, "All fields are empty", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkCombo = db.checkLoginCombo(email, password);
                    login(email, password);
                    if (checkCombo == true){
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        String checkType = db.getUserType(email, password);
                        String user_id = db.getUserId(email);
                        if(user_id == null){
                            Toast.makeText(Login.this, "id is null", Toast.LENGTH_SHORT).show();
                        } else{
                            Intent i = new Intent(getApplicationContext(), SplashScreen.class);
                            i.putExtra("user_id", user_id);
                            i.putExtra("type",checkType);
                            startActivity(i);
                            finish();
                        }
                    } else {
                        Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });









        login_password.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                int drawableEndIndex = 2; // Assuming drawableEnd is at index 2
                Drawable[] drawables = login_password.getCompoundDrawablesRelative();
                Drawable drawableEnd = drawables[drawableEndIndex];

                // Check if the touch is within the bounds of the drawableEnd
                if (event.getRawX() >= (login_password.getRight() - login_password.getPaddingEnd() - drawableEnd.getBounds().width())) {
                    togglePasswordVisibility();
                    return true;
                }
            }
            return false;
        });

        login_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Register.class);
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
    public void login(String email, String pass){
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Toast.makeText(Login.this, "login firebase good", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(Login.this, "login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible;

        // Toggle password visibility using a TransformationMethod
        login_password.setTransformationMethod(isPasswordVisible ? null : new PasswordTransformationMethod());

        // Change the drawableEnd based on password visibility
        Drawable drawable = ContextCompat.getDrawable(this, isPasswordVisible ? R.drawable.lock_open_ic : R.drawable.lock_closed_ic);
        login_password.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null);
    }
}