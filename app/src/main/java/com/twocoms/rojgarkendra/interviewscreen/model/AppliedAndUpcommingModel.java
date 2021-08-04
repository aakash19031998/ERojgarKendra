package com.twocoms.rojgarkendra.interviewscreen.model;

import android.content.Intent;

public class AppliedAndUpcommingModel {

    int appliedId;
    String scheduledDate;
    String interviewDate;
    String studentStatus;
    String clientName;
    String studentRemark;
    String clientRemark;
    String dateOFJoining;
    String appliedDate;
    String jobType ;
    String salary;
    String numberOfOpenPositions;
    String locationOfWork ;

    public String getVacancyTitle() {
        return vacancyTitle;
    }

    public void setVacancyTitle(String vacancyTitle) {
        this.vacancyTitle = vacancyTitle;
    }

    String vacancyTitle;


    public String getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(String appliedDate) {
        this.appliedDate = appliedDate;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getNumberOfOpenPositions() {
        return numberOfOpenPositions;
    }

    public void setNumberOfOpenPositions(String numberOfOpenPositions) {
        this.numberOfOpenPositions = numberOfOpenPositions;
    }

    public String getLocationOfWork() {
        return locationOfWork;
    }

    public void setLocationOfWork(String locationOfWork) {
        this.locationOfWork = locationOfWork;
    }


    public AppliedAndUpcommingModel() {
    }

    public AppliedAndUpcommingModel(int appliedId, String scheduledDate, String interviewDate, String studentStatus, String clientName, String studentRemark, String clientRemark, String dateOFJoining) {
        this.appliedId = appliedId;
        this.scheduledDate = scheduledDate;
        this.interviewDate = interviewDate;
        this.studentStatus = studentStatus;
        this.clientName = clientName;
        this.studentRemark = studentRemark;
        this.clientRemark = clientRemark;
        this.dateOFJoining = dateOFJoining;
    }

    public int getAppliedId() {
        return appliedId;
    }

    public void setAppliedId(int appliedId) {
        this.appliedId = appliedId;
    }

    public String getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(String scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(String interviewDate) {
        this.interviewDate = interviewDate;
    }

    public String getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(String studentStatus) {
        this.studentStatus = studentStatus;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getStudentRemark() {
        return studentRemark;
    }

    public void setStudentRemark(String studentRemark) {
        this.studentRemark = studentRemark;
    }

    public String getClientRemark() {
        return clientRemark;
    }

    public void setClientRemark(String clientRemark) {
        this.clientRemark = clientRemark;
    }

    public String getDateOFJoining() {
        return dateOFJoining;
    }

    public void setDateOFJoining(String dateOFJoining) {
        this.dateOFJoining = dateOFJoining;
    }
}
