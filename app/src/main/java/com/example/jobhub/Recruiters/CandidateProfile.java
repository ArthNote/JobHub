package com.example.jobhub.Recruiters;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobhub.Database;
import com.example.jobhub.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.List;

public class CandidateProfile extends AppCompatActivity {

    ImageView toolbar_arrow;
    TextView toolbar_title;
    LinearLayout candidateprofile_about_expand, candidateprofile_details, candidateprofile_skills_expand, candidateprofile_details_expand;
    TextView candidateprofile_name, candidateprofile_email
            ,candidateprofile_phone, candidateprofile_about, candidateprofile_grad_percent, candidateprofile_post_grad_percent
            , candidateprofile_work_exp, candidateprofile_specialization, candidateprofile_skills;

    Button candidateprofile_message_btn,candidateprofile_pdf;
    Boolean aboutvisible = false;
    Boolean detailsvisible = false;
    Boolean skillsvisible = false;
    ShapeableImageView profile_pic;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    StorageReference storageReference;
    StorageReference ref;
    FirebaseStorage firebaseStorage;
    RatingBar rec_can_rate;
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_profile);
        toolbar_arrow = findViewById(R.id.toolbar_arrow);
        toolbar_title = findViewById(R.id.toolbar_title);
        db = new Database(this);
        Intent i = getIntent();
        String user_id = i.getStringExtra("user_id");
        String recruiter_id = i.getStringExtra("recruiter_id");
        String source = i.getStringExtra("source");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        profile_pic = findViewById(R.id.profile_pic);
        candidateprofile_name = findViewById(R.id.candidateprofile_name);
        candidateprofile_email = findViewById(R.id.candidateprofile_email);
        candidateprofile_phone = findViewById(R.id.candidateprofile_phone);
        candidateprofile_about = findViewById(R.id.candidateprofile_about);
        candidateprofile_grad_percent = findViewById(R.id.candidateprofile_grad);
        candidateprofile_post_grad_percent = findViewById(R.id.candidateprofile_pgrad);
        candidateprofile_work_exp = findViewById(R.id.candidateprofile_exp);
        candidateprofile_specialization = findViewById(R.id.candidateprofile_specs_gender);
        candidateprofile_skills = findViewById(R.id.candidateprofile_skills);
        candidateprofile_message_btn = findViewById(R.id.candidateprofile_message_btn);
        rec_can_rate = findViewById(R.id.rec_can_rate);
        candidateprofile_pdf = findViewById(R.id.candidateprofile_pdf);
        rec_can_rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                setRate(user_id, recruiter_id,db);
            }
        });

        candidateprofile_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download(user_id);
            }
        });

        candidateprofile_about_expand = findViewById(R.id.candidateprofile_about_expand);
        candidateprofile_details = findViewById(R.id.candidateprofile_details);
        candidateprofile_skills_expand = findViewById(R.id.candidateprofile_skills_expand);
        candidateprofile_details_expand = findViewById(R.id.candidateprofile_details_expand);
        Boolean view = db.viewProfile(Integer.parseInt(user_id));
        if(view == true){
            Toast.makeText(this, "nice", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "not nice", Toast.LENGTH_SHORT).show();
        }

        DisplayData(user_id);

        candidateprofile_about_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAboutclicked();
            }
        });
        candidateprofile_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDetailsclicked();
            }
        });
        candidateprofile_skills_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSkillsclicked();
            }
        });

        getRate(user_id,db);



        candidateprofile_message_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean checkIfchatexists = db.checkIfChatExists(recruiter_id, user_id);
                String fid = db.getUserFId(user_id);
                if (checkIfchatexists == true){
                    Toast.makeText(CandidateProfile.this, "Chat exists", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), RecruiterChat.class);
                    i.putExtra("user_id", recruiter_id);
                    i.putExtra("fid", fid);
                    startActivity(i);
                }else {
                    Cursor ccursor = db.displayUserInfo(user_id);
                    Cursor rcursor = db.displayUserInfo(recruiter_id);
                    if(ccursor.getCount() == 0 && ccursor.getCount() == 0){
                        Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
                    } else{
                        while (ccursor.moveToNext() && rcursor.moveToNext()){
                            String cfname = ccursor.getString(1);
                            String clname  = ccursor.getString(2);
                            String cname = cfname + " " +clname;
                            String rfname = rcursor.getString(1);
                            String rlname  = rcursor.getString(2);
                            String rname = rfname + " " +rlname;
                            Boolean insert = db.startChat(user_id, recruiter_id, cname, rname);
                            if (insert == true){
                                Toast.makeText(CandidateProfile.this, "Chat started successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), RecruiterChat.class);
                                i.putExtra("user_id", recruiter_id);
                                i.putExtra("fid", fid);
                                startActivity(i);
                            }else{
                                Toast.makeText(CandidateProfile.this, "Chat not started", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });

        toolbar_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (source.equals("app")){
                    Intent i = new Intent(getApplicationContext(), RecruiterCandidats.class);
                    i.putExtra("user_id", recruiter_id);
                    startActivity(i);
                    finish();
                } else{
                    Intent i = new Intent(getApplicationContext(), RecruiterSearch.class);
                    i.putExtra("user_id", recruiter_id);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    private void download(String user_id) {
        String fid = db.getUserFId(user_id);
        storageReference = firebaseStorage.getInstance().getReference();
        ref = storageReference.child("Uploads/").child(fid+".pdf");
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                downloadFiles(getApplicationContext(), fid,".pdf",DIRECTORY_DOWNLOADS,url);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "No CV available for download", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void downloadFiles(Context context, String fileName, String fileEx, String directory, String url) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, directory, fileName + fileEx);
        downloadManager.enqueue(request);
    }

    void getRate(String user_id, Database db){
        List<Float> ratings = db.getRatings(user_id);
        float rate = calculateAverageRating(ratings);
        rec_can_rate.setRating(rate);
        Toast.makeText(this, "rate is "+rate, Toast.LENGTH_SHORT).show();
    }

    void setRate(String user_id,String recruiter_id, Database db){
        float rating = rec_can_rate.getRating();
        String rate = String.valueOf(rating);
        boolean insert = db.insertRating(user_id,recruiter_id, rate);
        Toast.makeText(this, "rate is "+rate, Toast.LENGTH_SHORT).show();
    }


    public float calculateAverageRating(List<Float> ratings) {
        if (ratings.isEmpty()) {
            return 0.0f;
        }

        float sum = 0.0f;
        for (float rating : ratings) {
            sum += rating;
        }

        return sum / ratings.size();
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
            candidateprofile_about.setVisibility(View.VISIBLE);
        } else {
            candidateprofile_about.setVisibility(View.GONE);
        }
    }
    void setDetailsclicked(){
        setDetailsvisible(detailsvisible);
        if (!detailsvisible){
            detailsvisible = true;
        } else {
            detailsvisible = false;
        }
    }
    void setDetailsvisible(Boolean detailsvisible){
        if (!detailsvisible){
            candidateprofile_details_expand.setVisibility(View.VISIBLE);
        } else {
            candidateprofile_details_expand.setVisibility(View.GONE);
        }
    }
    void setSkillsclicked(){
        setSkillsvisible(skillsvisible);
        if (!skillsvisible){
            skillsvisible = true;
        } else {
            skillsvisible = false;
        }
    }
    void setSkillsvisible(Boolean skillsvisible){
        if (!skillsvisible){
            candidateprofile_skills.setVisibility(View.VISIBLE);
        } else {
            candidateprofile_skills.setVisibility(View.GONE);
        }
    }
    void DisplayData(String user_id){
        Cursor cursor = db.displayUserInfo(user_id);
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else{
            while (cursor.moveToNext()){
                String fname = cursor.getString(1);
                String lname  = cursor.getString(2);
                candidateprofile_name.setText(fname +" "+ lname);
                candidateprofile_email.setText(cursor.getString(4));
                candidateprofile_phone.setText(cursor.getString(5));
                candidateprofile_about.setText(cursor.getString(7));
                candidateprofile_grad_percent.setText(cursor.getInt(9) + "% Graduation"); // Convert integer to string
                candidateprofile_post_grad_percent.setText(cursor.getInt(10) + "% Post Graduation"); // Convert integer to string
                candidateprofile_work_exp.setText(cursor.getInt(11) + " years experience");
                String specs = cursor.getString(12);
                candidateprofile_skills.setText(cursor.getString(13));
                String gender = cursor.getString(3);
                candidateprofile_specialization.setText(specs + " • "+ gender);
                String funame = fname + " " + lname;
                toolbar_title.setText(funame);
                if (gender.equals("Male")){
                    profile_pic.setImageResource(R.drawable.employee);
                } else{
                    profile_pic.setImageResource(R.drawable.women);
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