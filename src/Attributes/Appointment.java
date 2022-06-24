package Attributes;

import java.io.Serializable;

public class Appointment implements Serializable {
    private int AppNo;
    private int Doc;
    private int Patient;
    private String disease;
    private String AppDate;

    public Appointment(int appNo, int doc, int patient, String disease, String appDate) {
        AppNo = appNo;
        this.Doc = doc;
        this.Patient = patient;
        this.disease = disease;
        AppDate = appDate;
    }
    public Appointment(){

    }

    public int getAppNo() {
        return AppNo;
    }

    public void setAppNo(int appNo) {
        AppNo = appNo;
    }

    public int getDoc() {
        return Doc;
    }

    public void setDoc(int doc) {
        Doc = doc;
    }

    public int getPatient() {
        return Patient;
    }

    public void setPatient(int patient) {
        Patient = patient;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getAppDate() {
        return AppDate;
    }

    public void setAppDate(String appDate) {
        AppDate = appDate;
    }

    @Override
    public String toString() {
        return "Appointment : " + AppNo + "\n"
                + Doc + "\n"
                + Patient + "\n"+
                "Disease : " + disease + "\n" +
                "Appointment Date : " + AppDate;
    }
}
