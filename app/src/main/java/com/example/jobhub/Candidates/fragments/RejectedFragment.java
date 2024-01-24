package com.example.jobhub.Candidates.fragments;

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
import com.example.jobhub.models.Application;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;


public class RejectedFragment extends Fragment {

    DatabaseReference reference;
    FirebaseUser firebaseUser;
    String user_id;
    Database db;
    CandidateApplicationsAdapter adapter;
    RecyclerView candidate_applications_recyclerview;
    ArrayList<Application> appList = new ArrayList<>();

    public RejectedFragment(String user_id) {
        this.user_id = user_id;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rejected, container, false);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        candidate_applications_recyclerview = view.findViewById(R.id.candidate_applications_recyclerview);
        db = new Database(getContext());
        DisplayApplications(user_id);
        adapter = new CandidateApplicationsAdapter(getActivity(), getContext(), appList, user_id, db);
        candidate_applications_recyclerview.setAdapter(adapter);
        candidate_applications_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
    void DisplayApplications(String user_id){
        Cursor cursor = db.DisplayApplications(user_id, "rejected");
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
    private void status(String status){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);
        reference.updateChildren(hashMap);
    }

    @Override
    public void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    public void onPause() {
        super.onPause();
        status("offline");
    }
}