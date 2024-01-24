package com.example.jobhub.models;

public class Chat {
    private String cname;
    private String rname;
    private int cid;
    private int rid;
    private int id;

    public Chat(int id, int cid, int rid, String cname, String rname){

        this.cname = cname;
        this.rname = rname;
        this.cid = cid;
        this.rid = rid;
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
