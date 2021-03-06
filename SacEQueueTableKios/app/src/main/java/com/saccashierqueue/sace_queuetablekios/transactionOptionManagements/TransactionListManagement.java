package com.saccashierqueue.sace_queuetablekios.transactionOptionManagements;

import android.app.Dialog;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.saccashierqueue.sace_queuetablekios.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransactionListManagement extends AppCompatActivity {
    TextView btnAddTransactionOption;
    DatabaseReference mDatabase;
    RecyclerView transactionListRecyclerView,transactionOptionListUnfix;
    TransactionOptionsRecyclerViewAdapter transactionOptionsRecyclerViewAdapter;
    TransactionOptionsRecyclerViewAdapterUnfix transactionOptionsRecyclerViewAdapterUnfix;
    ArrayList<TransactionOptionDataModel> transactionOptionDataModels = new ArrayList<>();
    ArrayList<TransactionOptionDataModel> transactionOptionDataModelsUnfix = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        transactionListRecyclerView = (RecyclerView) findViewById(R.id.transactionoptionsRL);
        transactionOptionListUnfix = (RecyclerView) findViewById(R.id.transactionOptionListUnfix);
        btnAddTransactionOption = (TextView) findViewById(R.id.btnAddTransactionOption);
        btnAddTransactionOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(TransactionListManagement.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_add_transaction_options);
                final TextInputEditText inputTransactionNameUnFix = (TextInputEditText) dialog.findViewById(R.id.inputTransactionNameUnFix);
                final TextInputEditText inputTransactionName = (TextInputEditText) dialog.findViewById(R.id.inputTransactionName);
                final TextInputEditText inputTransactionCost = (TextInputEditText) dialog.findViewById(R.id.inputTransactionCost);
                Button btnDoneUnfix = (Button) dialog.findViewById(R.id.btnDoneUnfix);
                Button btnDone = (Button) dialog.findViewById(R.id.btnDone);
                btnDone.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           if (validateForm(inputTransactionName,inputTransactionCost)){
                               String key = mDatabase.push().getKey().toString();
                               TransactionOptionMapModel transactionOptionMapModel = new TransactionOptionMapModel(key,inputTransactionName.getText().toString(),Integer.parseInt(inputTransactionCost.getText().toString()));
                               Map<String,Object> transactionMaptValue = transactionOptionMapModel.toMap();
                               Map<String,Object> childUpdate = new HashMap<>();
                               childUpdate.put(key,transactionMaptValue);
                               mDatabase.child(Utils.transactionOptions).updateChildren(childUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void aVoid) {
                                       dialog.dismiss();
                                   }
                               });
                           }
                       }
                   });

                btnDoneUnfix.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!inputTransactionNameUnFix.getText().toString().trim().equals("")){
//                            String key = mDatabase.push().getKey().toString();
//                            mDatabase.child(Utils.transactionOptionsUnfix).child(key).child(Utils.transactionOptions).setValue(inputTransactionNameUnFix.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    dialog.dismiss();
//                                }
//                            });
                            String key = mDatabase.push().getKey().toString();
                            TransactionOptionMapModel transactionOptionMapModel = new TransactionOptionMapModel(key,inputTransactionNameUnFix.getText().toString(),0);
                            Map<String,Object> transactionMaptValue = transactionOptionMapModel.toMap();
                            Map<String,Object> childUpdate = new HashMap<>();
                            childUpdate.put(key,transactionMaptValue);
                            mDatabase.child(Utils.transactionOptionsUnfix).updateChildren(childUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    dialog.dismiss();
                                }
                            });

                        }else {
                            Utils.errorMessageDialog(TransactionListManagement.this,"Please Input Transaction Name");
                        }
                    }
                });
                dialog.show();



            }
        });
        transactionOptionsRecyclerViewAdapter = new TransactionOptionsRecyclerViewAdapter(TransactionListManagement.this,transactionOptionDataModels);
        transactionListRecyclerView.setLayoutManager(new LinearLayoutManager(TransactionListManagement.this));
        transactionListRecyclerView.setAdapter(transactionOptionsRecyclerViewAdapter);

        mDatabase.child(Utils.transactionOptions).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                transactionOptionDataModels.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    TransactionOptionDataModel transactionOptionDataModel = new TransactionOptionDataModel();
                    TransactionOptionMapModel transactionOptionMapModel = dataSnapshot1.getValue(TransactionOptionMapModel.class);
                    transactionOptionDataModel.setKey(transactionOptionMapModel.key);
                    transactionOptionDataModel.setTransactionCost(transactionOptionMapModel.transactionCost);
                    transactionOptionDataModel.setTransactionName(transactionOptionMapModel.transactionName);
                    transactionOptionDataModels.add(transactionOptionDataModel);
                }
                transactionOptionsRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        transactionOptionsRecyclerViewAdapterUnfix = new TransactionOptionsRecyclerViewAdapterUnfix(TransactionListManagement.this,transactionOptionDataModelsUnfix);
        transactionOptionListUnfix.setLayoutManager(new LinearLayoutManager(TransactionListManagement.this));
        transactionOptionListUnfix.setAdapter(transactionOptionsRecyclerViewAdapterUnfix);

        mDatabase.child(Utils.transactionOptionsUnfix).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                transactionOptionDataModelsUnfix.clear();

                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    TransactionOptionDataModel transactionOptionDataModel = new TransactionOptionDataModel();
                    TransactionOptionMapModel transactionOptionMapModel = dataSnapshot1.getValue(TransactionOptionMapModel.class);
                    transactionOptionDataModel.setKey(transactionOptionMapModel.key);
                    transactionOptionDataModel.setTransactionName(transactionOptionMapModel.transactionName);
                    transactionOptionDataModelsUnfix.add(transactionOptionDataModel);
                }
                transactionOptionsRecyclerViewAdapterUnfix.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void saveTransactionOption(String transactionName,int transactionCost){

    }
    private boolean validateForm(EditText transactionName,EditText transactionCost) {
        boolean valid = true;

        String name = transactionName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            transactionName.setError("Required.");
            valid = false;
        } else {
            transactionName.setError(null);
        }

        String address = transactionCost.getText().toString();
        if (TextUtils.isEmpty(address)) {
            transactionCost.setError("Required.");
            valid = false;
        } else {
            transactionCost.setError(null);
        }

        return valid;
    }
}
