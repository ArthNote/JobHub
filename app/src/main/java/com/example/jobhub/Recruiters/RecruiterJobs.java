package com.example.jobhub.Recruiters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jobhub.Database;
import com.example.jobhub.R;
import com.example.jobhub.models.Job;
import com.example.jobhub.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class RecruiterJobs extends AppCompatActivity {

    ImageView recruiterbar_jobs, recruiterbar_search, recruiterbar_appliers, recruiterbar_messages, recruiterbar_profile;
    Database db;
    RecruiterJobsAdapter adapter;
    RecyclerView recruiter_jobs_recyclerview;
    FloatingActionButton recruiter_jobs_add_btn;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    ArrayList<Job> jobList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_jobs);
        Intent i = getIntent();
        String user_id = i.getStringExtra("user_id");
        recruiterbar_jobs = findViewById(R.id.recruiterbar_jobs);
        recruiterbar_search = findViewById(R.id.recruiterbar_search);
        recruiterbar_appliers = findViewById(R.id.recruiterbar_appliers);
        recruiterbar_messages = findViewById(R.id.recruiterbar_messages);
        recruiterbar_profile = findViewById(R.id.recruiterbar_profile);
        recruiterbar_jobs.setImageResource(R.drawable.work_ic);
        Toast.makeText(this, "id is= "+user_id, Toast.LENGTH_SHORT).show();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        recruiter_jobs_add_btn = findViewById(R.id.recruiter_jobs_add_btn);
        recruiter_jobs_recyclerview = findViewById(R.id.recruiter_jobs_recyclerview);
        db = new Database(this);

        DisplayAllJobs(user_id);

        adapter = new RecruiterJobsAdapter(RecruiterJobs.this, RecruiterJobs.this, jobList, db);
        recruiter_jobs_recyclerview.setAdapter(adapter);
        recruiter_jobs_recyclerview.setLayoutManager(new LinearLayoutManager(RecruiterJobs.this));


        recruiter_jobs_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddJob.class);
                i.putExtra("user_id", user_id);
                startActivity(i);
            }
        });


        recruiterbar_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIconsToDefault();
                Intent i = new Intent(getApplicationContext(), RecruiterProfile.class);
                i.putExtra("user_id", user_id);
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

    void DisplayAllJobs(String user_id){
        Cursor cursor = db.DisplayAllJobs(user_id);
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else{
            while (cursor.moveToNext()){
                jobList.add(new Job(Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        Integer.parseInt(cursor.getString(7)),
                        cursor.getString(8),
                        Integer.parseInt(cursor.getString(9)),
                        Integer.parseInt(cursor.getString(10)),
                        Integer.parseInt(cursor.getString(11)),
                        Integer.parseInt(cursor.getString(12)),
                        cursor.getString(13),
                        cursor.getString(14),
                        cursor.getString(15),
                        cursor.getString(16),
                        Integer.parseInt(cursor.getString(17)),
                        Integer.parseInt(cursor.getString(18)),
                        cursor.getString(19),
                        cursor.getString(20),
                        cursor.getString(21)));
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

    /*private void status(String status){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);
        reference.updateChildren(hashMap);
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        //status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
       // status("offline");
    }
}