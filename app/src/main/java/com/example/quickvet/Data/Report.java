package com.example.quickvet.Data;

public class Report {

    private String DocID;
    private String ClientID;
    private String name;
    private String about;
    private String comment;

    public String getDocID() {
        return DocID;
    }

    public void setDocID(String docID) {
        DocID = docID;
    }

    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String clientID) {
        ClientID = clientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
