package com.example.jobhub.Candidates;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobhub.Database;
import com.example.jobhub.R;
import com.example.jobhub.models.Application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CandidateApplicationsAdapter extends RecyclerView.Adapter<CandidateApplicationsAdapter.MyViewHolder> {

    Context context;
    Activity activity;
    private List<Application> appList = new ArrayList<>();
    String user_id;
    Database db;

    public CandidateApplicationsAdapter (Activity activity, Context context, ArrayList<Application> appList, String user_id, Database db){
        this.context = context;
        this.activity = activity;
        this.appList = appList;
        this.user_id = user_id;
        this.db = db;
    }
    @NonNull
    @Override
    public CandidateApplicationsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.application_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CandidateApplicationsAdapter.MyViewHolder holder, int position) {


        Application aList = appList.get(position);
        holder.applications_row_position.setText(String.valueOf(aList.getPosition()));
        holder.applications_row_company.setText(String.valueOf(aList.getCompany()));
        String date = String.valueOf(aList.getDate());
        LocalDate jobPublishedDate = LocalDate.parse(date);
        LocalDate currentDate = LocalDate.now();
        long daysAgo = currentDate.toEpochDay() - jobPublishedDate.toEpochDay();
        long monthsAgo = currentDate.getMonthValue() - jobPublishedDate.getMonthValue() +
                12 * (currentDate.getYear() - jobPublishedDate.getYear());
        long yearsAgo = currentDate.getYear() - jobPublishedDate.getYear();
        if (daysAgo == 0) {
            holder.applications_row_pdate.setText("Today");
        } else if (daysAgo == 1) {
            holder.applications_row_pdate.setText("Yesterday");
        } else if (daysAgo < 31) {
            holder.applications_row_pdate.setText(String.valueOf(daysAgo) + " days ago");
        } else if (monthsAgo == 1) {
            holder.applications_row_pdate.setText("1 month ago");
        } else if (monthsAgo < 12) {
            holder.applications_row_pdate.setText(String.valueOf(monthsAgo) + " months ago");
        } else if (yearsAgo == 1) {
            holder.applications_row_pdate.setText("1 year ago");
        } else {
            holder.applications_row_pdate.setText(String.valueOf(yearsAgo) + " years ago");
        }
        String status = aList.getStatus();
        holder.applications_row_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CandidateViewApplication.class);
                i.putExtra("user_id",String.valueOf(aList.getCid()));
                i.putExtra("id",String.valueOf(aList.getId()));
                i.putExtra("job_id",String.valueOf(aList.getJid()));
                if (status.equals("pending")){
                    i.putExtra("statu","pending");
                } else if (status.equals("accepted")) {
                    i.putExtra("statu","accepted");
                }else{
                    i.putExtra("statu","rejected");
                }
                activity.startActivityForResult(i,1);
            }
        });
        holder.candidateappview_viewjob_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CandidateJobView.class);
                i.putExtra("user_id", user_id);
                i.putExtra("job_id", String.valueOf(aList.getJid()));
                i.putExtra("source", "app");
                activity.startActivity(i);
            }
        });
        holder.candidateappview_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog(String.valueOf(aList.getId()), user_id);
            }
        });
    }

    void confirmDialog(String app_id, String user_id){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Application?");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Boolean delete = db.deleteApplication(app_id);
                if (delete == true){
                    Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    Intent i  = new Intent(context, History.class);
                    i.putExtra("user_id", user_id);
                    activity.startActivity(i);
                    activity.finish();
                } else {
                    Toast.makeText(context, "Delete Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }



    @Override
    public int getItemCount() {
        return appList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView applications_row_position, applications_row_company, applications_row_pdate;
        LinearLayout applications_row_linearlayout;
        Button candidateappview_viewjob_btn, candidateappview_delete_btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            applications_row_position = itemView.findViewById(R.id.applications_row_position);
            applications_row_company = itemView.findViewById(R.id.applications_row_company);
            applications_row_pdate = itemView.findViewById(R.id.applications_row_pdate);
            applications_row_linearlayout = itemView.findViewById(R.id.applications_row_linearlayout);
            candidateappview_viewjob_btn = itemView.findViewById(R.id.candidateappview_viewjob_btn);
            candidateappview_delete_btn = itemView.findViewById(R.id.candidateappview_delete_btn);
        }
    }

}
