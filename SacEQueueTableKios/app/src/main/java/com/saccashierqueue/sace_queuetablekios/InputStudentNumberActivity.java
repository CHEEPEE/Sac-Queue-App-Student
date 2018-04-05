package com.saccashierqueue.sace_queuetablekios;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saccashierqueue.sace_queuetablekios.transactionSelectionActivty.TransactionSelection;

public class InputStudentNumberActivity extends AppCompatActivity {
    TextView btnArray[] = new TextView[9];
    int btnId[] = {R.id.btnOne,R.id.btnTwo,R.id.btnThree,R.id.btnFour,R.id.btnFive,R.id.btnsix,R.id.btnSeven,R.id.btnEight,R.id.btnNine};
    int btnValue[] = {1,2,3,4,5,6,7,8,9};
    TextView btnZero,btndash,lblNext;
    TextView inputStudentNumber;
    Button btnClear;
    TextView queueNumber;
    DatabaseReference mDatabase;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputstudent_number);
        inputStudentNumber = (TextView) findViewById(R.id.inputStudentNuber);
        context = InputStudentNumberActivity.this;
        lblNext = (TextView) findViewById(R.id.lblNext);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnZero = (TextView) findViewById(R.id.btnZero);
        btndash = (TextView) findViewById(R.id.btndash);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        for (int i=0;i<btnId.length;i++){
            final int number = i;
            btnArray[i] = (TextView) findViewById(btnId[i]);
            btnArray[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setInputText((number+1)+"");
                }
            });
        }
        btndash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInputText("-");
            }
        });
        btnZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInputText("0");
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputStudentNumber.setText(inputStudentNumber.getText().toString().substring(0,inputStudentNumber.length()-1));
            }
        });
        btnClear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                inputStudentNumber.setText("");
                return true;
            }
        });

        inputStudentNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()==0){
                    btnClear.setEnabled(false);
                }else if (s.length()>8){
                    inputStudentNumber.setText(s.subSequence(0,8));
                }
                else {
                    btnClear.setEnabled(true);
                }
            }
        });

        mDatabase.child(Utils.dirQueueNumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lblNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputStudentNumber.getText().length()<6){
                    Utils.errorMessageDialog(context,"Check Inputed Student Number");
                }else if (inputStudentNumber.getText().length()>8){
                    Utils.errorMessageDialog(context,"Check Inputed Student Number");
                }else {

                   mDatabase.child("students").child(inputStudentNumber.getText().toString()).child("studentNumber").addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {
                           try{
                               if (dataSnapshot.getValue(String.class).equals(inputStudentNumber.getText().toString())) {
                                   Intent i = new Intent(context,TransactionSelection.class);
                                   i.putExtra("studentNumber",inputStudentNumber.getText().toString());
                                   startActivity(i);
                                   finish();
                               }else {
                                   Utils.errorMessageDialog(context, "Student Number Doesn't exist");
                               }

                           }catch (NullPointerException e){
                               Utils.errorMessageDialog(context, "Student Number Doesn't exist");
                           }
                       }

                       @Override
                       public void onCancelled(DatabaseError databaseError) {

                       }
                   });


                    }

                }

        });

    }

    private void setInputText(String number){
        inputStudentNumber.setText(inputStudentNumber.getText().toString()+number);
    }


}
