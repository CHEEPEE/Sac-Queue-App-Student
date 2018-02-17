package com.sacequeue.sace_queueapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    TextView cashiernumber1,cashiernumber2,cashiernumber3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        cashiernumber1 = (TextView) findViewById(R.id.labelWindow1);
        cashiernumber2 = (TextView) findViewById(R.id.labelWindow2);
        cashiernumber3 = (TextView) findViewById(R.id.labelWindow3);

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
}
