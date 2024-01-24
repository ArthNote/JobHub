package com.example.jobhub.Candidates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

public class UserProfile extends AppCompatActivity {
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    TextView userprofile_name, userprofile_email
            ,userprofile_phone, userprofile_about, userprofile_grad, userprofile_pgrad
            , userprofile_exp, userprofile_specs_gender, userprofile_skills;
    LinearLayout userprofile_about_expand, userprofile_details, userprofile_skills_expand, userprofile_details_expand;
    ImageView navigation_home, navigation_search, navigation_profile, navigation_history, navigation_messages;
    Animation rotateOpen;
    Animation rotateClose;
    Animation fromBottom;
    Animation toBottom;
    FloatingActionButton userprofile_btn, userprofile_btn_edit, userprofile_btn_logout;
    Database db;
    Boolean clicked_btn = false;
    Boolean aboutvisible = false;
    Boolean detailsvisible = false;
    Boolean skillsvisible = false;
    FirebaseAuth auth;

    ShapeableImageView profile_pic;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Intent i = getIntent();
        String user_id = i.getStringExtra("user_id");
        db = new Database(this);
        auth = FirebaseAuth.getInstance();

        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anime);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);
        userprofile_btn = findViewById(R.id.userprofile_btn);
        userprofile_btn_edit = findViewById(R.id.userprofile_btn_edit);
        userprofile_btn_logout = findViewById(R.id.userprofile_btn_logout);
        profile_pic = findViewById(R.id.profile_pic);

        userprofile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClicked();
            }
        });
        userprofile_btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserProfile.this, "clicked on edit", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), EditCandidateProfile.class);
                i.putExtra("user_id", user_id);
                startActivity(i);
            }
        });
        userprofile_btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserProfile.this, "clicked on logout", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();
            }
        });

        navigation_home = findViewById(R.id.navigation_home);
        navigation_search = findViewById(R.id.navigation_search);
        navigation_profile = findViewById(R.id.navigation_profile);
        navigation_history = findViewById(R.id.navigation_history);
        navigation_messages = findViewById(R.id.navigation_messages);
        navigation_profile.setImageResource(R.drawable.person_male_svgrepo_filled);

        userprofile_name = findViewById(R.id.userprofile_name);
        userprofile_email = findViewById(R.id.userprofile_email);
        userprofile_phone = findViewById(R.id.userprofile_phone);
        userprofile_about = findViewById(R.id.userprofile_about);
        userprofile_grad = findViewById(R.id.userprofile_grad);
        userprofile_pgrad = findViewById(R.id.userprofile_pgrad);
        userprofile_exp = findViewById(R.id.userprofile_exp);
        userprofile_specs_gender = findViewById(R.id.userprofile_specs_gender);
        userprofile_skills = findViewById(R.id.userprofile_skills);
        userprofile_about_expand = findViewById(R.id.userprofile_about_expand);
        userprofile_details = findViewById(R.id.userprofile_details);
        userprofile_details_expand = findViewById(R.id.userprofile_details_expand);
        userprofile_skills_expand = findViewById(R.id.userprofile_skills_expand);
        Toast.makeText(this, "id is= "+user_id, Toast.LENGTH_SHORT).show();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        displayData(user_id);

        userprofile_about_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAboutclicked();
            }
        });
        userprofile_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDetailsclicked();
            }
        });
        userprofile_skills_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSkillsclicked();
            }
        });


        navigation_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIconsToDefault();
                Intent i = new Intent(getApplicationContext(), UserDashboard.class);
                i.putExtra("user_id", user_id);
                startActivity(i);
                finish();
            }
        });
        navigation_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIconsToDefault();
                Intent i = new Intent(getApplicationContext(), UserSearch.class);
                i.putExtra("user_id", user_id);
                startActivity(i);
                finish();
            }
        });
        navigation_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIconsToDefault();
                Intent i = new Intent(getApplicationContext(), History.class);
                i.putExtra("user_id", user_id);
                startActivity(i);
                finish();
            }
        });
        navigation_messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIconsToDefault();
                Intent i = new Intent(getApplicationContext(), CandidateMessages.class);
                i.putExtra("user_id", user_id);
                startActivity(i);
                finish();
            }
        });


    }

    void setAboutclicked(){
        setAboutvisible(aboutvisible);
        if (!aboutvisible){
            aboutvisible = true;
        } else {
            aboutvisible = false;
        }
    }
    void setAboutvisible(Boolean aboutvisible){
        if (!aboutvisible){
            userprofile_about.setVisibility(View.VISIBLE);
        } else {
            userprofile_about.setVisibility(View.GONE);
        }
    }
    void setDetailsclicked(){
        setDetailsvisible(detailsvisible);
        if (!detailsvisible){
            detailsvisible = true;
        } else {
            detailsvisible = false;
        }
    }
    void setDetailsvisible(Boolean detailsvisible){
        if (!detailsvisible){
            userprofile_details_expand.setVisibility(View.VISIBLE);
        } else {
            userprofile_details_expand.setVisibility(View.GONE);
        }
    }
    void setSkillsclicked(){
        setSkillsvisible(skillsvisible);
        if (!skillsvisible){
            skillsvisible = true;
        } else {
            skillsvisible = false;
        }
    }
    void setSkillsvisible(Boolean skillsvisible){
        if (!skillsvisible){
            userprofile_skills.setVisibility(View.VISIBLE);
        } else {
            userprofile_skills.setVisibility(View.GONE);
        }
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
            userprofile_btn_edit.setVisibility(View.VISIBLE);
            userprofile_btn_logout.setVisibility(View.VISIBLE);
            userprofile_btn_edit.setEnabled(true);
            userprofile_btn_logout.setEnabled(true);
        } else {
            userprofile_btn_edit.setVisibility(View.GONE);
            userprofile_btn_logout.setVisibility(View.GONE);
            userprofile_btn_edit.setEnabled(false);
            userprofile_btn_logout.setEnabled(false);
        }
    }
    void setAnimation(Boolean clicked_btn){
        if (!clicked_btn){
            userprofile_btn_edit.startAnimation(fromBottom);
            userprofile_btn_logout.startAnimation(fromBottom);
            userprofile_btn.startAnimation(rotateOpen);
        } else {
            userprofile_btn_edit.startAnimation(toBottom);
            userprofile_btn_logout.startAnimation(toBottom);
            userprofile_btn.startAnimation(rotateClose);
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
                userprofile_name.setText(fname +" "+ lname);
                userprofile_email.setText(cursor.getString(4));
                userprofile_phone.setText(cursor.getString(5));
                userprofile_about.setText(cursor.getString(7));
                userprofile_grad.setText(cursor.getInt(9) + "% Graduation"); // Convert integer to string
                userprofile_pgrad.setText(cursor.getInt(10) + "% Post Graduation"); // Convert integer to string
                userprofile_exp.setText(cursor.getInt(11) + " years experience");
                String specs = cursor.getString(12);
                userprofile_skills.setText(cursor.getString(13));
                String gender = cursor.getString(3);
                userprofile_specs_gender.setText(specs + " • "+ gender);
                if (gender.equals("Male")){
                    profile_pic.setImageResource(R.drawable.employee);
                } else{
                    profile_pic.setImageResource(R.drawable.women);
                }
            }
        }
    }
    private void setIconsToDefault() {
        navigation_home.setImageResource(R.drawable.home_outline);
        navigation_search.setImageResource(R.drawable.search_ic);
        navigation_history.setImageResource(R.drawable.bookmarks_svgrepo_com);
        navigation_messages.setImageResource(R.drawable.messages_outline);
        navigation_profile.setImageResource(R.drawable.person_male_svgrepo_com);
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