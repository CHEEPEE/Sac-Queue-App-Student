package com.saccashierqueue.sace_queuetablekios.transactionSelectionActivty;

/**
 * Created by Keji's Lab on 19/02/2018.
 */

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Keji's Lab on 26/11/2017.
 */

public class StudentTransactionDetailsDataMapModel {

        public String studentNumber;
        public String queueNumber;
        public String mobileNumber;

    public StudentTransactionDetailsDataMapModel(){


    }
    public StudentTransactionDetailsDataMapModel(String studentNumber, String queNumber, String mobileNumber){
        this.studentNumber  =studentNumber;
        this.queueNumber = queNumber;
        this.mobileNumber = mobileNumber;
    }
    @Exclude
    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();

        result.put("studentNumber",studentNumber);
        result.put("queueNumber",queueNumber);
        result.put("mobileNumber",mobileNumber);
        return result;
    }
}
