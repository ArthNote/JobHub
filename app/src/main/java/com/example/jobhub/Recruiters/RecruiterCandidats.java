package com.example.jobhub.Recruiters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.jobhub.Candidates.MyViewPagerAdapter;
import com.example.jobhub.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RecruiterCandidats extends AppCompatActivity {

    ImageView recruiterbar_jobs, recruiterbar_search, recruiterbar_appliers, recruiterbar_messages, recruiterbar_profile;
    TabLayout tab_layout;
    ViewPager2 view_pager;
    RecruiterViewPagerAdapter recruiterViewPagerAdapter;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_candidats);
        Intent i = getIntent();
        String user_id = i.getStringExtra("user_id");
        recruiterbar_jobs = findViewById(R.id.recruiterbar_jobs);
        recruiterbar_search = findViewById(R.id.recruiterbar_search);
        recruiterbar_appliers = findViewById(R.id.recruiterbar_appliers);
        recruiterbar_messages = findViewById(R.id.recruiterbar_messages);
        recruiterbar_profile = findViewById(R.id.recruiterbar_profile);
        recruiterbar_appliers.setImageResource(R.drawable.people_svgrepo_fill);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        tab_layout = findViewById(R.id.tab_layout);
        view_pager = findViewById(R.id.view_pager);
        recruiterViewPagerAdapter = new RecruiterViewPagerAdapter(this, user_id);
        view_pager.setAdapter(recruiterViewPagerAdapter);

        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view_pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        view_pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tab_layout.getTabAt(position).select();
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