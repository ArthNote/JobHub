package com.example.jobhub.Candidates;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobhub.Candidates.CandidateChat;
import com.example.jobhub.Database;
import com.example.jobhub.R;
import com.example.jobhub.models.Chat;
import com.example.jobhub.models.Job;
import com.example.jobhub.models.User;
import com.google.android.material.imageview.ShapeableImageView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CandidateChatsAdapter extends RecyclerView.Adapter<CandidateChatsAdapter.MyViewHolder> {

    Context context;
    Activity activity;
    Database db;
    private List<Chat> chatList = new ArrayList<>();
    String user_id;


    public CandidateChatsAdapter (Activity activity, Context context, ArrayList<Chat> chatList, Database db,String user_id){
        this.context = context;

        this.db = db;
        this.activity = activity;
        this.chatList = chatList;
        this.user_id = user_id;
    }
    @NonNull
    @Override
    public CandidateChatsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chat_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CandidateChatsAdapter.MyViewHolder holder, int position) {
        Chat cList = chatList.get(position);
        holder.chat_row_name.setText(String.valueOf(cList.getRname()));
        String gender = db.getUserGender(String.valueOf(cList.getRid()));
        String rid = String.valueOf(cList.getRid());
        String fid = db.getUserFId(rid);
        if (gender.equals("Male")){
            holder.chat_row_image.setImageResource(R.drawable.employee);
        } else {
            holder.chat_row_image.setImageResource(R.drawable.women);
        }


        holder.chat_row_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "you cant access the chat now", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, fid, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, CandidateChat.class);
                i.putExtra("user_id", user_id);
                i.putExtra("fid", fid);
                activity.startActivity(i);
            }
        });

    }



    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView chat_row_name, chat_row_status_on,chat_row_status_off;
        ShapeableImageView chat_row_image;
        LinearLayout chat_row_linearlayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            chat_row_name = itemView.findViewById(R.id.chat_row_name);
            chat_row_image = itemView.findViewById(R.id.chat_row_image);
            chat_row_linearlayout = itemView.findViewById(R.id.chat_row_linearlayout);
            chat_row_status_on = itemView.findViewById(R.id.chat_row_status_on);
            chat_row_status_off = itemView.findViewById(R.id.chat_row_status_off);
        }
    }

}
