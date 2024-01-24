package com.example.jobhub.Recruiters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobhub.Database;
import com.example.jobhub.MessageAdapter;
import com.example.jobhub.R;
import com.example.jobhub.models.Chat;
import com.example.jobhub.models.Message;
import com.example.jobhub.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class RecruiterChat extends AppCompatActivity {

    FirebaseUser firebaseUser;
    DatabaseReference reference;
    EditText message_field;
    ImageView send_msg_btn,send_mic_btn;
    RecyclerView rv_chat;
    MessageAdapter messageAdapter;
    List<Message> mChat;
    String fid;
    ImageView toolbar_arrow;
    TextView toolbar_title;
    Database db;
    ValueEventListener seenListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_chat);
        Intent i = getIntent();
        String recruiter_id =  i.getStringExtra("user_id");
        fid = i.getStringExtra("fid");
        Toast.makeText(this, fid, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, recruiter_id, Toast.LENGTH_SHORT).show();
        db = new Database(this);
        toolbar_arrow = findViewById(R.id.toolbar_arrow);
        toolbar_title = findViewById(R.id.toolbar_title);

        send_mic_btn = findViewById(R.id.send_mic_btn);
        message_field = findViewById(R.id.message_field);
        send_msg_btn = findViewById(R.id.send_msg_btn);
        rv_chat = findViewById(R.id.rv_chat);
        rv_chat.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        rv_chat.setLayoutManager(linearLayoutManager);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fid);

        Toast.makeText(this, firebaseUser.getUid(), Toast.LENGTH_SHORT).show();
        send_msg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = message_field.getText().toString().trim();
                if(!msg.equals("")){
                    sendMessage(firebaseUser.getUid(), fid, msg);
                } else{
                    Toast.makeText(RecruiterChat.this, "you cant send empty messages", Toast.LENGTH_SHORT).show();
                } message_field.setText("");
            }
        });

        send_mic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertSpeech();
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //User user = dataSnapshot.getValue(User.class);
                //toolbar_title.setText(user.getFname());
                String name = db.getUserName(fid);
                toolbar_title.setText(name);
                readMessage(firebaseUser.getUid(), fid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        toolbar_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RecruiterMessages.class);
                i.putExtra("user_id", recruiter_id);
                startActivity(i);
            }
        });
        seenMessage(fid);
    }

    private void convertSpeech() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK){
            ArrayList<String> speakresults = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            sendMessage(firebaseUser.getUid(),fid,speakresults.get(0));
        }
    }
    private void seenMessage(String userId){
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Message msg = snapshot.getValue(Message.class);
                    if (msg.getReceiver().equals(firebaseUser.getUid()) && msg.getSender().equals(userId)){
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("isseen", true);
                        snapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void sendMessage(String sender, String receiver, String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        reference.child("Chats").push().setValue(hashMap);
    }

    private void readMessage(String myid, String userId){
        mChat = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Message msg = snapshot.getValue(Message.class);
                    if (msg != null && msg.getReceiver() != null && msg.getSender() != null) {
                        if(msg.getReceiver().equals(myid) && msg.getSender().equals(userId) ||
                                msg.getReceiver().equals(userId) && msg.getSender().equals(myid)){
                            mChat.add(msg);
                        }
                        messageAdapter = new MessageAdapter(getApplicationContext(),mChat);
                        rv_chat.setAdapter(messageAdapter);
                    }else{
                        Toast.makeText(RecruiterChat.this, "null", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        reference.removeEventListener(seenListener);
        status("offline");
    }
}

