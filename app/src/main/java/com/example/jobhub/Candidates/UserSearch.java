package com.example.jobhub.Candidates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jobhub.Database;
import com.example.jobhub.R;
import com.example.jobhub.Recruiters.RecruiterCandidateSearchAdapter;
import com.example.jobhub.Recruiters.RecruiterSearch;
import com.example.jobhub.models.Job;
import com.example.jobhub.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class UserSearch extends AppCompatActivity {

    DatabaseReference reference;
    FirebaseUser firebaseUser;
    ImageView navigation_home, navigation_search, navigation_profile, navigation_history, navigation_messages;
    Database db;
    SearchView candidate_searchView;
    CandidateJobSearchAdapter adapter;
    RecyclerView candidate_jobs_recycleview;
    ArrayList<Job> jobList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);
        Intent i = getIntent();
        String user_id = i.getStringExtra("user_id");
        navigation_home = findViewById(R.id.navigation_home);
        navigation_search = findViewById(R.id.navigation_search);
        navigation_profile = findViewById(R.id.navigation_profile);
        navigation_history = findViewById(R.id.navigation_history);
        navigation_messages = findViewById(R.id.navigation_messages);
        navigation_search.setImageResource(R.drawable.search_fill);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        candidate_jobs_recycleview = findViewById(R.id.candidate_jobs_recycleview);
        db = new Database(this);
        candidate_searchView = findViewById(R.id.candidate_searchView);

        storeDataInArrays();

        adapter = new CandidateJobSearchAdapter(UserSearch.this, UserSearch.this, jobList, user_id,db);
        candidate_jobs_recycleview.setAdapter(adapter);
        candidate_jobs_recycleview.setLayoutManager(new LinearLayoutManager(UserSearch.this));

        candidate_searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
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

    void storeDataInArrays(){
        Cursor cursor = db.DisplayJobs();
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