package com.example.jobhub.Recruiters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class RecruiterApplicationAdapter extends RecyclerView.Adapter<RecruiterApplicationAdapter.MyViewHolder> {

    Context context;
    Activity activity;
    private List<Application> appList = new ArrayList<>();
    Map<Integer, List<Application>> groupedApplications;
    String user_id;
    Database db;
    String status;
    public RecruiterApplicationAdapter (Activity activity, Context context, Map<Integer, List<Application>> groupedApplications, String user_id, Database db, String status){
        this.context = context;
        this.activity = activity;
        this.groupedApplications = groupedApplications;
        this.user_id = user_id;
        this.db=db;
        this.status=status;
    }
    @NonNull
    @Override
    public RecruiterApplicationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recruiter_jobs_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecruiterApplicationAdapter.MyViewHolder holder, int position) {
        int jobId = groupedApplications.keySet().toArray(new Integer[0])[position];
        List<Application> applications = groupedApplications.get(jobId);
        //Application aList = appList.get(position);
        //String job_id = String.valueOf(aList.getJid());
        Cursor cursor = db.DisplayJobData(String.valueOf(applications.get(0).getJid()));
        if (cursor.getCount() == 0){
            Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show();
        } else{
            while (cursor.moveToNext()){

                holder.recruiter_jobs_row_jname.setText(cursor.getString(2));
                holder.recruiter_jobs_row_company_location.setText(cursor.getString(3)+" • "+cursor.getString(16));
                holder.recruiter_jobs_row_worktime.setText(cursor.getString(19));
                holder.recruiter_jobs_row_exp.setText(cursor.getString(9)+"-"+cursor.getString(10)+" Years");
                holder.recruiter_jobs_row_worktype.setText(cursor.getString(20));
                String id = cursor.getString(0);
                int count = db.CountApplicants(id);
                if (count <= 1){
                    holder.recruiter_jobs_row_numapp.setText(count+" application");
                } else{
                    holder.recruiter_jobs_row_numapp.setText(count+" applications");
                }
                String date = String.valueOf(cursor.getString(15));
                LocalDate jobPublishedDate = LocalDate.parse(date);
                LocalDate currentDate = LocalDate.now();
                long daysAgo = currentDate.toEpochDay() - jobPublishedDate.toEpochDay();
                long monthsAgo = currentDate.getMonthValue() - jobPublishedDate.getMonthValue() +
                        12 * (currentDate.getYear() - jobPublishedDate.getYear());
                long yearsAgo = currentDate.getYear() - jobPublishedDate.getYear();
                if (daysAgo == 0) {
                    holder.recruiter_jobs_row_pdate.setText("Today");
                } else if (daysAgo == 1) {
                    holder.recruiter_jobs_row_pdate.setText("Yesterday");
                } else if (daysAgo < 31) {
                    holder.recruiter_jobs_row_pdate.setText(String.valueOf(daysAgo) + " days ago");
                } else if (monthsAgo == 1) {
                    holder.recruiter_jobs_row_pdate.setText("1 month ago");
                } else if (monthsAgo < 12) {
                    holder.recruiter_jobs_row_pdate.setText(String.valueOf(monthsAgo) + " months ago");
                } else if (yearsAgo == 1) {
                    holder.recruiter_jobs_row_pdate.setText("1 year ago");
                } else {
                    holder.recruiter_jobs_row_pdate.setText(String.valueOf(yearsAgo) + " years ago");
                }
                holder.recruiter_jobs_row_linearlayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, RecruiterViewJobApplicants.class);
                        i.putExtra("user_id",user_id);
                        i.putExtra("job_id",String.valueOf(applications.get(0).getJid()));
                        i.putExtra("status",status);
                        activity.startActivityForResult(i,1);
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

        TextView recruiter_jobs_row_jname, recruiter_jobs_row_company_location, recruiter_jobs_row_worktime,
                recruiter_jobs_row_exp, recruiter_jobs_row_worktype,recruiter_jobs_row_numapp,recruiter_jobs_row_pdate;
        LinearLayout recruiter_jobs_row_linearlayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recruiter_jobs_row_jname = itemView.findViewById(R.id.recruiter_jobs_row_jname);
            recruiter_jobs_row_company_location = itemView.findViewById(R.id.recruiter_jobs_row_company_location);
            recruiter_jobs_row_worktime = itemView.findViewById(R.id.recruiter_jobs_row_worktime);
            recruiter_jobs_row_exp = itemView.findViewById(R.id.recruiter_jobs_row_exp);
            recruiter_jobs_row_worktype = itemView.findViewById(R.id.recruiter_jobs_row_worktype);
            recruiter_jobs_row_numapp = itemView.findViewById(R.id.recruiter_jobs_row_numapp);
            recruiter_jobs_row_pdate = itemView.findViewById(R.id.recruiter_jobs_row_pdate);
            recruiter_jobs_row_linearlayout = itemView.findViewById(R.id.recruiter_jobs_row_linearlayout);
        }
    }

}
