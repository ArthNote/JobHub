package com.example.jobhub.Recruiters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobhub.Database;
import com.example.jobhub.R;
import com.example.jobhub.models.Application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecruiterApplicantsAdapter extends RecyclerView.Adapter<RecruiterApplicantsAdapter.MyViewHolder> {

    Context context;
    Activity activity;
    private List<Application> appList = new ArrayList<>();
    Map<Integer, List<Application>> groupedApplications;
    String user_id;
    Database db;
    String job_id;
    public RecruiterApplicantsAdapter (Activity activity, Context context, Map<Integer, List<Application>> groupedApplications, String user_id, Database db, String job_id){
        this.context = context;
        this.activity = activity;
        this.groupedApplications = groupedApplications;
        this.user_id = user_id;
        this.job_id=job_id;
        this.db=db;
    }
    @NonNull
    @Override
    public RecruiterApplicantsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recruiter_applicants_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecruiterApplicantsAdapter.MyViewHolder holder, int position) {

        //Application aList = appList.get(position);
        //String c_id = String.valueOf(aList.getCid());
        int cId = groupedApplications.keySet().toArray(new Integer[0])[position];
        List<Application> applications = groupedApplications.get(cId);
        Cursor cursor = db.displayUserInfo(String.valueOf(applications.get(0).getCid()));
        if (cursor.getCount() == 0){
            Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show();
        } else{
            while (cursor.moveToNext()){
                String fname = cursor.getString(1);
                String lname = cursor.getString(2);
                Toast.makeText(context, "job "+job_id, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "user "+String.valueOf(applications.get(0).getCid()), Toast.LENGTH_SHORT).show();
                holder.recruiter_applicant_row_name.setText(fname+" "+lname);
                holder.recruiter_candidate_row_specs.setText(cursor.getString(12));
                holder.recruiter_candidate_row_exp.setText(cursor.getString(11));
                holder.accept_candidate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Boolean accept = db.updateApplicationStatus(String.valueOf(applications.get(0).getId()), "accepted");
                        if (accept == true){
                            Toast.makeText(context, "Application accepted", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(context, RecruiterCandidats.class);
                            i.putExtra("user_id", user_id);
                            activity.startActivity(i);
                            activity.finish();
                        } else{
                            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                holder.reject_candidate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Boolean accept = db.updateApplicationStatus(String.valueOf(applications.get(0).getId()), "rejected");
                        if (accept == true){
                            Toast.makeText(context, "Application rejected", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(context, RecruiterCandidats.class);
                            i.putExtra("user_id", user_id);
                            activity.startActivity(i);
                            activity.finish();
                        } else{
                            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                holder.view_cprofile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, CandidateProfile.class);
                        i.putExtra("user_id", String.valueOf(applications.get(0).getCid()));
                        i.putExtra("recruiter_id", user_id);
                        i.putExtra("source", "app");
                        activity.startActivity(i);
                    }
                });
                holder.view_jobpage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, EditDeleteJob.class);
                        i.putExtra("job_id", job_id);
                        i.putExtra("user_id", user_id);
                        i.putExtra("source", "app");
                        activity.startActivity(i);
                    }
                });
            }
        }
    }



    @Override
    public int getItemCount() {
        return groupedApplications.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView recruiter_applicant_row_name, recruiter_candidate_row_specs, recruiter_candidate_row_exp;
        ImageButton accept_candidate, reject_candidate, view_cprofile, view_jobpage;
        LinearLayout recruiter_applicant_row_linearlayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recruiter_applicant_row_name = itemView.findViewById(R.id.recruiter_applicant_row_name);
            recruiter_candidate_row_specs = itemView.findViewById(R.id.recruiter_candidate_row_specs);
            recruiter_candidate_row_exp = itemView.findViewById(R.id.recruiter_candidate_row_exp);
            accept_candidate = itemView.findViewById(R.id.accept_candidate);
            reject_candidate = itemView.findViewById(R.id.reject_candidate);
            view_cprofile = itemView.findViewById(R.id.view_cprofile);
            view_jobpage = itemView.findViewById(R.id.view_jobpage);
            recruiter_applicant_row_linearlayout = itemView.findViewById(R.id.recruiter_applicant_row_linearlayout);
        }
    }

}
