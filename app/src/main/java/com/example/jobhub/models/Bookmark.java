package com.example.jobhub.models;

public class Bookmark {
    private int id;
    private int cid;
    private int jid;
    private String position;
    private String cname;
    private String pdate;

    public Bookmark(int id, int cid, int jid, String position,String cname,String pdate){

        this.id = id;
        this.cid = cid;
        this.jid = jid;
        this.position = position;
        this.cname = cname;
        this.pdate = pdate;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }
}
