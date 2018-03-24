package com.saccashierqueue.sace_queuetablekios.transactionOptionManagements;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.saccashierqueue.sace_queuetablekios.R;
import com.saccashierqueue.sace_queuetablekios.Utils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

       public TextView lblTransactionOptionItem,transactionCost;


        public MyViewHolder(View view){
            super(view);
                lblTransactionOptionItem = (TextView) view.findViewById(R.id.transactionListOptions);
               transactionCost = (TextView) view.findViewById(R.id.transactionCost);
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
        final TransactionOptionDataModel transactionOptionDataModel = transactionOptionDataModelArrayList.get(position);
        holder.lblTransactionOptionItem.setText(transactionOptionDataModel.getTrasactionName());
        holder.lblTransactionOptionItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.show();
                dialog.setContentView(R.layout.edit_transaction);
                dialog.show();
                final TextInputEditText transactionName = (TextInputEditText) dialog.findViewById(R.id.inputTransactionName);
                final TextInputEditText transactionPrice = (TextInputEditText) dialog.findViewById(R.id.inputTransactionPrice);
                transactionName.setText(transactionOptionDataModel.transactionName);
                transactionPrice.setText(transactionOptionDataModel.transactionCost+"");
                TextView btnDone = (TextView) dialog.findViewById(R.id.btnDone);
                TextView btnDelete = (TextView) dialog.findViewById(R.id.btnDelete);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase.getInstance().getReference().child(Utils.transactionOptions).child(transactionOptionDataModel.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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
                       if (Utils.validateForm(transactionName,transactionPrice)){
                           String key =transactionOptionDataModel.getKey();
                           TransactionOptionMapModel transactionOptionMapModel = new TransactionOptionMapModel(key,transactionName.getText().toString(),Integer.parseInt(transactionPrice.getText().toString()));
                           Map<String,Object> transactionMaptValue = transactionOptionMapModel.toMap();
                           Map<String,Object> childUpdate = new HashMap<>();
                           childUpdate.put(key,transactionMaptValue);
                           FirebaseDatabase.getInstance().getReference().child(Utils.transactionOptions).updateChildren(childUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void aVoid) {
                                   dialog.dismiss();
                               }
                           });
                       }
                    }
                });
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return transactionOptionDataModelArrayList.size();
    }
}


