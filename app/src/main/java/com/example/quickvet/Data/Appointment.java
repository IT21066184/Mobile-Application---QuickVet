package com.example.quickvet.Data;

public class Appointment {

    private String ClientID;
    private String DoctorID;
    private String Name;
    private String Symptoms;
    private String Type;
    private String Gender;
    private String Date;

    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String clientID) {
        ClientID = clientID;
    }

    public String getDoctorID() {
        return DoctorID;
    }

    public void setDoctorID(String doctorID) {
        DoctorID = doctorID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSymptoms() {
        return Symptoms;
    }

    public void setSymptoms(String symptoms) {
        Symptoms = symptoms;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
