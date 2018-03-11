package com.saccashierqueue.sace_queuetablekios.transactionSelectionActivty;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saccashierqueue.sace_queuetablekios.R;
import com.saccashierqueue.sace_queuetablekios.TransactionOptionDataModel;
import com.saccashierqueue.sace_queuetablekios.TransactionOptionMapModel;
import com.saccashierqueue.sace_queuetablekios.Utils;

import java.util.ArrayList;

public class TransactionSelection extends AppCompatActivity {
    RecyclerView transactionList;
    ArrayList<TransactionOptionDataModel> transactionOptionDataModelArrayList = new ArrayList<>();
    DatabaseReference mDatabase;
    TransactionActivityRecyclerViewAdapter transactionActivityRecyclerViewAdapter;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_selection);
        transactionList = (RecyclerView) findViewById(R.id.transactionListRV);
        context = TransactionSelection.this;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        transactionActivityRecyclerViewAdapter = new TransactionActivityRecyclerViewAdapter(context,transactionOptionDataModelArrayList);
        transactionList.setLayoutManager(new GridLayoutManager(context,4));
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
    }
}
