package com.saccashierqueue.sace_queuetablekios.transactionOptionManagements;

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
    public String key;
    public String transactionName;
    public int transactionCost;

    public TransactionOptionMapModel(){

    }
    public TransactionOptionMapModel(String key, String name,int cost){
        this.key = key;
        this.transactionName = name;
        this.transactionCost =cost;
    }
    @Exclude
    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("key",key);
        result.put("transactionName",transactionName);
        result.put("transactionCost",transactionCost);
        return result;
    }
}
