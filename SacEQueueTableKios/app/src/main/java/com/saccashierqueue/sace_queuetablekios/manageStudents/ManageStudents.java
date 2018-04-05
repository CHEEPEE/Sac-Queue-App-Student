package com.saccashierqueue.sace_queuetablekios.manageStudents;

import android.app.Dialog;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saccashierqueue.sace_queuetablekios.R;
import com.saccashierqueue.sace_queuetablekios.Utils;
import com.saccashierqueue.sace_queuetablekios.transactionOptionManagements.TransactionListManagement;
import com.saccashierqueue.sace_queuetablekios.transactionOptionManagements.TransactionOptionMapModel;
import com.saccashierqueue.sace_queuetablekios.transactionSelectionActivty.TransactionActivityRecyclerViewAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManageStudents extends AppCompatActivity {
    TextView lblAddStudent;
    ArrayList<StudentDataModel> studentDataModelsArrayList = new ArrayList<>();
    RecyclerView studentListRv;
    StudentListViewAdapter studentListViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_students);

        studentListRv = (RecyclerView) findViewById(R.id.studentListRV);
        lblAddStudent = (TextView) findViewById(R.id.lblAddStudent);
        studentListViewAdapter = new StudentListViewAdapter(ManageStudents.this,studentDataModelsArrayList);
        studentListRv.setLayoutManager(new LinearLayoutManager(ManageStudents.this));
        studentListRv.setAdapter(studentListViewAdapter);
        studentListViewAdapter.notifyDataSetChanged();
        FirebaseDatabase.getInstance().getReference().child("students").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                studentDataModelsArrayList.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                   StudentDataModel studentDataModel = new StudentDataModel();
                   StudentsMapModel studentsMapModel = dataSnapshot1.getValue(StudentsMapModel.class);
                   studentDataModel.setStudentName(studentsMapModel.studentName);
                   studentDataModel.setStudentNumber(studentsMapModel.studentNumber);
                   studentDataModelsArrayList.add(studentDataModel);
                   studentListViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        studentListViewAdapter.setOnItemClickListener(new TransactionActivityRecyclerViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, final int position) {
                final Dialog dialog = new Dialog(ManageStudents.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.edit_students);
                dialog.show();
                final TextInputEditText studentNumber = (TextInputEditText) dialog.findViewById(R.id.inputStudentNumber);
                final TextInputEditText studentName = (TextInputEditText) dialog.findViewById(R.id.inputStudentName);
                TextView btnDelete = (TextView) dialog.findViewById(R.id.btnDelete);
                studentName.setText(studentDataModelsArrayList.get(position).studentName);
                studentNumber.setText(studentDataModelsArrayList.get(position).studentNumber);
                TextView btnDone = (TextView) dialog.findViewById(R.id.btnDone);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase.getInstance().getReference().child("students").child(studentNumber.getText().toString()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.validateForm(studentName,studentNumber)){
                            StudentsMapModel studentsMapModel = new StudentsMapModel(studentNumber.getText().toString(),studentName.getText().toString());
                            Map<String,Object> stringObjectMapValue = studentsMapModel.toMap();
                            Map<String,Object> childUpdate = new HashMap<>();
                            childUpdate.put(studentNumber.getText().toString(),stringObjectMapValue);
                            FirebaseDatabase.getInstance().getReference().child("students").updateChildren(childUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    dialog.dismiss();
                                }
                            });
                        }
                    }
                });
            }
        });

        lblAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ManageStudents.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.add_student_dialog);
                dialog.show();
                final TextInputEditText studentNumber = (TextInputEditText) dialog.findViewById(R.id.inputStudentNumber);
                final TextInputEditText studentName = (TextInputEditText) dialog.findViewById(R.id.inputStudentName);
                TextView btnDone = (TextView) dialog.findViewById(R.id.btnDone);
                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.validateForm(studentName,studentNumber)){
                            StudentsMapModel studentsMapModel = new StudentsMapModel(studentNumber.getText().toString(),studentName.getText().toString());
                            Map<String,Object> stringObjectMapValue = studentsMapModel.toMap();
                            Map<String,Object> childUpdate = new HashMap<>();
                            childUpdate.put(studentNumber.getText().toString(),stringObjectMapValue);
                            FirebaseDatabase.getInstance().getReference().child("students").updateChildren(childUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    dialog.dismiss();
                                }
                            });
                        }
                    }
                });
            }
        });


    }
}
