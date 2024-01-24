package com.example.jobhub.Candidates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobhub.Database;
import com.example.jobhub.R;
import com.example.jobhub.models.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UserDashboard extends AppCompatActivity {
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    ImageView navigation_home, navigation_search, navigation_profile, navigation_history, navigation_messages;
    Button userdashboard_button;
    TextView userdashboard_uname, viewscount, applicationscount,unread_msgs;
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        Intent i = getIntent();
        String user_id = i.getStringExtra("user_id");
        navigation_home = findViewById(R.id.navigation_home);
        navigation_search = findViewById(R.id.navigation_search);
        navigation_profile = findViewById(R.id.navigation_profile);
        navigation_history = findViewById(R.id.navigation_history);
        navigation_messages = findViewById(R.id.navigation_messages);
        navigation_home.setImageResource(R.drawable.home_ic);
        unread_msgs = findViewById(R.id.unread_msgs);
        Toast.makeText(this, "id = "+user_id, Toast.LENGTH_SHORT).show();

        db = new Database(this);
        applicationscount = findViewById(R.id.applicationscount);
        viewscount = findViewById(R.id.viewscount);
        userdashboard_button = findViewById(R.id.userdashboard_button);
        userdashboard_uname = findViewById(R.id.userdashboard_uname);

        int viewcount = db.DisplayViews(user_id);
        viewscount.setText(String.valueOf(viewcount));

        int applicationcount = db.DisplayApplicationCount(user_id);
        applicationscount.setText(String.valueOf(applicationcount));

        userdashboard_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), UserSearch.class);
                i.putExtra("user_id", user_id);
                startActivity(i);
                finish();
            }
        });

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /*
                int unread = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Message msg = snapshot.getValue(Message.class);
                    if (msg.getReceiver().equals(firebaseUser.getUid()) && !msg.isIsseen()){
                        unread++;
                    }
                }
                if (unread == 0){
                    unread_msgs.setText("0 new Messages");
                } else if(unread == 1){
                    unread_msgs.setText(String.valueOf(unread)+ " new Message");
                } else{
                    unread_msgs.setText(String.valueOf(unread)+ " new Messages");
                }*/
                int unread = 0;
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Message msg = snapshot.getValue(Message.class);
                        if (msg != null && msg.getReceiver() != null && msg.getReceiver().equals(currentUser.getUid()) && !msg.isIsseen()){
                            unread++;
                        }
                    }
                }

                if (unread == 0){
                    unread_msgs.setText("0 new Messages");
                } else if(unread == 1){
                    unread_msgs.setText(String.valueOf(unread)+ " new Message");
                } else{
                    unread_msgs.setText(String.valueOf(unread)+ " new Messages");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        //status("offline");
    }
}