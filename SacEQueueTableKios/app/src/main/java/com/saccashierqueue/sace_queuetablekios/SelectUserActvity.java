package com.saccashierqueue.sace_queuetablekios;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saccashierqueue.sace_queuetablekios.transactionSelectionActivty.StudenTransactionDetailsDataModel;
import com.saccashierqueue.sace_queuetablekios.transactionSelectionActivty.StudentTransactionDetailsDataMapModel;
import com.saccashierqueue.sace_queuetablekios.transactionSelectionActivty.TransactionSelection;

public class SelectUserActvity extends AppCompatActivity {
    FirebaseAuth mAuth;
    String TAG  = "SelectUser";
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 12;
    ImageView admin_dashboard,sacQueuKios;
    Context context;
    ProgressBar progressBar;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = SelectUserActvity.this;
        setContentView(R.layout.activity_select_user_actvity);
        mAuth = FirebaseAuth.getInstance();
        sacQueuKios = (ImageView) findViewById(R.id.sacQueuKios);
        admin_dashboard = (ImageView) findViewById(R.id.admin_dashboard);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("cashiernumber").child("cashierNnumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String latestNumber = dataSnapshot.getValue(long.class).toString();
                mDatabase.child("transactions").child(Utils.getCurrentDate()).child((Integer.parseInt(latestNumber)+5)+"").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        SmsManager smsManager = SmsManager.getDefault();
                      try{
                          StudentTransactionDetailsDataMapModel studentTransactionDetailsDataMapModel = dataSnapshot.getValue(StudentTransactionDetailsDataMapModel.class);
                          smsManager.sendTextMessage(studentTransactionDetailsDataMapModel.mobileNumber, null, "You are five numbers away from your queue number "+(Integer.parseInt(studentTransactionDetailsDataMapModel.queueNumber)), null, null);
                          Toast.makeText(SelectUserActvity.this,"message Sent" +(Integer.parseInt(studentTransactionDetailsDataMapModel.queueNumber)+4),Toast.LENGTH_SHORT).show();
                      }catch (NullPointerException e){

                      }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                mDatabase.child("transactions").child(Utils.getCurrentDate()).child((Integer.parseInt(latestNumber)-1)+"").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        SmsManager smsManager = SmsManager.getDefault();
                        try{
                            StudentTransactionDetailsDataMapModel studentTransactionDetailsDataMapModel = dataSnapshot.getValue(StudentTransactionDetailsDataMapModel.class);
                            smsManager.sendTextMessage(studentTransactionDetailsDataMapModel.mobileNumber, null, "You just Missed Your Number: "+(Integer.parseInt(studentTransactionDetailsDataMapModel.queueNumber)-1), null, null);
                            Toast.makeText(SelectUserActvity.this,"Just Missded Your Number" +(Integer.parseInt(studentTransactionDetailsDataMapModel.queueNumber)-1),Toast.LENGTH_SHORT).show();

                        }catch (NullPointerException e){

                        }
                     }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
       /* admin_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });*/
        admin_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent i = new Intent(context, InputStudentNumberActivity.class);
                startActivity(i);*/
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.input_pin);
                final TextInputEditText inputPin = (TextInputEditText) dialog.findViewById(R.id.inputPin);
                progressBar = (ProgressBar)dialog.findViewById(R.id.progressBar2);

                Button btnDone = (Button) dialog.findViewById(R.id.btnDone);
                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressBar.setVisibility(View.VISIBLE);

                        if (!inputPin.getText().toString().equals("")){
                            FirebaseDatabase.getInstance().getReference().child(Utils.kiosPinCode).child("pin").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue(String.class).toString().equals(inputPin.getText().toString())){
                                        Intent i = new Intent(context, Dashboard.class);
                                        startActivity(i);
                                        dialog.dismiss();

                                    }else {
                                        Toast.makeText(context,"Pin Incorrect",Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }else {
                            Toast.makeText(context,"Error: Empty Pin",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();
            }
        });
        sacQueuKios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent i = new Intent(context, InputStudentNumberActivity.class);
                startActivity(i);*/
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.input_pin);
                final TextInputEditText inputPin = (TextInputEditText) dialog.findViewById(R.id.inputPin);
                progressBar = (ProgressBar)dialog.findViewById(R.id.progressBar2);

                Button btnDone = (Button) dialog.findViewById(R.id.btnDone);
                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressBar.setVisibility(View.VISIBLE);

                        if (!inputPin.getText().toString().equals("")){
                            FirebaseDatabase.getInstance().getReference().child(Utils.kiosPinCode).child("pin").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue(String.class).toString().equals(inputPin.getText().toString())){
                                        Intent i = new Intent(context, InputStudentNumberActivity.class);
                                        startActivity(i);
                                        dialog.dismiss();

                                    }else {
                                        Toast.makeText(context,"Pin Incorrect",Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }else {
                            Toast.makeText(context,"Error: Empty Pin",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();
            }
        });
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
                Intent i = new Intent(context,Dashboard.class);
                startActivity(i);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                          /*  Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();*/

                        }

                        // ...
                    }
                });
    }
    private void reqPermissionSMS(){
        if (ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(SelectUserActvity.this,
                    android.Manifest.permission.SEND_SMS)) {
                ActivityCompat.requestPermissions(SelectUserActvity.this,
                        new String[]{android.Manifest.permission.SEND_SMS},
                        1);
            } else {
            }
            ActivityCompat.requestPermissions(SelectUserActvity.this,
                    new String[]{Manifest.permission.SEND_SMS},
                    1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    reqPermissionSMS();
                } else {
                    reqPermissionSMS();
                }
                return;
            }
        }
    }

}
