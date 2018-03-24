package com.saccashierqueue.sace_queuetablekios.transactionSelectionActivty;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saccashierqueue.sace_queuetablekios.R;
import com.saccashierqueue.sace_queuetablekios.transactionOptionManagements.TransactionOptionDataModel;
import com.saccashierqueue.sace_queuetablekios.transactionOptionManagements.TransactionOptionMapModel;
import com.saccashierqueue.sace_queuetablekios.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransactionSelection extends AppCompatActivity {
    RecyclerView transactionList;
    RecyclerView listUnfixValues;
    ArrayList<TransactionOptionDataModel> selectedTransaction = new ArrayList<>();
    ArrayList<TransactionOptionDataModel> transactionOptionDataModelArrayList = new ArrayList<>();
    ArrayList<TransactionOptionDataModel> transactionOptionDataModelArrayListUnfix = new ArrayList<>();
    DatabaseReference mDatabase;
    TransactionActivityRecyclerViewAdapter transactionActivityRecyclerViewAdapter;
    UnfixTransactionActivityRecyclerViewAdapter transactionActivityRecyclerViewAdapterUnfix;
    SelectedTransactionActivityRecyclerViewAdapter selectedTransactionActivityRecyclerViewAdapter;
    Context context;
    TextView totalTransactionCost,lblNext,lblOther;
    RecyclerView selectedTransactionRV;
    String studentNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_selection);
        transactionList = (RecyclerView) findViewById(R.id.transactionListRV);
        selectedTransactionRV = (RecyclerView) findViewById(R.id.selectedTransactionRV);
        listUnfixValues = (RecyclerView) findViewById(R.id.listUnfixValues);
        totalTransactionCost = (TextView) findViewById(R.id.totalTransactionCost);
        lblOther = (TextView) findViewById(R.id.lblOther);
        studentNumber = getIntent().getStringExtra("studentNumber");
        lblNext = (TextView) findViewById(R.id.lblNext);
        context = TransactionSelection.this;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        transactionActivityRecyclerViewAdapter = new TransactionActivityRecyclerViewAdapter(context,transactionOptionDataModelArrayList);
        transactionActivityRecyclerViewAdapterUnfix = new UnfixTransactionActivityRecyclerViewAdapter(context, transactionOptionDataModelArrayListUnfix);
        selectedTransactionActivityRecyclerViewAdapter = new SelectedTransactionActivityRecyclerViewAdapter(context,selectedTransaction);
        //selected Trasactions
        selectedTransactionRV.setLayoutManager(new LinearLayoutManager(context));
        selectedTransactionRV.setAdapter(selectedTransactionActivityRecyclerViewAdapter);
        //listUnfixValues

        listUnfixValues.setAdapter(transactionActivityRecyclerViewAdapterUnfix);
        listUnfixValues.setLayoutManager(new LinearLayoutManager(context));

        //transactions
        transactionList.setLayoutManager(new GridLayoutManager(context,2));
        transactionList.setAdapter(transactionActivityRecyclerViewAdapter);
        mDatabase.child(Utils.transactionOptions).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                transactionOptionDataModelArrayList.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    TransactionOptionDataModel transactionOptionDataModel = new TransactionOptionDataModel();
                    TransactionOptionMapModel transactionOptionMapModel = dataSnapshot1.getValue(TransactionOptionMapModel.class);
                    transactionOptionDataModel.setTransactionName(transactionOptionMapModel.transactionName);
                    transactionOptionDataModel.setTransactionCost(transactionOptionMapModel.transactionCost);
                    transactionOptionDataModel.setKey(transactionOptionMapModel.key);
                    transactionOptionDataModelArrayList.add(transactionOptionDataModel);
                }
                transactionActivityRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lblOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setCancelable(true);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setContentView(R.layout.add_unfix_transaction);

                TextView transactionLabel = (TextView) dialog.findViewById(R.id.transactionLabel);
                final EditText transactionCost = (EditText) dialog.findViewById(R.id.transactionCost);

                Button button =(Button) dialog.findViewById(R.id.btnDone);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!transactionCost.getText().toString().trim().equals("")){
                            TransactionOptionDataModel transactionOptionDataModel = new TransactionOptionDataModel();
                            transactionOptionDataModel.setTransactionCost(Integer.parseInt(transactionCost.getText().toString()));

                            selectedTransaction.add(transactionOptionDataModel);
                            dialog.dismiss();
                        }else {

                        }
                        selectedTransactionActivityRecyclerViewAdapter.notifyDataSetChanged();
                    }
                });
                dialog.show();
            }
        });


        mDatabase.child(Utils.transactionOptionsUnfix).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                transactionOptionDataModelArrayListUnfix.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    TransactionOptionDataModel transactionOptionDataModel = new TransactionOptionDataModel();
                    TransactionOptionMapModel transactionOptionMapModel = dataSnapshot1.getValue(TransactionOptionMapModel.class);
                    transactionOptionDataModel.setTransactionName(transactionOptionMapModel.transactionName);
                    transactionOptionDataModel.setTransactionCost(transactionOptionMapModel.transactionCost);
                    transactionOptionDataModel.setKey(transactionOptionMapModel.key);
                    transactionOptionDataModelArrayListUnfix.add(transactionOptionDataModel);
                }
                transactionActivityRecyclerViewAdapterUnfix.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        transactionActivityRecyclerViewAdapter.setOnItemClickListener(new TransactionActivityRecyclerViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                TransactionOptionDataModel transactionOptionDataModel = transactionOptionDataModelArrayList.get(position);
                selectedTransaction.add(transactionOptionDataModel);
                selectedTransactionActivityRecyclerViewAdapter.notifyDataSetChanged();
                System.out.println(transactionOptionDataModel.getTrasactionName()+position);
                totalTransactionCost.setText(getToTalCost(selectedTransaction)+"");
            }
        });
        selectedTransactionActivityRecyclerViewAdapter.setOnItemClickListener(new TransactionActivityRecyclerViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                selectedTransaction.remove(position);
                selectedTransactionActivityRecyclerViewAdapter.notifyDataSetChanged();
                totalTransactionCost.setText(getToTalCost(selectedTransaction)+"");
            }
        });
        transactionActivityRecyclerViewAdapterUnfix.setOnItemClickListener(new UnfixTransactionActivityRecyclerViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                getUnfixTransaction(context,transactionOptionDataModelArrayListUnfix.get(position).getTrasactionName());
                totalTransactionCost.setText(getToTalCost(selectedTransaction)+"");
            }
        });


        //lblNext onclicl

        lblNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInputNumber(context);
            }
        });
    }

    private int getToTalCost(ArrayList<TransactionOptionDataModel> arrayList){

        int total = 0;
        for (int i = 0;i<arrayList.size();i++){
            total+=arrayList.get(i).getTransactionCost();
        }
        return total;
    }

    private void getUnfixTransaction(Context context, final String transactionName){

        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(true);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.add_unfix_transaction);

        TextView transactionLabel = (TextView) dialog.findViewById(R.id.transactionLabel);
        final EditText transactionCost = (EditText) dialog.findViewById(R.id.transactionCost);
        transactionLabel.setText(transactionName);
        Button button =(Button) dialog.findViewById(R.id.btnDone);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!transactionCost.getText().toString().trim().equals("")){
                    TransactionOptionDataModel transactionOptionDataModel = new TransactionOptionDataModel();
                    transactionOptionDataModel.setTransactionCost(Integer.parseInt(transactionCost.getText().toString()));
                    transactionOptionDataModel.setTransactionName(transactionName);
                 selectedTransaction.add(transactionOptionDataModel);
                 dialog.dismiss();
                }else {

                }
                selectedTransactionActivityRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
        dialog.show();
    }

    private void getInputNumber(final Context context){
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(true);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.add_unfix_transaction);

        TextView transactionLabel = (TextView) dialog.findViewById(R.id.transactionLabel);
        final EditText inputNumber = (EditText) dialog.findViewById(R.id.transactionCost);
        transactionLabel.setText("Input Mobile Number");

        Button button =(Button) dialog.findViewById(R.id.btnDone);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedTransaction.size()>0){
                    if (!inputNumber.getText().toString().trim().equals("")){
                       // mDatabase.child(Utils.queueStudentNumber).child()
                       /* Utils.errorMessageDialog(context,Utils.getCurrentDate());
                        dialog.dismiss();*/

                       mDatabase.child(Utils.getLatestNumber).child(Utils.getCurrentDate()).addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(DataSnapshot dataSnapshot) {
                               if (dataSnapshot.getValue()==null){
                                   //if null set latest Queue number to 0
                                  mDatabase.child(Utils.getLatestNumber).child(Utils.getCurrentDate()).setValue(0).addOnSuccessListener(new OnSuccessListener<Void>() {
                                      @Override
                                      public void onSuccess(Void aVoid) {
                                          //then set StudentQueue Nmber to 1
                                          mDatabase.child(Utils.queueStudentNumber).child(Utils.getCurrentDate()).child(studentNumber).setValue(1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                              @Override
                                              public void onSuccess(Void aVoid) {
                                                  //then set number to 1
                                                  mDatabase.child(Utils.getLatestNumber).child(Utils.getCurrentDate()).setValue(1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                      @Override
                                                      public void onSuccess(Void aVoid) {
                                                          Utils.errorMessageDialog(context,"Your Queue Number is 1");
                                                          saveTransaction(studentNumber,(1)+"",inputNumber.getText().toString());
                                                          dialog.dismiss();
                                                      }
                                                  });
                                              }
                                          });
                                      }
                                  });
                               }

                               //if queNumber is not Null
                               else {

                                  final int QueNumber =  Integer.parseInt( dataSnapshot.getValue().toString());
                                  mDatabase.child(Utils.queueStudentNumber).child(Utils.getCurrentDate()).child(studentNumber).setValue(QueNumber+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                      @Override
                                      public void onSuccess(Void aVoid) {
                                          mDatabase.child(Utils.getLatestNumber).child(Utils.getCurrentDate()).setValue(QueNumber+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                              @Override
                                              public void onSuccess(Void aVoid) {

                                                  Utils.errorMessageDialog(context,"Your Queue Number is "+(QueNumber+1));
                                                  saveTransaction(studentNumber,(QueNumber+1)+"",inputNumber.getText().toString());
                                              }
                                          });
                                      }
                                  });

                               }
                           }

                           @Override
                           public void onCancelled(DatabaseError databaseError) {

                           }
                       });
                    }else {

                    }
                }else {
                    Utils.errorMessageDialog(context,"Please Select Transaction First");
                }
            }
        });
        dialog.show();
    }

    private void saveTransaction(String studentNumber,String queueNumber,String mobilebnumber) {

        // mDatabase.child(Utils.studentTransactions).child(Utils.getCurrentDate()).child(studentNumber).child(Utils.transactionDetails).updateChildren()

        StudentTransactionDetailsDataMapModel transactionDetailsDataMapModel = new StudentTransactionDetailsDataMapModel(studentNumber,queueNumber,mobilebnumber);
        Map<String,Object> detailsValue = transactionDetailsDataMapModel.toMap();
        Map<String,Object> detailsChildUpdate = new HashMap<>();
        detailsChildUpdate.put(studentNumber,detailsValue);
        mDatabase.child(Utils.studentTransactions).child(Utils.getCurrentDate()).child(studentNumber).child(Utils.transactionDetails).updateChildren(detailsChildUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
        Map<String,Object> detailsChildUpdateTrans = new HashMap<>();
        detailsChildUpdateTrans.put(queueNumber,detailsValue);
        mDatabase.child("transactions").child(Utils.getCurrentDate()).updateChildren(detailsChildUpdateTrans).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
        mDatabase.child("transactions").child(Utils.getCurrentDate()).child(queueNumber).child("totalCost").setValue(getToTalCost(selectedTransaction));

        for ( int i = 0; i < selectedTransaction.size(); i++) {
            final int index = i;
            String key = mDatabase.push().getKey().toString();
            TransactionOptionMapModel transactionOptionMapModel = new TransactionOptionMapModel(key,selectedTransaction.get(i).getTrasactionName(),selectedTransaction.get(i).getTransactionCost());
            Map<String,Object> transactionMaptValue = transactionOptionMapModel.toMap();
            Map<String,Object> childUpdate = new HashMap<>();
            childUpdate.put(key,transactionMaptValue);
            mDatabase.child(Utils.studentTransactions).child(Utils.getCurrentDate()).child(studentNumber).child(Utils.transactionSelectedList).updateChildren(childUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                   if (index == selectedTransaction.size()){
                       Utils.errorMessageDialog(context,"Success");

                   }



                }
            });
        }
    }
}
