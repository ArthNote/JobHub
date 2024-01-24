package com.example.jobhub.Candidates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobhub.Database;
import com.example.jobhub.R;
import com.example.jobhub.Candidates.CandidateChatsAdapter;
import com.example.jobhub.Recruiters.RecruiterChatsAdapter;
import com.example.jobhub.Recruiters.RecruiterMessages;
import com.example.jobhub.models.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class CandidateMessages extends AppCompatActivity {
    ImageView navigation_home, navigation_search, navigation_profile, navigation_history, navigation_messages;
    Database db;
    CandidateChatsAdapter adapter;
    RecyclerView candidate_chats_recyclerview;
    ArrayList<Chat> chatList = new ArrayList<>();
    DatabaseReference reference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_messages);
        Intent i = getIntent();
        String user_id = i.getStringExtra("user_id");
        navigation_home = findViewById(R.id.navigation_home);
        navigation_search = findViewById(R.id.navigation_search);
        navigation_profile = findViewById(R.id.navigation_profile);
        navigation_history = findViewById(R.id.navigation_history);
        navigation_messages = findViewById(R.id.navigation_messages);
        navigation_messages.setImageResource(R.drawable.messages_ic);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        candidate_chats_recyclerview = findViewById(R.id.candidate_chats_recyclerview);
        db = new Database(this);

        dislayChats(user_id, db);

        adapter = new CandidateChatsAdapter(CandidateMessages.this, CandidateMessages.this, chatList, db, user_id);
        candidate_chats_recyclerview.setAdapter(adapter);
        candidate_chats_recyclerview.setLayoutManager(new LinearLayoutManager(CandidateMessages.this));

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
        navigation_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIconsToDefault();
                Intent i = new Intent(getApplicationContext(), UserProfile.class);
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
    }

    void dislayChats(String user_id, Database db){
        Cursor cursor = db.dislayCChats(user_id);
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

    private void status(String status){
        reference =FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
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

    private void setIconsToDefault() {
        navigation_home.setImageResource(R.drawable.home_outline);
        navigation_search.setImageResource(R.drawable.search_ic);
        navigation_history.setImageResource(R.drawable.bookmarks_svgrepo_com);
        navigation_messages.setImageResource(R.drawable.messages_outline);
        navigation_profile.setImageResource(R.drawable.person_male_svgrepo_com);
    }
}