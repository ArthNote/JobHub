package com.example.jobhub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private Context context;
    static final String DATABASE_NAME = "jobhub.db";
    static final int DATABASE_VERSION = 1;

    String jobs_query = "CREATE TABLE " + Constants.JOBS_TABLE + " ("
            + Constants.JOBS_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Constants.JOBS_TABLE_RID + " INTEGER, "
            + Constants.JOBS_TABLE_JNAME + " TEXT, "
            + Constants.JOBS_TABLE_CNAME + " TEXT, "
            + Constants.JOBS_TABLE_SDATE + " TEXT, "
            + Constants.JOBS_TABLE_DESC + " TEXT, "
            + Constants.JOBS_TABLE_POSITION + " TEXT, "
            + Constants.JOBS_TABLE_NO_VACANCY + " INTEGER, "
            + Constants.JOBS_TABLE_CATEGORY + " TEXT, "
            + Constants.JOBS_TABLE_MINEXP + " INTEGER, "
            + Constants.JOBS_TABLE_MAXEXP + " INTEGER, "
            + Constants.JOBS_TABLE_GRAD + " INTEGER, "
            + Constants.JOBS_TABLE_PGRAD + " INTEGER, "
            + Constants.JOBS_TABLE_SPECS + " TEXT, "
            + Constants.JOBS_TABLE_SKILLS + " TEXT, "
            + Constants.JOBS_TABLE_PDATE + " TEXT, "
            + Constants.JOBS_TABLE_LOCATION + " TEXT, "
            + Constants.JOBS_TABLE_MINPAY + " INTEGER, "
            + Constants.JOBS_TABLE_MAXPAY + " INTEGER, "
            + Constants.JOBS_TABLE_WORKTIME + " TEXT, "
            + Constants.JOBS_TABLE_WORKTYPE + " TEXT, "
            + Constants.JOBS_TABLE_RESP + " TEXT );";

    String applications_query = "CREATE TABLE " + Constants.APPLICATIONS_TABLE + " ("
            + Constants.APPLICATIONS_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Constants.APPLICATIONS_TABLE_CID + " INTEGER, "
            + Constants.APPLICATIONS_TABLE_JID + " INTEGER, "
            + Constants.APPLICATIONS_TABLE_RID + " INTEGER, "
            + Constants.APPLICATIONS_TABLE_POSITION + " TEXT, "
            + Constants.APPLICATIONS_TABLE_COMPANY + " TEXT, "
            + Constants.APPLICATIONS_TABLE_STATUS + " TEXT, "
            + Constants.APPLICATIONS_TABLE_DATE + " TEXT );";

    String users_query = "CREATE TABLE " + Constants.USERS_TABLE + " ("
            + Constants.USERS_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Constants.USERS_TABLE_FNAME + " TEXT, "
            + Constants.USERS_TABLE_LNAME + " TEXT, "
            + Constants.USERS_TABLE_GENDER + " TEXT, "
            + Constants.USERS_TABLE_EMAIL + " TEXT, "
            + Constants.USERS_TABLE_PHONE + " TEXT, "
            + Constants.USERS_TABLE_PASSWORD + " TEXT, "
            + Constants.USERS_TABLE_ABOUT + " TEXT, "
            + Constants.USERS_TABLE_TYPE + " TEXT, "
            + Constants.USERS_TABLE_GRAD + " INTEGER, "
            + Constants.USERS_TABLE_PGRAD + " INTEGER, "
            + Constants.USERS_TABLE_EXP + " INTEGER, "
            + Constants.USERS_TABLE_SPECIALIZATION + " TEXT, "
            + Constants.USERS_TABLE_SKILLS + " TEXT, "
            + Constants.USERS_TABLE_FID + " TEXT);";

    String views_query = "CREATE TABLE " + Constants.VIEWS_TABLE + " ("
            + Constants.VIEWS_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Constants.VIEWS_TABLE_CID + " INTEGER);";

    String bookmarks_query = "CREATE TABLE " + Constants.BOOKMARKS_TABLE + " ("
            + Constants.BOOKMARKS_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Constants.BOOKMARKS_TABLE_CID + " INTEGER, "
            + Constants.BOOKMARKS_TABLE_JID + " INTEGER, "
            + Constants.BOOKMARKS_TABLE_POSITION + " TEXT, "
            + Constants.BOOKMARKS_TABLE_CNAME + " TEXT, "
            + Constants.BOOKMARKS_TABLE_PDATE + " TEXT);";

    String chats_query = "CREATE TABLE " + Constants.CHATS_TABLE + " ("
            + Constants.CHATS_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Constants.CHATS_TABLE_CID + " INTEGER, "
            + Constants.CHATS_TABLE_RID + " INTEGER, "
            + Constants.CHATS_TABLE_CNAME + " TEXT, "
            + Constants.CHATS_TABLE_RNAME + " TEXT);";

    String messages_query = "CREATE TABLE " + Constants.MESSAGES_TABLE + " ("
            + Constants.MESSAGES_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Constants.MESSAGES_TABLE_CID + " INTEGER, "
            + Constants.MESSAGES_TABLE_SID + " INTEGER, "
            + Constants.MESSAGES_TABLE_RID + " INTEGER, "
            + Constants.MESSAGES_TABLE_MSG + " TEXT, "
            + Constants.MESSAGES_TABLE_DATE + " TEXT, "
            + Constants.MESSAGES_TABLE_TIME + " TEXT);";

    String ratings_query = "CREATE TABLE " + Constants.RATINGS_TABLE + " ("
            + Constants.RATINGS_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Constants.RATINGS_TABLE_CID + " INTEGER, "
            + Constants.RATINGS_TABLE_RID + " INTEGER, "
            + Constants.RATINGS_TABLE_RATE + " TEXT);";


    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(users_query);
        db.execSQL(jobs_query);
        db.execSQL(applications_query);
        db.execSQL(views_query);
        db.execSQL(bookmarks_query);
        db.execSQL(chats_query);
        db.execSQL(messages_query);
        db.execSQL(ratings_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ Constants.USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.JOBS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.APPLICATIONS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.BOOKMARKS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.VIEWS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.CHATS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.MESSAGES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.RATINGS_TABLE);
    }

    public Boolean register(String fname, String lname, String gender, String email,
                         String phone, String password, String about, String type,
                         int grad, int post_grad, int exp, String specs,
                         String skills, String fid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.USERS_TABLE_FNAME, fname);
        cv.put(Constants.USERS_TABLE_LNAME, lname);
        cv.put(Constants.USERS_TABLE_GENDER, gender);
        cv.put(Constants.USERS_TABLE_EMAIL, email);
        cv.put(Constants.USERS_TABLE_PHONE, phone);
        cv.put(Constants.USERS_TABLE_PASSWORD, password);
        cv.put(Constants.USERS_TABLE_ABOUT, about);
        cv.put(Constants.USERS_TABLE_TYPE, type);
        cv.put(Constants.USERS_TABLE_GRAD, grad);
        cv.put(Constants.USERS_TABLE_PGRAD, post_grad);
        cv.put(Constants.USERS_TABLE_EXP, exp);
        cv.put(Constants.USERS_TABLE_SPECIALIZATION, specs);
        cv.put(Constants.USERS_TABLE_SKILLS, skills);
        cv.put(Constants.USERS_TABLE_FID, fid);
        long result = db.insert(Constants.USERS_TABLE, null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            return false;
        } else{
            Toast.makeText(context, "Good", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public Boolean checkEmail(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + Constants.USERS_TABLE + " WHERE " + Constants.USERS_TABLE_EMAIL + " =?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        if (cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkLoginCombo(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + Constants.USERS_TABLE + " WHERE " + Constants.USERS_TABLE_EMAIL + " =? AND " + Constants.USERS_TABLE_PASSWORD + " =?";
        Cursor cursor = db.rawQuery(query, new String[]{email,password});
        if (cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }

    public String getUserType(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+ Constants.USERS_TABLE_TYPE +" FROM " + Constants.USERS_TABLE + " WHERE " + Constants.USERS_TABLE_EMAIL + " =? AND " + Constants.USERS_TABLE_PASSWORD + " =?";
        Cursor cursor = db.rawQuery(query, new String[]{email,password});
        String type = null;
        if (cursor.moveToFirst()){
            type = cursor.getString(0);
        }
        return type;
    }

    public String getUserId(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+ Constants.USERS_TABLE_ID +" FROM " + Constants.USERS_TABLE + " WHERE " + Constants.USERS_TABLE_EMAIL +" =?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        String id = null;
        if (cursor.moveToNext()){
            id = cursor.getString(0);
        }
        return id;
    }

    public String getUserFId(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+ Constants.USERS_TABLE_FID +" FROM " + Constants.USERS_TABLE + " WHERE " + Constants.USERS_TABLE_ID +" =?";
        Cursor cursor = db.rawQuery(query, new String[]{id});
        String fid = null;
        if (cursor.moveToNext()){
            fid = cursor.getString(0);
        }
        return fid;
    }


    public String getUserName(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Constants.USERS_TABLE + " WHERE " + Constants.USERS_TABLE_FID +" =?";
        Cursor cursor = db.rawQuery(query, new String[]{id});
        String name = null;
        if (cursor.moveToNext()){
            String fname = cursor.getString(1);
            String lname = cursor.getString(2);
            name = fname+" "+lname;
        }
        return name;
    }

    public Cursor displayUserInfo(String user_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Constants.USERS_TABLE + " WHERE " + Constants.USERS_TABLE_ID + " =?";
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, new String[]{user_id});
        } else{
            Toast.makeText(context, "No db connection", Toast.LENGTH_SHORT).show();
        }
        return  cursor;
    }

    public Boolean updateCandidateInfo(String user_id, String fname, String lname, String gender,
                                  String phone, String about,
                                  String grad, String post_grad, String exp, String specs,
                                  String skills){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.USERS_TABLE_FNAME, fname);
        cv.put(Constants.USERS_TABLE_LNAME, lname);
        cv.put(Constants.USERS_TABLE_GENDER, gender);
        cv.put(Constants.USERS_TABLE_PHONE, phone);
        cv.put(Constants.USERS_TABLE_ABOUT, about);
        cv.put(Constants.USERS_TABLE_GRAD, grad);
        cv.put(Constants.USERS_TABLE_PGRAD, post_grad);
        cv.put(Constants.USERS_TABLE_EXP, exp);
        cv.put(Constants.USERS_TABLE_SPECIALIZATION, specs);
        cv.put(Constants.USERS_TABLE_SKILLS, skills);
        long result = db.update(Constants.USERS_TABLE, cv, Constants.USERS_TABLE_ID + "=?", new String[]{user_id});
        if (result == -1){
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(context, "Profile Saved", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public Boolean updateRecruiterInfo(String user_id, String fname, String lname, String gender,
                                       String phone, String about){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.USERS_TABLE_FNAME, fname);
        cv.put(Constants.USERS_TABLE_LNAME, lname);
        cv.put(Constants.USERS_TABLE_GENDER, gender);
        cv.put(Constants.USERS_TABLE_PHONE, phone);
        cv.put(Constants.USERS_TABLE_ABOUT, about);
        long result = db.update(Constants.USERS_TABLE, cv, Constants.USERS_TABLE_ID + "=?", new String[]{user_id});
        if (result == -1){
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(context, "Profile Saved", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public Cursor DisplayAllCandidates(){
        String query = "SELECT * FROM "+ Constants.USERS_TABLE + " WHERE " + Constants.USERS_TABLE_TYPE + " =?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, new String[]{"Candidate"});
        } return  cursor;
    }

    public Cursor DisplayAllJobs(String user_id){
        String query = "SELECT * FROM "+ Constants.JOBS_TABLE + " WHERE " + Constants.JOBS_TABLE_RID + " =?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, new String[]{user_id});
        } return  cursor;
    }

    public Cursor DisplayJobData(String job_id){
        String query = "SELECT * FROM "+ Constants.JOBS_TABLE + " WHERE " + Constants.JOBS_TABLE_ID + " =?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, new String[]{job_id});
        } return  cursor;
    }


    public Cursor DisplayJobs(){
        String query = "SELECT * FROM "+ Constants.JOBS_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        } return  cursor;
    }

    public Boolean addJob(int recruiter_id, String jname ,String cname,String sdate ,String desc,
                          String position ,int no_vacancy, String category , int min_exp,
                          int max_exp ,int grad, int pgrad ,String specs,
                          String skills, String pdate, String location, int min_pay, int max_pay, String work_time,
                          String work_type, String resp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.JOBS_TABLE_RID, recruiter_id);
        cv.put(Constants.JOBS_TABLE_JNAME, jname);
        cv.put(Constants.JOBS_TABLE_CNAME, cname);
        cv.put(Constants.JOBS_TABLE_SDATE, sdate);
        cv.put(Constants.JOBS_TABLE_DESC, desc);
        cv.put(Constants.JOBS_TABLE_POSITION, position);
        cv.put(Constants.JOBS_TABLE_NO_VACANCY, no_vacancy);
        cv.put(Constants.JOBS_TABLE_CATEGORY, category);
        cv.put(Constants.JOBS_TABLE_MINEXP, min_exp);
        cv.put(Constants.JOBS_TABLE_MAXEXP, max_exp);
        cv.put(Constants.JOBS_TABLE_GRAD, grad);
        cv.put(Constants.JOBS_TABLE_PGRAD, pgrad);
        cv.put(Constants.JOBS_TABLE_SPECS, specs);
        cv.put(Constants.JOBS_TABLE_SKILLS, skills);
        cv.put(Constants.JOBS_TABLE_PDATE, pdate);
        cv.put(Constants.JOBS_TABLE_LOCATION,location);
        cv.put(Constants.JOBS_TABLE_MINPAY, min_pay);
        cv.put(Constants.JOBS_TABLE_MAXPAY, max_pay);
        cv.put(Constants.JOBS_TABLE_WORKTIME, work_time);
        cv.put(Constants.JOBS_TABLE_WORKTYPE, work_type);
        cv.put(Constants.JOBS_TABLE_RESP, resp);
        long result = db.insert(Constants.JOBS_TABLE, null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            return false;
        } else{
            Toast.makeText(context, "Good", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public Boolean applyToJob(int candidate_id, int job_id ,int recruiter_id, String position ,String company,
                          String status, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.APPLICATIONS_TABLE_CID, candidate_id);
        cv.put(Constants.APPLICATIONS_TABLE_JID, job_id);
        cv.put(Constants.APPLICATIONS_TABLE_RID, recruiter_id);
        cv.put(Constants.APPLICATIONS_TABLE_POSITION, position);
        cv.put(Constants.APPLICATIONS_TABLE_COMPANY, company);
        cv.put(Constants.APPLICATIONS_TABLE_STATUS, status);
        cv.put(Constants.APPLICATIONS_TABLE_DATE, date);
        long result = db.insert(Constants.APPLICATIONS_TABLE, null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            return false;
        } else{
            Toast.makeText(context, "Good", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public Boolean checkIfApplied(String job_id, String candidate_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + Constants.APPLICATIONS_TABLE + " WHERE " + Constants.APPLICATIONS_TABLE_JID + " =? AND " + Constants.APPLICATIONS_TABLE_CID + " =?";
        Cursor cursor = db.rawQuery(query, new String[]{job_id,candidate_id});
        if (cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }

    public Boolean editJob(String id, String jname ,String cname,String sdate ,String desc,
                          String position ,int no_vacancy, String category , int min_exp,
                          int max_exp ,int grad, int pgrad ,String specs,
                          String skills, String location, int min_pay, int max_pay, String work_time,
                           String work_type, String resp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.JOBS_TABLE_JNAME, jname);
        cv.put(Constants.JOBS_TABLE_CNAME, cname);
        cv.put(Constants.JOBS_TABLE_SDATE, sdate);
        cv.put(Constants.JOBS_TABLE_DESC, desc);
        cv.put(Constants.JOBS_TABLE_POSITION, position);
        cv.put(Constants.JOBS_TABLE_NO_VACANCY, no_vacancy);
        cv.put(Constants.JOBS_TABLE_CATEGORY, category);
        cv.put(Constants.JOBS_TABLE_MINEXP, min_exp);
        cv.put(Constants.JOBS_TABLE_MAXEXP, max_exp);
        cv.put(Constants.JOBS_TABLE_GRAD, grad);
        cv.put(Constants.JOBS_TABLE_PGRAD, pgrad);
        cv.put(Constants.JOBS_TABLE_SPECS, specs);
        cv.put(Constants.JOBS_TABLE_SKILLS, skills);
        cv.put(Constants.JOBS_TABLE_LOCATION,location);
        cv.put(Constants.JOBS_TABLE_MINPAY, min_pay);
        cv.put(Constants.JOBS_TABLE_MAXPAY, max_pay);
        cv.put(Constants.JOBS_TABLE_WORKTIME, work_time);
        cv.put(Constants.JOBS_TABLE_WORKTYPE, work_type);
        cv.put(Constants.JOBS_TABLE_RESP, resp);
        long result = db.update(Constants.JOBS_TABLE, cv, Constants.JOBS_TABLE_ID + "=?", new String[]{id});
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            return false;
        } else{
            Toast.makeText(context, "Good", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public Boolean deleteJob(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(Constants.JOBS_TABLE,Constants.JOBS_TABLE_ID + "=?", new String[]{id});
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            return false;
        } else{
            Toast.makeText(context, "Good", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public Boolean viewProfile(int candidate_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.VIEWS_TABLE_CID, candidate_id);
        long result = db.insert(Constants.VIEWS_TABLE, null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            return false;
        } else{
            Toast.makeText(context, "Good", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public int DisplayViews(String user_id){
        String query = "SELECT COUNT(*) FROM "+ Constants.VIEWS_TABLE + " WHERE " + Constants.VIEWS_TABLE_CID + " =?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{user_id});
        int count = 0;
        if (cursor != null){
            if (cursor.moveToFirst()){
                count = cursor.getInt(0);
            } cursor.close();
        } return  count;
    }

    public int DisplayApplicationCount(String user_id){
        String query = "SELECT COUNT(*) FROM "+ Constants.APPLICATIONS_TABLE + " WHERE " + Constants.APPLICATIONS_TABLE_CID + " =?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{user_id});
        int count = 0;
        if (cursor != null){
            if (cursor.moveToFirst()){
                count = cursor.getInt(0);
            } cursor.close();
        } return  count;
    }

    public Cursor DisplayApplications(String user_id ,String status){
        String query = "SELECT * FROM "+ Constants.APPLICATIONS_TABLE + " WHERE " + Constants.APPLICATIONS_TABLE_CID + " =? AND " + Constants.APPLICATIONS_TABLE_STATUS + " =?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, new String[]{user_id, status});
        } return  cursor;
    }

    public Cursor DisplayRecApplications(String user_id ,String status){
        String query = "SELECT * FROM "+ Constants.APPLICATIONS_TABLE + " WHERE " + Constants.APPLICATIONS_TABLE_RID + " =? AND " + Constants.APPLICATIONS_TABLE_STATUS + " =?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, new String[]{user_id, status});
        } return  cursor;
    }

    public Cursor DisplayRecApplicationsd(String job_id ,String status){
        String query = "SELECT * FROM "+ Constants.APPLICATIONS_TABLE + " WHERE " + Constants.APPLICATIONS_TABLE_JID + " =? AND " + Constants.APPLICATIONS_TABLE_STATUS + " =?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, new String[]{job_id, status});
        } return  cursor;
    }

    public Cursor DisplayApplicationData(String app_id){
        String query = "SELECT * FROM "+ Constants.APPLICATIONS_TABLE + " WHERE " + Constants.APPLICATIONS_TABLE_ID + " =?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, new String[]{app_id});
        } return  cursor;
    }

    public Boolean deleteApplication(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(Constants.APPLICATIONS_TABLE,Constants.APPLICATIONS_TABLE_ID + "=?", new String[]{id});
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            return false;
        } else{
            Toast.makeText(context, "Good", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
    public Boolean updateApplicationStatus(String app_id, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.APPLICATIONS_TABLE_STATUS, status);
        long result = db.update(Constants.APPLICATIONS_TABLE, cv, Constants.APPLICATIONS_TABLE_ID + " =?", new String[]{app_id});
        if (result == -1){
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(context, "Update Saved", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public Boolean bookmarkJob(String user_id, String job_id, String position, String company, String pdate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.BOOKMARKS_TABLE_CID, user_id);
        cv.put(Constants.BOOKMARKS_TABLE_JID, job_id);
        cv.put(Constants.BOOKMARKS_TABLE_POSITION, position);
        cv.put(Constants.BOOKMARKS_TABLE_CNAME, company);
        cv.put(Constants.BOOKMARKS_TABLE_PDATE, pdate);
        long result = db.insert(Constants.BOOKMARKS_TABLE, null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            return false;
        } else{
            Toast.makeText(context, "Good", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public Cursor displayBookmarks(String user_id){
        String query = "SELECT * FROM "+ Constants.BOOKMARKS_TABLE + " WHERE " + Constants.BOOKMARKS_TABLE_CID + " =?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, new String[]{user_id});
        } return  cursor;
    }

    public String getJobPdate(String job_id){
        String query = "SELECT "+ Constants.JOBS_TABLE_PDATE + " FROM "+ Constants.JOBS_TABLE + " WHERE " + Constants.JOBS_TABLE_ID + " =?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{job_id});
        String pdate = null;
        if (cursor.moveToFirst()){
            pdate = cursor.getString(0);
        } return  pdate;
    }

    public Boolean checkIfBookmarked(String job_id, String user_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + Constants.BOOKMARKS_TABLE + " WHERE " + Constants.BOOKMARKS_TABLE_JID + " =? AND " + Constants.BOOKMARKS_TABLE_CID + " =?";
        Cursor cursor = db.rawQuery(query, new String[]{job_id,user_id});
        if (cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }

    public Boolean deleteBookmark(String user_id, String job_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(Constants.BOOKMARKS_TABLE,Constants.BOOKMARKS_TABLE_CID + "=? AND "+ Constants.BOOKMARKS_TABLE_JID + " =?", new String[]{user_id, job_id});
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            return false;
        } else{
            Toast.makeText(context, "Good", Toast.LENGTH_SHORT).show();
            return true;
        }
    }


    public int CountApplicants(String job_id){
        String query = "SELECT COUNT(*) FROM "+ Constants.APPLICATIONS_TABLE + " WHERE " + Constants.APPLICATIONS_TABLE_JID + " =?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{job_id});
        int count = 0;
        if (cursor != null){
            if (cursor.moveToFirst()){
                count = cursor.getInt(0);
            } cursor.close();
        } return  count;
    }


    public Boolean startChat(String candidate_id, String recruiter_id, String cname, String rname){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.CHATS_TABLE_CID, candidate_id);
        cv.put(Constants.CHATS_TABLE_RID, recruiter_id);
        cv.put(Constants.CHATS_TABLE_RNAME, rname);
        cv.put(Constants.CHATS_TABLE_CNAME, cname);
        long result = db.insert(Constants.CHATS_TABLE, null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            return false;
        } else{
            Toast.makeText(context, "Good", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public Boolean checkIfChatExists(String recruiter_id, String candidate_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + Constants.CHATS_TABLE + " WHERE " + Constants.CHATS_TABLE_RID + " =? AND " + Constants.CHATS_TABLE_CID + " =?";
        Cursor cursor = db.rawQuery(query, new String[]{recruiter_id,candidate_id});
        if (cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }

    public Cursor dislayRChats(String user_id){
        String query = "SELECT * FROM "+ Constants.CHATS_TABLE + " WHERE "+ Constants.CHATS_TABLE_RID + " =?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, new String[]{user_id});
        } return  cursor;
    }
    public Cursor dislayCChats(String user_id){
        String query = "SELECT * FROM "+ Constants.CHATS_TABLE + " WHERE "+ Constants.CHATS_TABLE_CID + " =?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, new String[]{user_id});
        } return  cursor;
    }

    public String getUserGender(String user_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+ Constants.USERS_TABLE_GENDER +" FROM " + Constants.USERS_TABLE + " WHERE " + Constants.USERS_TABLE_ID +" =?";
        Cursor cursor = db.rawQuery(query, new String[]{user_id});
        String gender = null;
        if (cursor.moveToNext()){
            gender = cursor.getString(0);
        }
        return gender;
    }

    /*public void setRate(String user_id, String rate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.USERS_TABLE_RATE, rate);
        long result = db.update(Constants.USERS_TABLE, cv,  Constants.USERS_TABLE_ID + " =?", new String[]{user_id});
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(context, "Good", Toast.LENGTH_SHORT).show();
        }
    }

    public String getRate(String user_id){
        String query = "SELECT "+ Constants.USERS_TABLE_RATE + " FROM "+ Constants.USERS_TABLE + " WHERE "+ Constants.USERS_TABLE_ID + " =?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{user_id});
        String rate = null;
        if (cursor != null){
            if (cursor.moveToFirst()){
                rate = cursor.getString(0);
            } cursor.close();
        } return  rate;
    }*/


    public Boolean insertRating(String cid, String rid, String rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.RATINGS_TABLE_CID, cid);
        values.put(Constants.RATINGS_TABLE_RID, rid);
        values.put(Constants.RATINGS_TABLE_RATE, rating);
        long result = db.insert(Constants.RATINGS_TABLE, null, values);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            return false;
        } else{
            Toast.makeText(context, "Good", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public List<Float> getRatings(String cid) {
        List<Float> ratings = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + Constants.RATINGS_TABLE_RATE + " FROM " + Constants.RATINGS_TABLE + " WHERE " + Constants.RATINGS_TABLE_CID + " =?";
        Cursor cursor = db.rawQuery(query, new String[]{cid});
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                float rating = Float.parseFloat(cursor.getString(0));
                ratings.add(rating);
            }
        }
        return ratings;
    }




}
