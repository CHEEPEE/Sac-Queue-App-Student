package com.saccashierqueue.sace_queuetablekios;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Keji's Lab on 19/01/2018.
 */

public class TransactionOptionsRecyclerViewAdapter extends RecyclerView.Adapter<TransactionOptionsRecyclerViewAdapter.MyViewHolder> {
    private ArrayList<TransactionOptionDataModel> transactionOptionDataModelArrayList;
    private Context context;
    private ArrayList<Boolean> getNull;
    private int Comment_Number;
    private DatabaseReference mdatabase;
    private FirebaseAuth mAuth;
    public class MyViewHolder extends RecyclerView.ViewHolder{

       public TextView lblTransactionOptionItem;


        public MyViewHolder(View view){
            super(view);
                lblTransactionOptionItem = (TextView) view.findViewById(R.id.lblTransactionOptionItem);
        }
    }

    public TransactionOptionsRecyclerViewAdapter(Context c, ArrayList<TransactionOptionDataModel> transactionmodel){
        this.transactionOptionDataModelArrayList = transactionmodel;
        this.context =c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkbox_transaction_option_list_row,parent,false);


        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        TransactionOptionDataModel transactionOptionDataModel = transactionOptionDataModelArrayList.get(position);
        holder.lblTransactionOptionItem.setText(transactionOptionDataModel.getTrasactionName());

    }

    @Override
    public int getItemCount() {
        return transactionOptionDataModelArrayList.size();
    }
}


