package com.example.jobhub.Recruiters.fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jobhub.Candidates.CandidateApplicationsAdapter;
import com.example.jobhub.Database;
import com.example.jobhub.R;
import com.example.jobhub.Recruiters.RecruiterApplicationAdapter;
import com.example.jobhub.models.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RRejectedFragment extends Fragment {

    String user_id;
    Database db;
    RecruiterApplicationAdapter adapter;
    RecyclerView candidate_applications_recyclerview;
    ArrayList<Application> appList = new ArrayList<>();
    public RRejectedFragment(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_r_rejected, container, false);

        candidate_applications_recyclerview = view.findViewById(R.id.candidate_applications_recyclerview);
        db = new Database(getContext());
        DisplayRecApplications(user_id);
        Map<Integer, List<Application>> groupedApplications = groupApplicationsByJob(appList);
        adapter = new RecruiterApplicationAdapter(getActivity(), getContext(), groupedApplications, user_id,db,"rejected");
        candidate_applications_recyclerview.setAdapter(adapter);
        candidate_applications_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }
    Map<Integer, List<Application>> groupApplicationsByJob(List<Application> applications){
        Map<Integer, List<Application>> groupedApplications = new HashMap<>();
        for(Application application : applications){
            int jobId = application.getJid();
            if(groupedApplications.containsKey(jobId)){
                groupedApplications.get(jobId).add(application);
            } else{
                List<Application> jobApplications = new ArrayList<>();
                jobApplications.add(application);
                groupedApplications.put(jobId,jobApplications);
            }
        }
        return groupedApplications;
    }
    void DisplayRecApplications(String user_id){
        Cursor cursor = db.DisplayRecApplications(user_id, "rejected");
        if (cursor.getCount() == 0){
            Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
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