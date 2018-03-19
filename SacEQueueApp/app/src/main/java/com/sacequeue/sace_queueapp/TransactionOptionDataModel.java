package com.sacequeue.sace_queueapp;

/**
 * Created by Keji's Lab on 27/02/2018.
 */

public class TransactionOptionDataModel {
     String key;
     String transactionName;
     int transactionCost;

    public  String getKey(){
        return key;
    }
    public String getTrasactionName(){
        return transactionName;
    }
    public int getTransactionCost(){
        return transactionCost;
    }

    public void setKey(String k){
        this.key = k;
    }
    public void setTransactionName(String name){
        this.transactionName = name;
    }

    public void setTransactionCost(int cost){
        this.transactionCost = cost;
    }
}
