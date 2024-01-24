package com.example.jobhub.Recruiters;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jobhub.Database;
import com.example.jobhub.MainActivity;
import com.example.jobhub.R;
import com.example.jobhub.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class RecruiterSearch extends AppCompatActivity {

    ImageView recruiterbar_jobs, recruiterbar_search, recruiterbar_appliers, recruiterbar_messages, recruiterbar_profile;
    Database db;
    SearchView recruiter_searchView;
    RecruiterCandidateSearchAdapter adapter;
    RecyclerView recruiter_candidates_recycleview;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    EditText recruiter_searchField;
    ArrayList<User> userList = new ArrayList<>();
    /*ArrayList<String> candidate_id, candidate_fname, candidate_lname, candidate_gender, candidate_phone,
            candidate_about, candidate_grad, candidate_post_grad, candidate_exp, candidate_specs, candidate_skills;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_search);
        Intent i = getIntent();
        String user_id = i.getStringExtra("user_id");
        recruiterbar_jobs = findViewById(R.id.recruiterbar_jobs);
        recruiterbar_search = findViewById(R.id.recruiterbar_search);
        recruiterbar_appliers = findViewById(R.id.recruiterbar_appliers);
        recruiterbar_messages = findViewById(R.id.recruiterbar_messages);
        recruiterbar_profile = findViewById(R.id.recruiterbar_profile);
        recruiterbar_search.setImageResource(R.drawable.search_fill);
        //recruiter_searchField = findViewById(R.id.recruiter_searchField);
        recruiter_candidates_recycleview = findViewById(R.id.recruiter_candidates_recycleview);
        db = new Database(this);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
/*
        candidate_id = new ArrayList<>();
        candidate_fname = new ArrayList<>();
        candidate_lname = new ArrayList<>();
        candidate_gender = new ArrayList<>();
        candidate_phone = new ArrayList<>();
        candidate_about = new ArrayList<>();
        candidate_grad = new ArrayList<>();
        candidate_post_grad = new ArrayList<>();
        candidate_exp = new ArrayList<>();
        candidate_specs = new ArrayList<>();
        candidate_skills = new ArrayList<>();
*/
        recruiter_searchView = findViewById(R.id.recruiter_searchView);







        storeDataInArrays();

        adapter = new RecruiterCandidateSearchAdapter(RecruiterSearch.this, RecruiterSearch.this, userList, user_id/*candidate_id, candidate_fname, candidate_lname, candidate_gender, candidate_phone,
                candidate_about, candidate_grad, candidate_post_grad, candidate_exp, candidate_specs, candidate_skills*/);
        recruiter_candidates_recycleview.setAdapter(adapter);
        recruiter_candidates_recycleview.setLayoutManager(new LinearLayoutManager(RecruiterSearch.this));


        recruiter_searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = db.DisplayAllCandidates();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else{
            while (cursor.moveToNext()){
                userList.add(new User(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        Integer.parseInt(cursor.getString(9)),
                        Integer.parseInt(cursor.getString(10)),
                        Integer.parseInt(cursor.getString(11)),
                        cursor.getString(12),
                        cursor.getString(13),
                        cursor.getString(14),
                        cursor.getString(14)));
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