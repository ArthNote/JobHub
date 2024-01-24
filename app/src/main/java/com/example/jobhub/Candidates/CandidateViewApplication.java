package com.example.jobhub.Candidates;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobhub.Database;
import com.example.jobhub.R;
import com.example.jobhub.Recruiters.EditDeleteJob;
import com.example.jobhub.Recruiters.RecruiterJobs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CandidateViewApplication extends AppCompatActivity {

    DatabaseReference reference;
    FirebaseUser firebaseUser;
    ImageView toolbar_arrow;
    TextView toolbar_title;
    EditText candidateappview_company, candidateappview_position, candidateappview_date;
    Button candidateappview_delete_btn,candidateappview_viewjob_btn;
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_view_application);
        toolbar_arrow = findViewById(R.id.toolbar_arrow);
        toolbar_title = findViewById(R.id.toolbar_title);
        Intent i = getIntent();
        String user_id = i.getStringExtra("user_id");
        String app_id = i.getStringExtra("id");
        String statu = i.getStringExtra("statu");
        String job_id = i.getStringExtra("job_id");
        toolbar_title.setText(statu);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        candidateappview_company = findViewById(R.id.candidateappview_company);
        candidateappview_position = findViewById(R.id.candidateappview_position);
        candidateappview_date = findViewById(R.id.candidateappview_date);
        candidateappview_delete_btn = findViewById(R.id.candidateappview_delete_btn);
        candidateappview_viewjob_btn = findViewById(R.id.candidateappview_viewjob_btn);

        disableFields();

        db = new Database(this);

        DisplayApplicationData(app_id);
        /*candidateappview_viewjob_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CandidateJobView.class);
                i.putExtra("user_id", user_id);
                i.putExtra("job_id", job_id);
                i.putExtra("source", "app");
                startActivity(i);
            }
        });
        candidateappview_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog(app_id, user_id);
            }
        });
*/
        toolbar_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), History.class);
                i.putExtra("user_id", user_id);
                startActivity(i);
            }
        });
    }

    void confirmDialog(String app_id, String user_id){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Application?");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Boolean delete = db.deleteApplication(app_id);
                if (delete == true){
                    Toast.makeText(CandidateViewApplication.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), History.class);
                    i.putExtra("user_id", user_id);
                    startActivity(i);
                } else {
                    Toast.makeText(CandidateViewApplication.this, "Delete Failed", Toast.LENGTH_SHORT).show();
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

    void DisplayApplicationData(String app_id){
        Cursor cursor = db.DisplayApplicationData(app_id);
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else{
            while (cursor.moveToNext()){
                candidateappview_company.setText(cursor.getString(5));
                candidateappview_position.setText(cursor.getString(4));
                candidateappview_date.setText(cursor.getString(7));
            }
        }
    }

    void disableFields(){
        candidateappview_company.setEnabled(false);
        candidateappview_position.setEnabled(false);
        candidateappview_date.setEnabled(false);
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