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
import com.example.jobhub.models.Bookmark;
import com.example.jobhub.models.Job;
import com.example.jobhub.models.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class bookmarksAdapter extends RecyclerView.Adapter<bookmarksAdapter.MyViewHolder> {

    Context context;
    Activity activity;
    private List<Bookmark> bookmarkList = new ArrayList<>();
    String user_id;
    Database db;

    public bookmarksAdapter (Activity activity, Context context, ArrayList<Bookmark> bookmarkList, String user_id , Database db){
        this.db = db;
        this.context = context;
        this.activity = activity;
        this.bookmarkList = bookmarkList;
        this.user_id = user_id;
    }
    @NonNull
    @Override
    public bookmarksAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recruiter_jobs_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull bookmarksAdapter.MyViewHolder holder, int position) {


        Bookmark blist = bookmarkList.get(position);
        holder.recruiter_jobs_row_pdate.setText(String.valueOf(blist.getPdate()));
        Cursor cursor = db.DisplayJobData(String.valueOf(blist.getJid()));
        if(cursor.getCount() == 0){
            Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
        } else{
            while (cursor.moveToNext()){
                holder.recruiter_jobs_row_company_location.setText(cursor.getString(3)+" • "+cursor.getString(16));
                holder.recruiter_jobs_row_worktime.setText(cursor.getString(19));
                holder.recruiter_jobs_row_exp.setText(String.valueOf(cursor.getInt(9))+"-"+String.valueOf(cursor.getInt(10))+" Years");
                holder.recruiter_jobs_row_worktype.setText(cursor.getString(20));
            }
        }
        int count = db.CountApplicants(String.valueOf(blist.getJid()));
        if (count <= 1){
            holder.recruiter_jobs_row_numapp.setText(count+" application");
        } else{
            holder.recruiter_jobs_row_numapp.setText(count+" applications");
        }
        String date = String.valueOf(blist.getPdate());
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
                i.putExtra("user_id",String.valueOf(blist.getCid()));
                i.putExtra("job_id",String.valueOf(blist.getJid()));
                i.putExtra("source","bookmarks");
                activity.startActivityForResult(i,1);
                Toast.makeText(context, "you clicked on bookmark", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView recruiter_jobs_row_jname, recruiter_jobs_row_company_location, recruiter_jobs_row_worktime,
                recruiter_jobs_row_exp, recruiter_jobs_row_worktype,recruiter_jobs_row_numapp,recruiter_jobs_row_pdate;
        LinearLayout recruiter_jobs_row_linearlayout, line;
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
            line = itemView.findViewById(R.id.line);
        }
    }

}
