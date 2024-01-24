package com.example.jobhub.Recruiters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobhub.Candidates.UserProfile;
import com.example.jobhub.Database;
import com.example.jobhub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EditRecruiterProfile extends AppCompatActivity {

    EditText editrecruiterprofile_lname, editrecruiterprofile_fname, editrecruiterprofile_phone, editrecruiterprofile_about;
    Button editrecruiterprofile_save_btn;
    RadioGroup editrecruiterprofile_gender;
    RadioButton selected_gender, editrecruiterprofile_male, editrecruiterprofile_female;
    ImageView toolbar_arrow;
    TextView toolbar_title;
    Database db;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recruiter_profile);
        toolbar_arrow = findViewById(R.id.toolbar_arrow);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Edit Profile");
        Intent i = getIntent();
        String user_id = i.getStringExtra("user_id");
        db = new Database(this);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        editrecruiterprofile_lname = findViewById(R.id.editrecruiterprofile_lname);
        editrecruiterprofile_fname = findViewById(R.id.editrecruiterprofile_fname);
        editrecruiterprofile_phone = findViewById(R.id.editrecruiterprofile_phone);
        editrecruiterprofile_about = findViewById(R.id.editrecruiterprofile_about);
        editrecruiterprofile_save_btn = findViewById(R.id.editrecruiterprofile_save_btn);
        editrecruiterprofile_gender = findViewById(R.id.editrecruiterprofile_gender);
        editrecruiterprofile_male = findViewById(R.id.editrecruiterprofile_male);
        editrecruiterprofile_female = findViewById(R.id.editrecruiterprofile_female);

        Toast.makeText(this, "id is= "+user_id, Toast.LENGTH_SHORT).show();


        displayData(user_id);

        editrecruiterprofile_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = editrecruiterprofile_fname.getText().toString().trim();
                String lname = editrecruiterprofile_lname.getText().toString().trim();
                String phone = editrecruiterprofile_phone.getText().toString().trim();
                String about = editrecruiterprofile_about.getText().toString().trim();
                int gender_id = editrecruiterprofile_gender.getCheckedRadioButtonId();

                if (fname.equals("") || lname.equals("")  || phone.equals("") || about.equals("") || gender_id ==-1){
                    Toast.makeText(EditRecruiterProfile.this, "All fields are Empty", Toast.LENGTH_SHORT).show();
                } else{
                    selected_gender = findViewById(gender_id);
                    String gender = selected_gender.getText().toString().trim();
                    Boolean update = db.updateRecruiterInfo(user_id, fname, lname, gender, phone, about);
                    if (update == true){
                        Toast.makeText(getApplicationContext(), "Profile updated Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Profile update Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        toolbar_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RecruiterProfile.class);
                i.putExtra("user_id", user_id);
                startActivity(i);
                finish();
            }
        });

    }
    void displayData(String user_id){
        Cursor cursor = db.displayUserInfo(user_id);
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else{
            while (cursor.moveToNext()){
                editrecruiterprofile_fname.setText(cursor.getString(1));
                editrecruiterprofile_lname.setText(cursor.getString(2));
                editrecruiterprofile_phone.setText(cursor.getString(5));
                editrecruiterprofile_about.setText(cursor.getString(7));
                String gender = cursor.getString(3);
                if (gender.equals("Male")){
                    editrecruiterprofile_male.setChecked(true);
                } else{
                    editrecruiterprofile_female.setChecked(true);
                }
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