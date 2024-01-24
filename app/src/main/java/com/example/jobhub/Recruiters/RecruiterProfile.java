package com.example.jobhub.Recruiters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobhub.Candidates.EditCandidateProfile;
import com.example.jobhub.Candidates.UserProfile;
import com.example.jobhub.Database;
import com.example.jobhub.Login;
import com.example.jobhub.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RecruiterProfile extends AppCompatActivity {

    TextView recruiterprofile_name, recruiterprofile_type_gender, recruiterprofile_email, recruiterprofile_phone, recruiterprofile_about;
    ImageView recruiterbar_jobs, recruiterbar_search, recruiterbar_appliers, recruiterbar_messages, recruiterbar_profile;
    Database db;
    Animation rotateOpen;
    Animation rotateClose;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    Animation fromBottom;
    Animation toBottom;
    FloatingActionButton recruiterprofile_btn, recruiterprofile_btn_edit, recruiterprofile_btn_logout;
    Boolean clicked_btn = false;
    FirebaseAuth auth;
    ShapeableImageView profile_pic;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_profile);
        Intent i = getIntent();
        String user_id = i.getStringExtra("user_id");
        db = new Database(this);
        auth = FirebaseAuth.getInstance();
        recruiterbar_jobs = findViewById(R.id.recruiterbar_jobs);
        recruiterbar_search = findViewById(R.id.recruiterbar_search);
        recruiterbar_appliers = findViewById(R.id.recruiterbar_appliers);
        recruiterbar_messages = findViewById(R.id.recruiterbar_messages);
        recruiterbar_profile = findViewById(R.id.recruiterbar_profile);
        recruiterbar_profile.setImageResource(R.drawable.person_male_svgrepo_filled);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anime);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);
        recruiterprofile_btn = findViewById(R.id.recruiterprofile_btn);
        recruiterprofile_btn_edit = findViewById(R.id.recruiterprofile_btn_edit);
        recruiterprofile_btn_logout = findViewById(R.id.recruiterprofile_btn_logout);

        profile_pic = findViewById(R.id.profile_pic);
        recruiterprofile_name = findViewById(R.id.recruiterprofile_name);
        recruiterprofile_email = findViewById(R.id.recruiterprofile_email);
        recruiterprofile_phone = findViewById(R.id.recruiterprofile_phone);
        recruiterprofile_about = findViewById(R.id.recruiterprofile_about);
        recruiterprofile_type_gender = findViewById(R.id.recruiterprofile_type_gender);
        Toast.makeText(this, "id is= "+user_id, Toast.LENGTH_SHORT).show();


        displayData(user_id);


        recruiterprofile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClicked();
            }
        });
        recruiterprofile_btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecruiterProfile.this, "clicked on edit", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), EditRecruiterProfile.class);
                i.putExtra("user_id", user_id);
                startActivity(i);
            }
        });
        recruiterprofile_btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //auth.signOut();
                Toast.makeText(RecruiterProfile.this, "clicked on logout", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();
            }
        });












        recruiterbar_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIconsToDefault();
                Intent i = new Intent(getApplicationContext(), RecruiterSearch.class);
                i.putExtra("user_id", user_id);
                startActivity(i);
                finish();
            }
        });
        recruiterbar_jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIconsToDefault();
                Intent i = new Intent(getApplicationContext(), RecruiterJobs.class);
                i.putExtra("user_id", user_id);
                startActivity(i);
                finish();
            }
        });
        recruiterbar_messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIconsToDefault();
                Intent i = new Intent(getApplicationContext(), RecruiterMessages.class);
                i.putExtra("user_id", user_id);
                startActivity(i);
                finish();
            }
        });
        recruiterbar_appliers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIconsToDefault();
                Intent i = new Intent(getApplicationContext(), RecruiterCandidats.class);
                i.putExtra("user_id", user_id);
                startActivity(i);
                finish();
            }
        });


    }
    void onButtonClicked(){
        setVisiblity(clicked_btn);
        setAnimation(clicked_btn);
        if (!clicked_btn){
            clicked_btn = true;
        } else {
            clicked_btn = false;
        }
    }
    void setVisiblity(Boolean clicked_btn){
        if (!clicked_btn){
            recruiterprofile_btn_edit.setVisibility(View.VISIBLE);
            recruiterprofile_btn_logout.setVisibility(View.VISIBLE);
            recruiterprofile_btn_edit.setEnabled(true);
            recruiterprofile_btn_logout.setEnabled(true);
        } else {
            recruiterprofile_btn_edit.setVisibility(View.GONE);
            recruiterprofile_btn_logout.setVisibility(View.GONE);
            recruiterprofile_btn_edit.setEnabled(false);
            recruiterprofile_btn_logout.setEnabled(false);
        }
    }
    void setAnimation(Boolean clicked_btn){
        if (!clicked_btn){
            recruiterprofile_btn_edit.startAnimation(fromBottom);
            recruiterprofile_btn_logout.startAnimation(fromBottom);
            recruiterprofile_btn.startAnimation(rotateOpen);
        } else {
            recruiterprofile_btn_edit.startAnimation(toBottom);
            recruiterprofile_btn_logout.startAnimation(toBottom);
            recruiterprofile_btn.startAnimation(rotateClose);
        }
    }
    void displayData(String user_id){
        Cursor cursor = db.displayUserInfo(user_id);
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else{
            while (cursor.moveToNext()){
                String fname = cursor.getString(1);
                String lname  = cursor.getString(2);
                recruiterprofile_name.setText(fname +" "+ lname);
                recruiterprofile_email.setText(cursor.getString(4));
                recruiterprofile_phone.setText(cursor.getString(5));
                recruiterprofile_about.setText(cursor.getString(7));
                String gender = cursor.getString(3);
                recruiterprofile_type_gender.setText("Recruiter • " + gender);
                if (gender.equals("Male")){
                    profile_pic.setImageResource(R.drawable.employee);
                } else{
                    profile_pic.setImageResource(R.drawable.women);
                }
            }
        }
    }
    private void setIconsToDefault() {
        recruiterbar_jobs.setImageResource(R.drawable.work_alt_svgrepo_com);
        recruiterbar_search.setImageResource(R.drawable.search_ic);
        recruiterbar_appliers.setImageResource(R.drawable.people_svgrepo);
        recruiterbar_messages.setImageResource(R.drawable.messages_outline);
        recruiterbar_profile.setImageResource(R.drawable.person_male_svgrepo_com);
    }
    private void status(String status){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);
        reference.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }
}