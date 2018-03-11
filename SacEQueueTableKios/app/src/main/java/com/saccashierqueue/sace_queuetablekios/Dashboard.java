package com.saccashierqueue.sace_queuetablekios;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Dashboard extends AppCompatActivity {
    TextView TransactionOptionManager,setKiosPin;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        context = Dashboard.this;
        setKiosPin = (TextView) findViewById(R.id.setKiosPin);
        TransactionOptionManager = (TextView) findViewById(R.id.TransactionOptionManager);
        TransactionOptionManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,TransactionListManagement.class);
                startActivity(i);
            }
        });
        setKiosPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_set_kios_pin);
                final Button btnDone = (Button) dialog.findViewById(R.id.btnDone);
                btnDone.setVisibility(View.INVISIBLE);
                final TextInputEditText inputSetPin = (TextInputEditText) dialog.findViewById(R.id.inputSetPin);
                final TextInputEditText inputPin = (TextInputEditText) dialog.findViewById(R.id.inputPin);
                final TextInputEditText inputConfirmPin = (TextInputEditText) dialog.findViewById(R.id.inputConfirmPin);
                inputPin.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (confirmPin(inputSetPin.getText().toString(),inputConfirmPin.getText().toString())){
                            if (!inputPin.getText().toString().equals("")){
                                btnDone.setVisibility(View.VISIBLE);
                            }else {
                                btnDone.setVisibility(View.INVISIBLE);
                            }
                        }else {
                            btnDone.setVisibility(View.INVISIBLE);
                        }
                    }
                });
                inputSetPin.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (confirmPin(inputSetPin.getText().toString(),inputConfirmPin.getText().toString())){
                            if (!inputPin.getText().toString().equals("")){
                                btnDone.setVisibility(View.VISIBLE);
                            }else {
                                btnDone.setVisibility(View.INVISIBLE);
                            }
                        }else {
                            btnDone.setVisibility(View.INVISIBLE);
                        }
                    }
                });
                inputConfirmPin.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (confirmPin(inputSetPin.getText().toString(),inputConfirmPin.getText().toString())){
                            if (!inputPin.getText().toString().equals("")){
                                btnDone.setVisibility(View.VISIBLE);
                            }else {
                                btnDone.setVisibility(View.INVISIBLE);
                            }
                        }else {
                            btnDone.setVisibility(View.INVISIBLE);
                        }
                    }
                });


                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase.getInstance().getReference().child(Utils.kiosPinCode).child("pin").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue(String.class).toString().equals(inputPin.getText().toString())){
                                    FirebaseDatabase.getInstance().getReference().child(Utils.kiosPinCode).child("pin").setValue(inputSetPin.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            dialog.dismiss();
                                        }
                                    });
                                }else {
                                    Toast.makeText(context,"failed",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                });
                dialog.show();



            }
        });
    }

    private boolean confirmPin(String inputPin,String confirmPin){
        boolean value = false;
        if (inputPin.equals(confirmPin)){
            value = true;
        }
        return value;
    }

}
