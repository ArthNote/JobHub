package com.example.jobhub.Recruiters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.example.jobhub.Database;
import com.example.jobhub.Login;
import com.example.jobhub.R;
import com.example.jobhub.Register;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddJob extends AppCompatActivity {

    ImageView toolbar_arrow;
    TextView toolbar_title;
    EditText addjob_cname, addjob_jname, addjob_sdate, addjob_desc,
            addjob_position, addjob_no_vacancy, addjob_category,
            addjob_min_exp, addjob_max_exp, addjob_grad, addjob_pgrad,
            addjob_specs, addjob_skills, addjob_min_pay, addjob_max_pay,addjob_location, addjob_resp;
    Button addjob_add_btn;
    RadioGroup addjob_worktime_group, addjob_worktype_group;
    AppCompatRadioButton selected_worktime, selected_worktype;
    Database db;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);
        toolbar_arrow = findViewById(R.id.toolbar_arrow);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Create a Job");
        Intent i = getIntent();
        String user_id = i.getStringExtra("user_id");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        addjob_cname = findViewById(R.id.addjob_cname);
        addjob_jname = findViewById(R.id.addjob_jname);
        addjob_sdate = findViewById(R.id.addjob_sdate);
        addjob_desc = findViewById(R.id.addjob_desc);
        addjob_position = findViewById(R.id.addjob_position);
        addjob_no_vacancy = findViewById(R.id.addjob_no_vacancy);
        addjob_category = findViewById(R.id.addjob_category);
        addjob_min_exp = findViewById(R.id.addjob_min_exp);
        addjob_max_exp = findViewById(R.id.addjob_max_exp);
        addjob_grad = findViewById(R.id.addjob_grad);
        addjob_pgrad = findViewById(R.id.addjob_pgrad);
        addjob_specs = findViewById(R.id.addjob_specs);
        addjob_skills = findViewById(R.id.addjob_skills);
        addjob_add_btn = findViewById(R.id.addjob_add_btn);
        addjob_min_pay = findViewById(R.id.addjob_min_pay);
        addjob_max_pay = findViewById(R.id.addjob_max_pay);
        addjob_location = findViewById(R.id.addjob_location);
        addjob_worktime_group = findViewById(R.id.addjob_worktime_group);
        addjob_worktype_group = findViewById(R.id.addjob_worktype_group);
        addjob_resp = findViewById(R.id.addjob_resp);

        db = new Database(this);

        addjob_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jname = addjob_jname.getText().toString().trim();
                String cname = addjob_cname.getText().toString().trim();
                String sdate = addjob_sdate.getText().toString().trim();
                String desc = addjob_desc.getText().toString().trim();
                String position = addjob_position.getText().toString().trim();
                int no_vacancy = Integer.parseInt(addjob_no_vacancy.getText().toString().trim());
                String category = addjob_category.getText().toString().trim();
                int min_exp = Integer.parseInt(addjob_min_exp.getText().toString().trim());
                int max_exp = Integer.parseInt(addjob_max_exp.getText().toString().trim());
                int grad = Integer.parseInt(addjob_grad.getText().toString().trim());
                int pgrad = Integer.parseInt(addjob_pgrad.getText().toString().trim());
                String specs = addjob_specs.getText().toString().trim();
                String skills = addjob_skills.getText().toString().trim();
                String location = addjob_location.getText().toString().trim();
                String resp = addjob_resp.getText().toString().trim();
                int min_pay = Integer.parseInt(addjob_min_pay.getText().toString().trim());
                int max_pay = Integer.parseInt(addjob_max_pay.getText().toString().trim());
                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String pdate = dateFormat.format(currentDate);

                int time_id = addjob_worktime_group.getCheckedRadioButtonId();
                int type_id = addjob_worktype_group.getCheckedRadioButtonId();
                if (time_id != -1 && type_id != -1) {
                    selected_worktime = findViewById(time_id);
                    String time = selected_worktime.getText().toString().trim();

                    selected_worktype = findViewById(type_id);
                    String type = selected_worktype.getText().toString().trim();

                    if (jname.equals("") || cname.equals("") || sdate.equals("") || desc.equals("") || skills.equals("")|| location.equals("") || resp.equals("")
                            || position.equals("") || category.equals("") || String.valueOf(no_vacancy).isEmpty() || String.valueOf(min_exp).isEmpty()
                            || specs.equals("") || String.valueOf(pgrad).isEmpty() || String.valueOf(grad).isEmpty()
                            || String.valueOf(min_pay).isEmpty() || String.valueOf(max_pay).isEmpty() || String.valueOf(max_exp).isEmpty()
                            || time_id ==-1 || type_id ==-1){
                        Toast.makeText(AddJob.this, "All fields are Empty", Toast.LENGTH_SHORT).show();
                    } else {
                        Boolean insert = db.addJob(Integer.parseInt(user_id),  jname , cname, sdate , desc,
                                position , no_vacancy,  category ,  min_exp, max_exp , grad,  pgrad , specs, skills,  pdate, location, min_pay, max_pay
                                , time, type,resp);
                        if (insert == true){
                            Toast.makeText(AddJob.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            addjob_cname.setText("");
                            addjob_jname.setText("");
                            addjob_sdate.setText("");
                            addjob_desc.setText("");
                            addjob_position.setText("");
                            addjob_no_vacancy.setText("0");
                            addjob_category.setText("");
                            addjob_min_exp.setText("0");
                            addjob_max_exp.setText("0");
                            addjob_grad.setText("0");
                            addjob_pgrad.setText("0");
                            addjob_specs.setText("");
                            addjob_skills.setText("");
                            addjob_location.setText("");
                            addjob_resp.setText("");
                            addjob_min_pay.setText("0");
                            addjob_max_pay.setText("0");
                            addjob_worktime_group.check(-1);
                            addjob_worktype_group.check(-1);
                        } else {
                            Toast.makeText(AddJob.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    // Rest of your code
                } else {
                    Toast.makeText(AddJob.this, "Please select work time and type", Toast.LENGTH_SHORT).show();
                }

            }
        });

        addjob_skills.addTextChangedListener(new TextWatcher() {
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
        addjob_skills.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    insertBulletPoint();
                    return true;
                }
                return false;
            }
        });




        toolbar_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RecruiterJobs.class);
                i.putExtra("user_id", user_id);
                startActivity(i);
            }
        });
    }
    private void insertBulletPoint() {
        // Get the current text
        Editable editable = addjob_skills.getText();
        int start = addjob_skills.getSelectionStart();
        int end = addjob_skills.getSelectionEnd();

        // Insert a bullet point at the beginning if not already present
        if (editable.length() == 0 || editable.charAt(0) != '\u2022') {
            editable.insert(0, "\u2022 ");
        } else if (start == 0 || (start > 0 && editable.charAt(start - 1) == '\n')) {
            // Insert a bullet point at the cursor position
            editable.replace(start, end, "\n\u2022 ");
            addjob_skills.setSelection(addjob_skills.getText().length());
        } else {
            // Allow the user to go to the next line without a bullet point
            editable.insert(start, "\n\u2022");
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