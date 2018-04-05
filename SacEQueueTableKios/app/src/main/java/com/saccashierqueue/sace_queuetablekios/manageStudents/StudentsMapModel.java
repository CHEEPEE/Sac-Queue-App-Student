package com.saccashierqueue.sace_queuetablekios.manageStudents;

/**
 * Created by Keji's Lab on 19/02/2018.
 */

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Keji's Lab on 26/11/2017.
 */

public class StudentsMapModel {
    public String studentNumber;
    public String studentName;


    public StudentsMapModel(){

    }
    public StudentsMapModel(String studentNumber, String studentNmae){

        this.studentNumber = studentNumber;
        this.studentName =studentNmae;
    }
    @Exclude
    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("studentNumber",studentNumber);
        result.put("studentName",studentName);
        return result;
    }
}
