package com.example.jobhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobhub.Candidates.UserDashboard;
import com.example.jobhub.Recruiters.RecruiterJobs;
import com.google.android.material.snackbar.Snackbar;

public class SplashScreen extends AppCompatActivity {
    Handler handler;
    TextView splash_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        handler = new Handler();
        Intent i = getIntent();
        splash_txt = findViewById(R.id.splash_txt);
        String user_id = i.getStringExtra("user_id");
        String type = i.getStringExtra("type");
        if (type.equals("Recruiter")){
            splash_txt.setText("LogIn in as a Recruiter..");
        } else{
            splash_txt.setText("LogIn in as a Candidate..");
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(user_id == null){
                    Toast.makeText(SplashScreen.this, "id is null", Toast.LENGTH_SHORT).show();
                } else{
                    if (type.equals("Recruiter")){
                        Intent i = new Intent(getApplicationContext(), RecruiterJobs.class);
                        i.putExtra("user_id", user_id);
                        startActivity(i);
                        finish();
                    } else{
                        Intent i = new Intent(getApplicationContext(), UserDashboard.class);
                        i.putExtra("user_id", user_id);
                        startActivity(i);
                        finish();
                    }
                }
            }
        },3000);
    }
}