package com.example.jobhub;

public class Constants {


    // Users table Columns
    static final String USERS_TABLE = "users";
    static final String USERS_TABLE_ID = "id";
    static final String USERS_TABLE_FNAME = "first_name";
    static final String USERS_TABLE_LNAME = "last_name";
    static final String USERS_TABLE_GENDER = "gender";
    static final String USERS_TABLE_EMAIL = "email";
    static final String USERS_TABLE_PHONE = "phone";
    static final String USERS_TABLE_PASSWORD = "password";
    static final String USERS_TABLE_ABOUT = "about";
    static final String USERS_TABLE_TYPE = "type";
    static final String USERS_TABLE_GRAD = "graduation_percentage";
    static final String USERS_TABLE_PGRAD = "post_graduation_percentage";
    static final String USERS_TABLE_EXP = "experience";
    static final String USERS_TABLE_SPECIALIZATION = "specialization";
    static final String USERS_TABLE_SKILLS = "skills";
    static final String USERS_TABLE_FID = "firebase_id";


    // Jobs Table Columns
    static final String JOBS_TABLE = "jobs";
    static final String JOBS_TABLE_ID = "id";
    static final String JOBS_TABLE_RID = "recruiter_id";
    static final String JOBS_TABLE_JNAME = "job_name";
    static final String JOBS_TABLE_CNAME = "company_name";
    static final String JOBS_TABLE_SDATE = "start_date";
    static final String JOBS_TABLE_DESC = "description";
    static final String JOBS_TABLE_POSITION = "position";
    static final String JOBS_TABLE_NO_VACANCY = "no_vacancy";
    static final String JOBS_TABLE_CATEGORY = "category";
    static final String JOBS_TABLE_MINEXP = "min_exp";
    static final String JOBS_TABLE_MAXEXP = "max_exp";
    static final String JOBS_TABLE_GRAD = "grad";
    static final String JOBS_TABLE_PGRAD = "pgrad";
    static final String JOBS_TABLE_SPECS = "specs";
    static final String JOBS_TABLE_SKILLS = "skills";
    static final String JOBS_TABLE_PDATE = "published_date";
    static final String JOBS_TABLE_LOCATION = "location";
    static final String JOBS_TABLE_MINPAY = "min_pay";
    static final String JOBS_TABLE_MAXPAY = "max_pay";
    static final String JOBS_TABLE_WORKTIME = "work_time";
    static final String JOBS_TABLE_WORKTYPE = "work_type";
    static final String JOBS_TABLE_RESP = "responsibilities";




    // Applications Table Columns
    static final String APPLICATIONS_TABLE = "applications";
    static final String APPLICATIONS_TABLE_ID = "id";
    static final String APPLICATIONS_TABLE_CID = "candidate_id";
    static final String APPLICATIONS_TABLE_JID = "job_id";
    static final String APPLICATIONS_TABLE_RID = "recruiter_id";
    static final String APPLICATIONS_TABLE_POSITION = "position";
    static final String APPLICATIONS_TABLE_COMPANY= "company";
    static final String APPLICATIONS_TABLE_STATUS = "status";
    static final String APPLICATIONS_TABLE_DATE = "date";


    // Views Table Columns
    static final String VIEWS_TABLE = "views";
    static final String VIEWS_TABLE_ID = "id";
    static final String VIEWS_TABLE_CID = "candidate_id";


    // Bookmarks Table Columns
    static final String BOOKMARKS_TABLE = "bookmarks";
    static final String BOOKMARKS_TABLE_ID = "id";
    static final String BOOKMARKS_TABLE_CID = "candidate_id";
    static final String BOOKMARKS_TABLE_JID = "job_id";
    static final String BOOKMARKS_TABLE_POSITION = "position";
    static final String BOOKMARKS_TABLE_CNAME= "company";
    static final String BOOKMARKS_TABLE_PDATE = "pdate";


    // Bookmarks Table Columns
    static final String CHATS_TABLE = "chats";
    static final String CHATS_TABLE_ID = "id";
    static final String CHATS_TABLE_CID = "candidate_id";
    static final String CHATS_TABLE_RID = "recruiter_id";
    static final String CHATS_TABLE_CNAME = "cname";
    static final String CHATS_TABLE_RNAME= "rname";



    // Bookmarks Table Columns
    static final String MESSAGES_TABLE = "messages";
    static final String MESSAGES_TABLE_ID = "id";
    static final String MESSAGES_TABLE_CID = "chat_id";
    static final String MESSAGES_TABLE_SID = "sender_id";
    static final String MESSAGES_TABLE_RID = "receiver_id";
    static final String MESSAGES_TABLE_MSG = "message";
    static final String MESSAGES_TABLE_DATE= "date";
    static final String MESSAGES_TABLE_TIME= "time";


    // Bookmarks Table Columns
    static final String RATINGS_TABLE = "ratings";
    static final String RATINGS_TABLE_ID = "id";
    static final String RATINGS_TABLE_CID = "candidate_id";
    static final String RATINGS_TABLE_RID = "recruiter_id";
    static final String RATINGS_TABLE_RATE = "rating";


}
