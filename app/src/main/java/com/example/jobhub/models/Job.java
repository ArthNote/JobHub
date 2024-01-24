package com.example.jobhub.models;

public class Job {
    private int id;
    private int recruiter_id;
    private String jname;
    private String cname;
    private String sdate;
    private String desc;
    private String position;
    private int no_vacancy;
    private String category;
    private int min_exp;
    private int max_exp;
    private int grad;
    private int pgrad;
    private String specs;
    private String skills;
    private String pdate;
    private String location;
    private int min_pay;
    private int max_pay;
    private String worktime;
    private String worktype;
    private String resp;

    public Job (int id, int recruiter_id, String jname ,String cname,String sdate ,String desc,
                 String position ,int no_vacancy, String category , int min_exp,
                 int max_exp ,int grad, int pgrad ,String specs,
                 String skills, String pdate, String location ,int min_pay ,int max_pay ,String worktime ,String worktype ,String resp){

        this.id = id;
        this.recruiter_id = recruiter_id;
        this.jname = jname;
        this.cname = cname;
        this.sdate = sdate;
        this.desc = desc;
        this.position = position;
        this.no_vacancy = no_vacancy;
        this.category = category;
        this.min_exp = min_exp;
        this.max_exp = max_exp;
        this.grad = grad;
        this.pgrad = pgrad;
        this.specs = specs;
        this.skills = skills;
        this.pdate = pdate;
        this.location = location;
        this.min_pay = min_pay;
        this.max_pay = max_pay;
        this.worktime = worktime;
        this.resp = resp;
        this.worktype = worktype;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJname() {
        return jname;
    }

    public void setJname(String jname) {
        this.jname = jname;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getNo_vacancy() {
        return no_vacancy;
    }

    public void setNo_vacancy(int no_vacancy) {
        this.no_vacancy = no_vacancy;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getMin_exp() {
        return min_exp;
    }

    public void setMin_exp(int min_exp) {
        this.min_exp = min_exp;
    }

    public int getGrad() {
        return grad;
    }

    public void setGrad(int grad) {
        this.grad = grad;
    }

    public int getMax_exp() {
        return max_exp;
    }

    public void setMax_exp(int max_exp) {
        this.max_exp = max_exp;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public int getPgrad() {
        return pgrad;
    }

    public void setPgrad(int pgrad) {
        this.pgrad = pgrad;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }

    public int getRecruiter_id() {
        return recruiter_id;
    }

    public void setRecruiter_id(int recruiter_id) {
        this.recruiter_id = recruiter_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMin_pay() {
        return min_pay;
    }

    public void setMin_pay(int min_pay) {
        this.min_pay = min_pay;
    }

    public int getMax_pay() {
        return max_pay;
    }

    public void setMax_pay(int max_pay) {
        this.max_pay = max_pay;
    }

    public String getWorktime() {
        return worktime;
    }

    public void setWorktime(String worktime) {
        this.worktime = worktime;
    }

    public String getWorktype() {
        return worktype;
    }

    public void setWorktype(String worktype) {
        this.worktype = worktype;
    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }
}
