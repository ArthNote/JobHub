package com.example.jobhub.models;

public class User {

    private int id;
    private String fname;
    private String lname;
    private String gender;
    private String email;
    private String phone;
    private String password;
    private String about;
    private String type;
    private int grad;
    private int pgard;
    private int exp;
    private String specs;
    private String skills;
    private String fid;
    private String status;

    public User (int id,String fname ,String lname,String gender ,String email,
                 String phone ,String password, String about ,String type,
                 int grad ,int pgard, int exp ,String specs,
                 String skills, String fid,String status){

        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.about = about;
        this.type = type;
        this.grad = grad;
        this.pgard = pgard;
        this.exp = exp;
        this.skills = skills;
        this.specs = specs;
        this.fid = fid;
        this.status=status;
    }
    public User(){}


    public int getid() {
        return id;
    }
    public void setid(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getGrad() {
        return grad;
    }

    public void setGrad(int grad) {
        this.grad = grad;
    }

    public int getPgard() {
        return pgard;
    }

    public void setPgard(int pgard) {
        this.pgard = pgard;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getSpecs() {
        return specs;
    }


    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



}
