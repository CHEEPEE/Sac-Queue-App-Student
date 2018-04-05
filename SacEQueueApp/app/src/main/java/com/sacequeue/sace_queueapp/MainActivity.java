package com.sacequeue.sace_queueapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sacequeue.sace_queueapp.transactions.ActivityTransactionSelection;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    TextView cashiernumber1,cashiernumber2,cashiernumber3,userQueueNumber;
    Context context;
    String queNumber;
    String studentNumber;
    TransactionOptionsRecyclerViewAdapter transactionOptionsRecyclerViewAdapter;
    ArrayList<TransactionOptionDataModel> transactionOptionDataModelsArray = new ArrayList<>();
    ImageView lblLogOut;
    ImageView getNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        context = MainActivity.this;
        cashiernumber1 = (TextView) findViewById(R.id.labelWindow1);
        cashiernumber2 = (TextView) findViewById(R.id.labelWindow2);
        cashiernumber3 = (TextView) findViewById(R.id.labelWindow3);
        userQueueNumber = (TextView) findViewById(R.id.userQueueNumber);
        lblLogOut = (ImageView) findViewById(R.id.lblLogOut);
        lblLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        getNumber = (ImageView) findViewById(R.id.lblgetNumber);
        getNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(MainActivity.this, ActivityTransactionSelection.class);
                i.putExtra("studentNumber",studentNumber);
                startActivity(i);

            }
        });
        transactionOptionsRecyclerViewAdapter = new TransactionOptionsRecyclerViewAdapter(context,transactionOptionDataModelsArray);


        databaseReference.child("studentUsers").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("studentNumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                studentNumber = dataSnapshot.getValue().toString();

                databaseReference.child(Utils.queueStudentNumber).child(Utils.getCurrentDate()).child(studentNumber).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                       try {
                           userQueueNumber.setText(dataSnapshot.getValue().toString());
                           Utils.errorMessageDialog(context,dataSnapshot.getValue().toString(),"Your Queue Number is");
                           queNumber = dataSnapshot.getValue().toString();
                       }catch (NullPointerException e){
                           Utils.errorMessageDialog(context,"","No Queue Number");
                       }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                FirebaseDatabase.getInstance().getReference()
                        .child(Utils.studentTransactions).child(Utils.getCurrentDate())
                        .child(studentNumber).child(Utils.transactionSelectedList).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            TransactionOptionDataModel transactionOptionDataModel = new TransactionOptionDataModel();
                            TransactionOptionMapModel transactionOptionMapModel = dataSnapshot1.getValue(TransactionOptionMapModel.class);
                            transactionOptionDataModel.setTransactionName(transactionOptionMapModel.transactionName);
                            transactionOptionDataModel.setTransactionCost(transactionOptionDataModel.transactionCost);
                            transactionOptionDataModelsArray.add(transactionOptionDataModel);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        userQueueNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetailsDialog(queNumber,"");
            }
        });



        databaseReference.child("cashiernumber1").child("cashierNnumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cashiernumber1.setText(dataSnapshot.getValue(Integer.class)+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference.child("cashiernumber2").child("cashierNnumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cashiernumber2.setText(dataSnapshot.getValue(Integer.class)+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }); databaseReference.child("cashiernumber3").child("cashierNnumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cashiernumber3.setText(dataSnapshot.getValue(Integer.class)+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    private void showDetailsDialog(String msg,String totalCost){
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(true);
        ArrayList<TransactionOptionDataModel> transactionOptionDataModelsArray = new ArrayList<>();


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.details_dialog);
        TextView lblDone = (TextView) dialog.findViewById(R.id.lblDone);
        TextView messageLabel = (TextView)dialog.findViewById(R.id.messageLabel);
        TextView lblTransactionCost = (TextView) dialog.findViewById(R.id.transactionCost);
        lblTransactionCost.setText("P "+totalCost);
        TextView message = (TextView) dialog.findViewById(R.id.messgae);
        message.setText(msg);
        messageLabel.setText("Your Queue number is");

        dialog.show();
        lblDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}
