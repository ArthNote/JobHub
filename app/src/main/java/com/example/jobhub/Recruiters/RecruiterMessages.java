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
import com.example.jobhub.models.Chat;
import com.example.jobhub.models.Job;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class RecruiterMessages extends AppCompatActivity {

    ImageView recruiterbar_jobs, recruiterbar_search, recruiterbar_appliers, recruiterbar_messages, recruiterbar_profile;
    Database db;
    RecruiterChatsAdapter adapter;
    RecyclerView recruiter_chats_recyclerview;
    ArrayList<Chat> chatList = new ArrayList<>();
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_messages);
        Intent i = getIntent();
        String user_id = i.getStringExtra("user_id");
        recruiterbar_jobs = findViewById(R.id.recruiterbar_jobs);
        recruiterbar_search = findViewById(R.id.recruiterbar_search);
        recruiterbar_appliers = findViewById(R.id.recruiterbar_appliers);
        recruiterbar_messages = findViewById(R.id.recruiterbar_messages);
        recruiterbar_profile = findViewById(R.id.recruiterbar_profile);
        recruiterbar_messages.setImageResource(R.drawable.messages_ic);

        recruiter_chats_recyclerview = findViewById(R.id.recruiter_chats_recyclerview);
        db = new Database(this);

        dislayChats(user_id, db);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        adapter = new RecruiterChatsAdapter(RecruiterMessages.this, RecruiterMessages.this, chatList, db, user_id);
        recruiter_chats_recyclerview.setAdapter(adapter);
        recruiter_chats_recyclerview.setLayoutManager(new LinearLayoutManager(RecruiterMessages.this));

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
    void dislayChats(String user_id, Database db){
        Cursor cursor = db.dislayRChats(user_id);
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else{
            while (cursor.moveToNext()){
                chatList.add(new Chat(Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)),
                        Integer.parseInt(cursor.getString(2)),
                        cursor.getString(3),
                        cursor.getString(4)));
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