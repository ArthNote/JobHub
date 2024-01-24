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

import com.example.jobhub.Database;
import com.example.jobhub.R;
import com.example.jobhub.Recruiters.EditDeleteJob;
import com.example.jobhub.Recruiters.RecruiterJobsAdapter;
import com.example.jobhub.models.Job;
import com.example.jobhub.models.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CandidateJobSearchAdapter extends RecyclerView.Adapter<CandidateJobSearchAdapter.MyViewHolder> implements Filterable {

    Context context;
    Activity activity;
    String user_id;
    Database db;
    private List<Job> jobList = new ArrayList<>();
    private List<Job> jobListAll = new ArrayList<>();

    CandidateJobSearchAdapter (Activity activity, Context context, ArrayList<Job> jobList, String user_id,Database db){
        this.context = context;
        this.activity = activity;
        this.jobList = jobList;
        this.db = db;
        this.user_id = user_id;
        this.jobListAll = new ArrayList<>(jobList);
    }
    @NonNull
    @Override
    public CandidateJobSearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recruiter_jobs_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CandidateJobSearchAdapter.MyViewHolder holder, int position) {


        Job jlist = jobList.get(position);
        holder.recruiter_jobs_row_jname.setText(String.valueOf(jlist.getJname()));
        holder.recruiter_jobs_row_company_location.setText(String.valueOf(jlist.getCname())+" • "+String.valueOf(jlist.getLocation()));
        holder.recruiter_jobs_row_worktime.setText(String.valueOf(jlist.getWorktime()));
        holder.recruiter_jobs_row_exp.setText(String.valueOf(jlist.getMin_exp())+"-"+String.valueOf(jlist.getMax_exp())+" Years");
        holder.recruiter_jobs_row_worktype.setText(String.valueOf(jlist.getWorktype()));
        int count = db.CountApplicants(String.valueOf(jlist.getId()));
        if (count <= 1){
            holder.recruiter_jobs_row_numapp.setText(count+" application");
        } else{
            holder.recruiter_jobs_row_numapp.setText(count+" applications");
        }
        String date = String.valueOf(jlist.getPdate());
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
                Intent i = new Intent(context, CandidateJobView.class);
                i.putExtra("user_id",user_id);
                i.putExtra("job_id",String.valueOf(jlist.getId()));
                i.putExtra("source","search");
                activity.startActivityForResult(i,1);
            }
        });
    }



    @Override
    public int getItemCount() {
        return jobList.size();
    }
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constaint) {

            List<Job> filteredList = new ArrayList<>();

            if (constaint.toString().isEmpty()) {
                filteredList.addAll(jobListAll);
            } else {
                for (Job job : jobListAll) {
                    if (job.getJname().toLowerCase().contains(constaint.toString().toLowerCase())
                            || job.getCname().toLowerCase().contains(constaint.toString().toLowerCase())
                            || job.getSpecs().toLowerCase().contains(constaint.toString().toLowerCase())
                            || job.getSkills().toLowerCase().contains(constaint.toString().toLowerCase())
                            || String.valueOf(job.getMin_exp()).toLowerCase().contains(constaint.toString().toLowerCase())
                            || String.valueOf(job.getMax_exp()).toLowerCase().contains(constaint.toString().toLowerCase())) {
                        filteredList.add(job);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            jobList.clear();
            jobList.addAll((Collection<? extends Job>) filterResults.values);
            notifyDataSetChanged();
        }
    };
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
