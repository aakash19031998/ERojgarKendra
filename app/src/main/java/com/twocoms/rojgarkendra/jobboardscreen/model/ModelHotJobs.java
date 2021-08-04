package com.twocoms.rojgarkendra.jobboardscreen.model;

 public class ModelHotJobs {

     int id;
     String salary;
     String jobTypes;
     String clientName;
     String numberOpenings;
     String dates;
     String location;
     String vacancyTitle = "";

     public ModelHotJobs() {
     }



     public ModelHotJobs(int id, String salary, String jobTypes, String clientName, String numberOpenings, String dates, String location) {
         this.id = id;
         this.salary = salary;
         this.jobTypes = jobTypes;
         this.clientName = clientName;
         this.numberOpenings = numberOpenings;
         this.dates = dates;
         this.location = location;
         this.vacancyTitle = location;
     }

     public int getId() {
         return id;
     }

     public void setId(int id) {
         this.id = id;
     }

     public String getSalary() {
         return salary;
     }

     public void setSalary(String salary) {
         this.salary = salary;
     }

     public String getJobTypes() {
         return jobTypes;
     }

     public void setJobTypes(String jobTypes) {
         this.jobTypes = jobTypes;
     }

     public String getClientName() {
         return clientName;
     }

     public void setClientName(String clientName) {
         this.clientName = clientName;
     }

     public String getNumberOpenings() {
         return numberOpenings;
     }

     public void setNumberOpenings(String numberOpenings) {
         this.numberOpenings = numberOpenings;
     }

     public String getDates() {
         return dates;
     }

     public void setDates(String dates) {
         this.dates = dates;
     }

     public String getLocation() {
         return location;
     }

     public void setLocation(String location) {
         this.location = location;
     }

     public String getVacancyTitle() {
         return vacancyTitle;
     }

     public void setVacancyTitle(String vacancyTitle) {
         this.vacancyTitle = vacancyTitle;
     }
 }
