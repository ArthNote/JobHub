package com.example.jobhub.models;

public class Application {

    private int id;
    private int cid;
    private int jid;
    private int rid;
    private String position;
    private String company;
    private String status;
    private String date;

    public Application(int id, int cid, int jid, int rid, String position, String company, String status, String date){

        this.id = id;
        this.cid = cid;
        this.jid = jid;
        this.rid = rid;
        this.position = position;
        this.company = company;
        this.status = status;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getJid() {
        return jid;
    }

    public void setJid(int jid) {
        this.jid = jid;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
