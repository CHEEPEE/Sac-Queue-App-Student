package com.saccashierqueue.sace_queuetablekios.manageStudents;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.saccashierqueue.sace_queuetablekios.R;
import com.saccashierqueue.sace_queuetablekios.transactionSelectionActivty.TransactionActivityRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * Created by Keji's Lab on 19/01/2018.
 */

public class StudentListViewAdapter extends RecyclerView.Adapter<StudentListViewAdapter.MyViewHolder> {
    private ArrayList<StudentDataModel> studentDataModelArr;
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

    public StudentListViewAdapter(Context c, ArrayList<StudentDataModel> transactionmodel){
        this.studentDataModelArr = transactionmodel;
        this.context =c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkbox_transaction_option_list_row,parent,false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        StudentDataModel studentDataModel = studentDataModelArr.get(position);
        holder.lblTransactionOptionItem.setText(studentDataModel.getStudentName());
        holder.transactionCost.setText(studentDataModel.getStudentNumber());
        holder.lblTransactionOptionItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickLitener.onItemClick(holder.itemView,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return studentDataModelArr.size();
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

    private TransactionActivityRecyclerViewAdapter.OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickListener(TransactionActivityRecyclerViewAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    private TransactionActivityRecyclerViewAdapter.OnItemLongClickListener monItemLongClickListener;

    public void setonItemLongClickListener(TransactionActivityRecyclerViewAdapter.OnItemLongClickListener monItemLongClickListener){
        this.monItemLongClickListener = monItemLongClickListener;
    }
}


