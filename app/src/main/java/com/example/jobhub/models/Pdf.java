package com.example.jobhub.models;

public class Pdf {
    private String url;
    private String Pdfname;
    private String cid;

    public Pdf(){}
    public Pdf(String url,String Pdfname,String cid){
        this.url = url;
        this.Pdfname = Pdfname;
        this.cid=cid;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPdfName() {
        return Pdfname;
    }

    public void setPdfName(String Pdfname) {
        this.Pdfname = Pdfname;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
