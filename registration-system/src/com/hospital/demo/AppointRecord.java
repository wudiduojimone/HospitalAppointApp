package com.hospital.demo;

//挂号记录
public class AppointRecord {
    private String PatientID;
    private String PatientName;
    private String doctorID;
    private String DoctorName;
    private Department department;
    private String briefDescription;//病情简介


    public AppointRecord() {
    }

    public AppointRecord(String patientID, String patientName, String doctorID, String doctorName, Department department, String briefDescription) {
        PatientID = patientID;
        PatientName = patientName;
        this.doctorID = doctorID;
        DoctorName = doctorName;
        this.department = department;
        this.briefDescription = briefDescription;
    }

    public String getPatientID() {
        return PatientID;
    }

    public void setPatientID(String patientID) {
        PatientID = patientID;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }
}
