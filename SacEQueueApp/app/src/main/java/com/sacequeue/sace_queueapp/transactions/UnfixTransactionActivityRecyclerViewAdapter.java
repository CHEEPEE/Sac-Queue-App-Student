package com.sacequeue.sace_queueapp.transactions;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.sacequeue.sace_queueapp.R;


import java.util.ArrayList;

/**
 * Created by Keji's Lab on 19/01/2018.
 */

public class UnfixTransactionActivityRecyclerViewAdapter extends RecyclerView.Adapter<UnfixTransactionActivityRecyclerViewAdapter.MyViewHolder> {
    private ArrayList<TransactionOptionDataModel> transactionOptionDataModelArrayList;
    private Context context;
    private ArrayList<Boolean> getNull;
    private int Comment_Number;
    private DatabaseReference mdatabase;
    private FirebaseAuth mAuth;
    public class MyViewHolder extends RecyclerView.ViewHolder{

       public TextView lblTransactionOptionItem,lblTransactionCost;


        public MyViewHolder(View view){
            super(view);
            lblTransactionCost = (TextView) view.findViewById(R.id.transactionCost);
                lblTransactionOptionItem = (TextView) view.findViewById(R.id.transactionListOptions);
        }
    }

    public UnfixTransactionActivityRecyclerViewAdapter(Context c, ArrayList<TransactionOptionDataModel> transactionmodel){
        this.transactionOptionDataModelArrayList = transactionmodel;
        this.context = c;
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
        holder.lblTransactionOptionItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickLitener.onItemClick(holder.itemView,position);
            }
        });
        holder.lblTransactionCost.setText("");
    }
    @Override
    public int getItemCount() {
        return transactionOptionDataModelArrayList.size();
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

    }
    public interface OnItemLongClickListener{
        void onItemLongClick(View view, int posistion);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickListener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    private OnItemLongClickListener monItemLongClickListener;

    public void setonItemLongClickListener(OnItemLongClickListener monItemLongClickListener){
        this.monItemLongClickListener = monItemLongClickListener;
    }
}


