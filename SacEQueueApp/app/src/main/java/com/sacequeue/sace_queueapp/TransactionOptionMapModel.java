package com.sacequeue.sace_queueapp;

/**
 * Created by Keji's Lab on 19/02/2018.
 */

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Keji's Lab on 26/11/2017.
 */

public class TransactionOptionMapModel {
    public String userId;
    public String studentNumber;

    public TransactionOptionMapModel(){

    }
    public TransactionOptionMapModel(String userId, String studentNumber){
        this.studentNumber = studentNumber;
        this.userId = userId;
    }
    @Exclude
    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("studentNumber", studentNumber);

        return result;
    }
}
