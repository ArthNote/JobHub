package com.example.jobhub.Candidates;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobhub.Candidates.fragments.SavedFragment;
import com.example.jobhub.Database;
import com.example.jobhub.R;
import com.example.jobhub.Recruiters.RecruiterJobs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class CandidateJobView extends AppCompatActivity {
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    ImageView toolbar_arrow;
    TextView toolbar_title;
    TextView candidatejobview_jname, candidatejobview_cname, candidatejobview_location,
            candidatejobview_worktime, candidatejobview_category,
            candidatejobview_worktype, candidatejobview_pay,
            candidatejobview_exp,
            candidatejobview_desc, candidatejobview_resp,
            candidatejobview_specs, candidatejobview_grad,
            candidatejobview_pgrad, candidatejobview_no_vacancy,
            candidatejobview_sdate, candidatejobview_skills;
    Button candidatejobview_apply_btn;
    ImageButton bookmark_btn;
    LinearLayout candidatejobview_desc_holder, candidatejobview_resp_holder,
                candidatejobview_req_holder, candidatejobview_req_details;
    Database db;
    Boolean aboutvisible = false;
    Boolean reqvisible = false;
    Boolean respvisible = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_job_view);
        toolbar_arrow = findViewById(R.id.toolbar_arrow);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Job Page");
        Intent i = getIntent();
        String user_id = i.getStringExtra("user_id");
        String job_id = i.getStringExtra("job_id");
        String source = i.getStringExtra("source");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        candidatejobview_location = findViewById(R.id.candidatejobview_location);
        candidatejobview_cname = findViewById(R.id.candidatejobview_cname);
        candidatejobview_jname = findViewById(R.id.candidatejobview_jname);
        candidatejobview_sdate = findViewById(R.id.candidatejobview_sdate);
        candidatejobview_desc = findViewById(R.id.candidatejobview_desc);
        candidatejobview_exp = findViewById(R.id.candidatejobview_exp);
        candidatejobview_no_vacancy = findViewById(R.id.candidatejobview_no_vacancy);
        candidatejobview_category = findViewById(R.id.candidatejobview_category);
        candidatejobview_resp = findViewById(R.id.candidatejobview_resp);
        candidatejobview_pay = findViewById(R.id.candidatejobview_pay);
        candidatejobview_grad = findViewById(R.id.candidatejobview_grad);
        candidatejobview_pgrad = findViewById(R.id.candidatejobview_pgrad);
        candidatejobview_specs = findViewById(R.id.candidatejobview_specs);
        candidatejobview_skills = findViewById(R.id.candidatejobview_skills);
        candidatejobview_apply_btn = findViewById(R.id.candidatejobview_apply_btn);
        Drawable bg = getDrawable(R.drawable.button_red);
        Drawable bg1 = getDrawable(R.drawable.bookmark_shape);
        candidatejobview_worktime = findViewById(R.id.candidatejobview_worktime);
        candidatejobview_worktype = findViewById(R.id.candidatejobview_worktype);
        candidatejobview_desc_holder = findViewById(R.id.candidatejobview_desc_holder);
        candidatejobview_resp_holder = findViewById(R.id.candidatejobview_resp_holder);
        candidatejobview_req_holder = findViewById(R.id.candidatejobview_req_holder);
        candidatejobview_req_details = findViewById(R.id.candidatejobview_req_details);
        bookmark_btn = findViewById(R.id.bookmark_btn);
        db = new Database(this);
        changebookmarkcolor(job_id,user_id,bg, db,bg1);



        DisplayJobData(job_id);

        candidatejobview_desc_holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAboutclicked();
            }
        });
        candidatejobview_resp_holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRespclicked();
            }
        });
        candidatejobview_req_holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReqclicked();
            }
        });

        candidatejobview_apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyToJob(job_id, user_id);
            }
        });

        bookmark_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean check = db.checkIfBookmarked(job_id, user_id);
                if (check == false){
                    String pdate = db.getJobPdate(job_id);
                    Boolean bookmark = db.bookmarkJob(user_id, job_id, candidatejobview_jname.getText().toString().trim(),candidatejobview_cname.getText().toString().trim(),pdate);
                    if (bookmark == true){
                        Toast.makeText(CandidateJobView.this, "You bookmarked this Job", Toast.LENGTH_SHORT).show();
                        bookmark_btn.setBackground(bg);
                    } else {
                        Toast.makeText(CandidateJobView.this, "Bookmark failed", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    confirmDialog(job_id, user_id, db);
                    changebookmarkcolor(job_id,user_id,bg, db,bg1);

                }

            }
        });
        toolbar_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (source.equals("bookmarks")){
                    Intent i = new Intent(getApplicationContext(), History.class);
                    i.putExtra("user_id", user_id);
                    startActivity(i);
                } else if (source.equals("app")) {
                    Intent i = new Intent(getApplicationContext(), History.class);
                    i.putExtra("user_id", user_id);
                    startActivity(i);
                } else {
                    Intent i = new Intent(getApplicationContext(), UserSearch.class);
                    i.putExtra("user_id", user_id);
                    startActivity(i);
                }
            }
        });
    }

    void changebookmarkcolor(String job_id, String user_id, Drawable bg, Database db, Drawable bg1){
        Boolean check = db.checkIfBookmarked(job_id, user_id);
        if (check == true){
            bookmark_btn.setBackground(bg);
        } else {
            bookmark_btn.setBackground(bg1);
        }
    }

    void confirmDialog(String job_id, String user_id, Database db){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remove Bookmark?");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Boolean delete = db.deleteBookmark(user_id, job_id);
                if (delete == true){
                    Toast.makeText(CandidateJobView.this, "Removed Successfully", Toast.LENGTH_SHORT).show();
                    recreate();
                } else {
                    Toast.makeText(CandidateJobView.this, "Remove Failed", Toast.LENGTH_SHORT).show();
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

    void applyToJob(String job_id, String candidate_id){
        Cursor cursor = db.DisplayJobData(job_id);
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else{
            while (cursor.moveToNext()){
                String position = cursor.getString(6).trim();
                String company = cursor.getString(3);
                int recruiter_id = Integer.parseInt(cursor.getString(1));
                String status = "pending";
                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = dateFormat.format(currentDate);
                Boolean checkApply = db.checkIfApplied(job_id, candidate_id);
                if (checkApply == false){
                    Boolean insert = db.applyToJob(Integer.parseInt(candidate_id), Integer.parseInt(job_id), recruiter_id, position, company, status, date);
                    if (insert == true){
                        Toast.makeText(this, "You applied to this job", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(this, "Apply failed", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(this, "You already applied to this job", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
    void DisplayJobData(String job_id){
        Cursor cursor = db.DisplayJobData(job_id);
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else{
            while (cursor.moveToNext()){
                candidatejobview_jname.setText(cursor.getString(2));
                candidatejobview_cname.setText(cursor.getString(3));
                candidatejobview_sdate.setText(cursor.getString(4));
                candidatejobview_desc.setText(cursor.getString(5));
                candidatejobview_no_vacancy.setText(String.valueOf(cursor.getInt(7))+" days");
                candidatejobview_category.setText(cursor.getString(8));
                String min_exp = String.valueOf(cursor.getInt(9));
                String max_exp = String.valueOf(cursor.getInt(10));
                candidatejobview_exp.setText(min_exp+"-"+max_exp+" Years");
                candidatejobview_grad.setText(String.valueOf(cursor.getInt(11))+"%");
                candidatejobview_pgrad.setText(String.valueOf(cursor.getInt(12))+"%");
                candidatejobview_specs.setText(cursor.getString(13));
                candidatejobview_skills.setText(cursor.getString(14));
                candidatejobview_location.setText(cursor.getString(16));
                String min_pay = String.valueOf(cursor.getInt(17));
                String max_pay = String.valueOf(cursor.getInt(18));
                candidatejobview_pay.setText("$"+min_pay+"-$"+max_pay+"k");
                candidatejobview_worktime.setText(cursor.getString(19));
                candidatejobview_worktype.setText(cursor.getString(20));
                candidatejobview_resp.setText(cursor.getString(21));


            }

        }
    }

    void setAboutclicked(){
        setAboutvisible(aboutvisible);
        if (!aboutvisible){
            aboutvisible = true;
        } else {
            aboutvisible = false;
        }
    }
    void setAboutvisible(Boolean aboutvisible){
        if (!aboutvisible){
            candidatejobview_desc.setVisibility(View.VISIBLE);
        } else {
            candidatejobview_desc.setVisibility(View.GONE);
        }
    }
    void setReqclicked(){
        setReqvisible(reqvisible);
        if (!reqvisible){
            reqvisible = true;
        } else {
            reqvisible = false;
        }
    }
    void setReqvisible(Boolean reqvisible){
        if (!reqvisible){
            candidatejobview_req_details.setVisibility(View.VISIBLE);
        } else {
            candidatejobview_req_details.setVisibility(View.GONE);
        }
    }
    void setRespclicked(){
        setRespvisible(respvisible);
        if (!respvisible){
            respvisible = true;
        } else {
            respvisible = false;
        }
    }
    void setRespvisible(Boolean respvisible){
        if (!respvisible){
            candidatejobview_resp.setVisibility(View.VISIBLE);
        } else {
            candidatejobview_resp.setVisibility(View.GONE);
        }
    }

    private void status(String status){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
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
        status("offline");
    }
}