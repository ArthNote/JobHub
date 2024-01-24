package com.example.jobhub.Recruiters;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobhub.Database;
import com.example.jobhub.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class EditDeleteJob extends AppCompatActivity {
    ImageView toolbar_arrow;
    TextView toolbar_title;
    EditText editdeletejob_cname, editdeletejob_jname,
            editdeletejob_sdate, editdeletejob_desc,
            editdeletejob_position, editdeletejob_no_vacancy,
            editdeletejob_category,
            editdeletejob_min_exp, editdeletejob_max_exp,
            editdeletejob_grad, editdeletejob_pgrad,
            editdeletejob_specs, editdeletejob_skills, editdeletejob_min_pay, editdeletejob_max_pay,editdeletejob_location, editdeletejob_resp;
    Button editdeletejob_edit_btn, editdeletejob_delete_btn;
    Database db;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    RadioGroup editdeletejob_worktime_group, editdeletejob_worktype_group;
    AppCompatRadioButton selected_worktime, selected_worktype, editdeletejob_con, editdeletejob_full,editdeletejob_part,editdeletejob_free,
            editdeletejob_remote,editdeletejob_site, editdeletejob_hybrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_job);
        toolbar_arrow = findViewById(R.id.toolbar_arrow);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Job Page");
        Intent i = getIntent();
        String user_id = i.getStringExtra("user_id");
        String job_id = i.getStringExtra("job_id");
        String source = i.getStringExtra("source");

        editdeletejob_cname = findViewById(R.id.editdeletejob_cname);
        editdeletejob_jname = findViewById(R.id.editdeletejob_jname);
        editdeletejob_sdate = findViewById(R.id.editdeletejob_sdate);
        editdeletejob_desc = findViewById(R.id.editdeletejob_desc);
        editdeletejob_position = findViewById(R.id.editdeletejob_position);
        editdeletejob_no_vacancy = findViewById(R.id.editdeletejob_no_vacancy);
        editdeletejob_category = findViewById(R.id.editdeletejob_category);
        editdeletejob_min_exp = findViewById(R.id.editdeletejob_min_exp);
        editdeletejob_max_exp = findViewById(R.id.editdeletejob_max_exp);
        editdeletejob_grad = findViewById(R.id.editdeletejob_grad);
        editdeletejob_pgrad = findViewById(R.id.editdeletejob_pgrad);
        editdeletejob_specs = findViewById(R.id.editdeletejob_specs);
        editdeletejob_skills = findViewById(R.id.editdeletejob_skills);
        editdeletejob_edit_btn = findViewById(R.id.editdeletejob_edit_btn);
        editdeletejob_delete_btn = findViewById(R.id.editdeletejob_delete_btn);

        editdeletejob_max_pay = findViewById(R.id.editdeletejob_max_pay);
        editdeletejob_min_pay = findViewById(R.id.editdeletejob_min_pay);
        editdeletejob_location = findViewById(R.id.editdeletejob_location);
        editdeletejob_worktime_group = findViewById(R.id.editdeletejob_worktime_group);
        editdeletejob_worktype_group = findViewById(R.id.editdeletejob_worktype_group);
        editdeletejob_resp = findViewById(R.id.editdeletejob_resp);

        editdeletejob_con = findViewById(R.id.editdeletejob_con);
        editdeletejob_full = findViewById(R.id.editdeletejob_full);
        editdeletejob_part = findViewById(R.id.editdeletejob_part);
        editdeletejob_free = findViewById(R.id.editdeletejob_free);
        editdeletejob_remote = findViewById(R.id.editdeletejob_remote);
        editdeletejob_site = findViewById(R.id.editdeletejob_site);
        editdeletejob_hybrid = findViewById(R.id.editdeletejob_hybrid);

        db = new Database(this);

        DisplayJobData(job_id);


        editdeletejob_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jname = editdeletejob_jname.getText().toString().trim();
                String cname = editdeletejob_cname.getText().toString().trim();
                String sdate = editdeletejob_sdate.getText().toString().trim();
                String desc = editdeletejob_desc.getText().toString().trim();
                String position = editdeletejob_position.getText().toString().trim();
                int no_vacancy = Integer.parseInt(editdeletejob_no_vacancy.getText().toString().trim());
                String category = editdeletejob_category.getText().toString().trim();
                int min_exp = Integer.parseInt(editdeletejob_min_exp.getText().toString().trim());
                int max_exp = Integer.parseInt(editdeletejob_max_exp.getText().toString().trim());
                int grad = Integer.parseInt(editdeletejob_grad.getText().toString().trim());
                int pgrad = Integer.parseInt(editdeletejob_pgrad.getText().toString().trim());
                String specs = editdeletejob_specs.getText().toString().trim();
                String skills = editdeletejob_skills.getText().toString().trim();
                String location = editdeletejob_location.getText().toString().trim();
                String resp = editdeletejob_resp.getText().toString().trim();
                int min_pay = Integer.parseInt(editdeletejob_min_pay.getText().toString().trim());
                int max_pay = Integer.parseInt(editdeletejob_max_pay.getText().toString().trim());

                int time_id = editdeletejob_worktime_group.getCheckedRadioButtonId();
                int type_id = editdeletejob_worktype_group.getCheckedRadioButtonId();
                selected_worktime = findViewById(time_id);
                String time = selected_worktime.getText().toString().trim();
                selected_worktype = findViewById(type_id);
                String type = selected_worktype.getText().toString().trim();


                if (jname.equals("") || cname.equals("") || sdate.equals("") || desc.equals("") || skills.equals("")
                        || position.equals("") || category.equals("") || String.valueOf(no_vacancy).isEmpty() || String.valueOf(min_exp).isEmpty()
                        || specs.equals("") || String.valueOf(pgrad).isEmpty() || String.valueOf(grad).isEmpty()
                        || String.valueOf(max_exp).isEmpty() || String.valueOf(min_pay).isEmpty() || String.valueOf(max_pay).isEmpty() ||
                        location.equals("") || resp.equals("") || time_id ==-1 || type_id ==-1){
                    Toast.makeText(EditDeleteJob.this, "All fields are Empty", Toast.LENGTH_SHORT).show();
                } else{
                    Boolean update = db.editJob(job_id,  jname , cname, sdate , desc,
                            position , no_vacancy,  category ,  min_exp, max_exp , grad,  pgrad , specs, skills, location, min_pay, max_pay,
                            time, type, resp);
                    if (update == true){
                        Toast.makeText(EditDeleteJob.this, "Updated Successfully", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(EditDeleteJob.this, "Update Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        editdeletejob_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog(job_id, user_id, editdeletejob_jname.getText().toString().trim());
            }
        });


        toolbar_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (source.equals("app")){
                    Intent i = new Intent(getApplicationContext(), RecruiterCandidats.class);
                    i.putExtra("user_id", user_id);
                    startActivity(i);
                    finish();
                } else{
                    Intent i = new Intent(getApplicationContext(), RecruiterJobs.class);
                    i.putExtra("user_id", user_id);
                    startActivity(i);
                    finish();
                }

            }
        });

        editdeletejob_skills.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Detect if Enter key is pressed
                if (count > 0 && charSequence.charAt(start + count - 1) == '\n') {
                    insertBulletPoint();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        editdeletejob_skills.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    insertBulletPoint();
                    return true;
                }
                return false;
            }
        });
    }

    private void insertBulletPoint() {
        // Get the current text
        Editable editable = editdeletejob_skills.getText();
        int start = editdeletejob_skills.getSelectionStart();
        int end = editdeletejob_skills.getSelectionEnd();

        // Insert a bullet point at the beginning if not already present
        if (editable.length() == 0 || editable.charAt(0) != '\u2022') {
            editable.insert(0, "\u2022 ");
        } else if (start == 0 || (start > 0 && editable.charAt(start - 1) == '\n')) {
            // Insert a bullet point at the cursor position
            editable.replace(start, end, "\n\u2022 ");
            editdeletejob_skills.setSelection(editdeletejob_skills.getText().length());
        } else {
            // Allow the user to go to the next line without a bullet point
            editable.insert(start, "\n\u2022");
        }
    }
    void confirmDialog(String job_id, String user_id, String jname){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+jname+" ?");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Boolean delete = db.deleteJob(job_id);
                if (delete == true){
                    Toast.makeText(EditDeleteJob.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), RecruiterJobs.class);
                    i.putExtra("user_id", user_id);
                    startActivity(i);
                } else {
                    Toast.makeText(EditDeleteJob.this, "Delete Failed", Toast.LENGTH_SHORT).show();
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
    void DisplayJobData(String job_id){
        Cursor cursor = db.DisplayJobData(job_id);
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else{
            while (cursor.moveToNext()){
                editdeletejob_jname.setText(cursor.getString(2));
                editdeletejob_cname.setText(cursor.getString(3));
                editdeletejob_sdate.setText(cursor.getString(4));
                editdeletejob_desc.setText(cursor.getString(5));
                editdeletejob_position.setText(cursor.getString(6));
                editdeletejob_no_vacancy.setText(String.valueOf(cursor.getInt(7)));
                editdeletejob_category.setText(cursor.getString(8));
                editdeletejob_min_exp.setText(String.valueOf(cursor.getInt(9)));
                editdeletejob_max_exp.setText(String.valueOf(cursor.getInt(10)));
                editdeletejob_grad.setText(String.valueOf(cursor.getInt(11)));
                editdeletejob_pgrad.setText(String.valueOf(cursor.getInt(12)));
                editdeletejob_specs.setText(cursor.getString(13));
                editdeletejob_skills.setText(cursor.getString(14));
                editdeletejob_location.setText(cursor.getString(16));
                editdeletejob_min_pay.setText(String.valueOf(cursor.getInt(17)));
                editdeletejob_max_pay.setText(String.valueOf(cursor.getInt(18)));
                editdeletejob_resp.setText(cursor.getString(21));
                String time = cursor.getString(19);
                if (time.equals("Full Time")){
                    editdeletejob_full.setChecked(true);
                } else if(time.equals("Part Time")){
                    editdeletejob_part.setChecked(true);
                }else if(time.equals("Freelance")){
                    editdeletejob_free.setChecked(true);
                } else{
                    editdeletejob_con.setChecked(true);
                }
                String type = cursor.getString(20);
                if (type.equals("Remote")){
                    editdeletejob_remote.setChecked(true);
                } else if(type.equals("On Site")){
                    editdeletejob_site.setChecked(true);
                }else{
                    editdeletejob_hybrid.setChecked(true);
                }
            }
        }
    }
}