package com.example.jobhub.Candidates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.jobhub.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class History extends AppCompatActivity {
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    TabLayout tab_layout;
    ViewPager2 view_pager;
    MyViewPagerAdapter myViewPagerAdapter;
    ImageView navigation_home, navigation_search, navigation_profile, navigation_history, navigation_messages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Intent i = getIntent();
        String user_id = i.getStringExtra("user_id");
        navigation_home = findViewById(R.id.navigation_home);
        navigation_search = findViewById(R.id.navigation_search);
        navigation_profile = findViewById(R.id.navigation_profile);
        navigation_history = findViewById(R.id.navigation_history);
        navigation_messages = findViewById(R.id.navigation_messages);
        navigation_history.setImageResource(R.drawable.bookmarks_ic);
        tab_layout = findViewById(R.id.tab_layout);
        view_pager = findViewById(R.id.view_pager);
        myViewPagerAdapter = new MyViewPagerAdapter(this, user_id);
        view_pager.setAdapter(myViewPagerAdapter);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
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