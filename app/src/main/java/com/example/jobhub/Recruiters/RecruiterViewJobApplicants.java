package com.example.jobhub.Recruiters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobhub.Database;
import com.example.jobhub.R;
import com.example.jobhub.models.Application;
import com.example.jobhub.models.Job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RecruiterViewJobApplicants extends AppCompatActivity {

    ImageView toolbar_arrow;
    TextView toolbar_title;
    Database db;
    RecruiterApplicantsAdapter adapter;
    RecyclerView recruiter_applicants_recyclerview;
    ArrayList<Application> appList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_view_job_applicants);
        toolbar_arrow = findViewById(R.id.toolbar_arrow);
        toolbar_title = findViewById(R.id.toolbar_title);
        db = new Database(this);
        Intent i = getIntent();
        String recruiter_id = i.getStringExtra("user_id");
        String job_id = i.getStringExtra("job_id");
        String status = i.getStringExtra("status");

        recruiter_applicants_recyclerview = findViewById(R.id.recruiter_applicants_recyclerview);

        DisplayRecApplications(job_id, status);
        Map<Integer, List<Application>> groupedApplications = groupApplicationsByCid(appList);
        adapter = new RecruiterApplicantsAdapter(RecruiterViewJobApplicants.this, RecruiterViewJobApplicants.this, groupedApplications, recruiter_id,db,job_id);
        recruiter_applicants_recyclerview.setAdapter(adapter);
        recruiter_applicants_recyclerview.setLayoutManager(new LinearLayoutManager(RecruiterViewJobApplicants.this));

        toolbar_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RecruiterCandidats.class);
                i.putExtra("user_id", recruiter_id);
                startActivity(i);
                finish();
            }
        });
    }
    Map<Integer, List<Application>> groupApplicationsByCid(List<Application> applications){
        Map<Integer, List<Application>> groupedApplications = new HashMap<>();
        for(Application application : applications){
            int canId = application.getCid();
            if(groupedApplications.containsKey(canId)){
                groupedApplications.get(canId).add(application);
            } else{
                List<Application> jobApplications = new ArrayList<>();
                jobApplications.add(application);
                groupedApplications.put(canId,jobApplications);
            }
        }
        return groupedApplications;
    }
    /*void DisplayRecApplications(String user_id, String status){
        appList.clear();
        Cursor cursor = db.DisplayRecApplications(user_id, status);
        if (cursor.getCount() == 0){
            Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();
        } else{
            Set<Integer> uniqueIds = new HashSet<>();
            while (cursor.moveToNext()){
                int appId = cursor.getInt(0);
                if(!uniqueIds.contains(appId)){
                    appList.add(new Application(appId,
                            cursor.getInt(1),
                            cursor.getInt(2),
                            cursor.getInt(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7)));
                }
            }
        }
    }*/
    void DisplayRecApplications(String user_id, String status){
        Cursor cursor = db.DisplayRecApplicationsd(user_id, status);
        if (cursor.getCount() == 0){
            Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();
        } else{
            while (cursor.moveToNext()){
                    appList.add(new Application(cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getInt(2),
                            cursor.getInt(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7)));

            }
        }
    }

}