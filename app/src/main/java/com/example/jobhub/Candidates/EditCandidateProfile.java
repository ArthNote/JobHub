package com.example.jobhub.Candidates;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.MotionEvent;
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
import com.example.jobhub.models.Pdf;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class EditCandidateProfile extends AppCompatActivity {
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    DatabaseReference reference1;
    StorageReference storage;
    Button edituserprofile_save_btn,edituserprofile_upload;
    ImageView toolbar_arrow;
    TextView toolbar_title;
    EditText edituserprofile_fname, edituserprofile_lname,edituserprofile_pdfname
            ,edituserprofile_phone, edituserprofile_about, edituserprofile_grad_percent, edituserprofile_post_grad_percent
            , edituserprofile_work_exp, edituserprofile_specialization, edituserprofile_skills;
    RadioGroup edituserprofile_gender;
    RadioButton selected_gender, edituserprofile_male, edituserprofile_female;
    Database db;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_candidate_profile);
        toolbar_arrow = findViewById(R.id.toolbar_arrow);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Edit Profile");
        Intent i = getIntent();
        String user_id = i.getStringExtra("user_id");
        db = new Database(this);

        edituserprofile_upload = findViewById(R.id.edituserprofile_upload);
        edituserprofile_pdfname = findViewById(R.id.edituserprofile_pdfname);
        edituserprofile_fname = findViewById(R.id.edituserprofile_fname);
        edituserprofile_lname = findViewById(R.id.edituserprofile_lname);
        edituserprofile_phone = findViewById(R.id.edituserprofile_phone);
        edituserprofile_about = findViewById(R.id.edituserprofile_about);
        edituserprofile_grad_percent = findViewById(R.id.edituserprofile_grad_percent);
        edituserprofile_post_grad_percent = findViewById(R.id.edituserprofile_post_grad_percent);
        edituserprofile_work_exp = findViewById(R.id.edituserprofile_work_exp);
        edituserprofile_specialization = findViewById(R.id.edituserprofile_specialization);
        edituserprofile_skills = findViewById(R.id.edituserprofile_skills);
        edituserprofile_save_btn = findViewById(R.id.edituserprofile_save_btn);
        edituserprofile_male = findViewById(R.id.edituserprofile_male);
        edituserprofile_female = findViewById(R.id.edituserprofile_female);
        edituserprofile_gender = findViewById(R.id.edituserprofile_gender);
        Toast.makeText(this, "id is= "+user_id, Toast.LENGTH_SHORT).show();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        storage = FirebaseStorage.getInstance().getReference();
        reference1 = FirebaseDatabase.getInstance().getReference("Uploads");
        displayData(user_id);

        edituserprofile_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFiles();
            }
        });

        edituserprofile_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = edituserprofile_fname.getText().toString().trim();
                String lname = edituserprofile_lname.getText().toString().trim();
                String phone = edituserprofile_phone.getText().toString().trim();
                String about = edituserprofile_about.getText().toString().trim();
                String skills  = edituserprofile_skills.getText().toString().trim();
                String grad = edituserprofile_grad_percent.getText().toString().trim();
                String post_grad = edituserprofile_post_grad_percent.getText().toString().trim();
                String specs = edituserprofile_specialization.getText().toString().trim();
                String exp = edituserprofile_work_exp.getText().toString().trim();
                int gender_id = edituserprofile_gender.getCheckedRadioButtonId();

                if (fname.equals("") || lname.equals("") || phone.equals("") || about.equals("") || gender_id ==-1
                        || skills.equals("") || specs.equals("") || grad.equals("")  || exp.equals("") ){
                    Toast.makeText(EditCandidateProfile.this, "All fields are Empty", Toast.LENGTH_SHORT).show();
                } else{
                    selected_gender = findViewById(gender_id);
                    String gender = selected_gender.getText().toString().trim();
                    Boolean update = db.updateCandidateInfo(user_id, fname, lname, gender, phone, about, grad, post_grad, exp, specs, skills );
                    if (update == true){
                        Toast.makeText(getApplicationContext(), "Profile updated Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Profile update Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        edituserprofile_skills.addTextChangedListener(new TextWatcher() {
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
        edituserprofile_skills.setOnKeyListener(new View.OnKeyListener() {
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
                Intent i = new Intent(getApplicationContext(), UserProfile.class);
                i.putExtra("user_id", user_id);
                startActivity(i);
                finish();
            }
        });
    }

    private void selectFiles() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select PDF File"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            UploadFiles(data.getData());
        }
    }

    private void UploadFiles(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading..");
        progressDialog.show();
        String cid = firebaseUser.getUid();
        StorageReference reference2 = storage.child("Uploads/"+cid+".pdf");
        reference2.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                uriTask.addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri url = task.getResult();
                            Pdf pdf = new Pdf(url.toString(), edituserprofile_pdfname.getText().toString().trim(), cid);
                            reference1.child(reference1.push().getKey()).setValue(pdf);
                            Toast.makeText(EditCandidateProfile.this, "PDF Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else {
                            // Handle the error case here
                            Toast.makeText(EditCandidateProfile.this, "PDF Upload Failed", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded:"+(int)progress+"%");
            }
        });
    }

    private void checkIfPdfExists(String cid) {
        DatabaseReference pdfReference = FirebaseDatabase.getInstance().getReference("Uploads");

        pdfReference.orderByChild("cid").equalTo(cid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Pdf pdf = dataSnapshot.getValue(Pdf.class);
                    // Update UI with PDF information, e.g., set text in fields
                    edituserprofile_pdfname.setText(pdf.getPdfName());
                    // Add more fields as needed
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }


    private void insertBulletPoint() {
        // Get the current text
        Editable editable = edituserprofile_skills.getText();
        int start = edituserprofile_skills.getSelectionStart();
        int end = edituserprofile_skills.getSelectionEnd();

        // Insert a bullet point at the beginning if not already present
        if (editable.length() == 0 || editable.charAt(0) != '\u2022') {
            editable.insert(0, "\u2022 ");
        } else if (start == 0 || (start > 0 && editable.charAt(start - 1) == '\n')) {
            // Insert a bullet point at the cursor position
            editable.replace(start, end, "\n\u2022 ");
            edituserprofile_skills.setSelection(edituserprofile_skills.getText().length());
        } else {
            // Allow the user to go to the next line without a bullet point
            editable.insert(start, "\n\u2022");
        }
    }

    void displayData(String user_id){
        Cursor cursor = db.displayUserInfo(user_id);
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else{
            while (cursor.moveToNext()){
                edituserprofile_fname.setText(cursor.getString(1));
                edituserprofile_lname.setText(cursor.getString(2));
                edituserprofile_phone.setText(cursor.getString(5));
                edituserprofile_about.setText(cursor.getString(7));
                edituserprofile_grad_percent.setText(String.valueOf(cursor.getInt(9))); // Convert integer to string
                edituserprofile_post_grad_percent.setText(String.valueOf(cursor.getInt(10))); // Convert integer to string
                edituserprofile_work_exp.setText(String.valueOf(cursor.getInt(11)));
                edituserprofile_specialization.setText(cursor.getString(12));
                edituserprofile_skills.setText(cursor.getString(13));
                String gender = cursor.getString(3);
                if (gender.equals("Male")){
                    edituserprofile_male.setChecked(true);
                } else{
                    edituserprofile_female.setChecked(true);
                }
                String cid = firebaseUser.getUid();
                checkIfPdfExists(cid);
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