package com.sacequeue.sace_queueapp;

/**
 * Created by Keji's Lab on 27/02/2018.
 */

public class StudenTransactionDetailsDataModel {

     String studentNumber;
     String queueNumber;
     String mobileNumber;

     public String getStudentNumber(){
         return studentNumber;
     }

     public String getQueueNumber(){
         return queueNumber;
     }
     public String getMobileNumber(){
         return mobileNumber;
     }

     public void setStudentNumber(String number){
         this.studentNumber = number;
     }
     public void setQueueNumber(String queNum){
         this.queueNumber = queNum;

     }

     public void setMobileNumber(String mNumber){
         this.mobileNumber = mNumber;
     }

}
