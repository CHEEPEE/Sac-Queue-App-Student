package com.sacequeue.sace_queueapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    Button signUpBtn;
    FirebaseAuth mAuth;
    String TAG = "SignUpActivity";
    EditText studentNumber,email,password,confirmpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        password = (EditText) findViewById(R.id.inpt_password);
        confirmpassword = (EditText)findViewById(R.id.inpt_cnfrm_password);
        studentNumber = (EditText) findViewById(R.id.input_student_number);
        email = (EditText) findViewById(R.id.inpt_email);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           createUser(email.getText().toString(),password.getText().toString());
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (confirmPass(password,confirmpassword)){
                    if (validateForm(studentNumber,email,password,confirmpassword)){
                        signUpBtn.setEnabled(true);
                    }
                }
            }
        });
        confirmpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               if (confirmPass(password,confirmpassword)){
                   if (validateForm(studentNumber,email,password,confirmpassword)){
                       signUpBtn.setEnabled(true);
                   }
               }
            }
        });





    }

    private boolean confirmPass(EditText password,EditText cnfirmPassword){
        boolean valid = true;

        if (!password.getText().toString().equals(cnfirmPassword.getText().toString())){
            valid= false;
        }
        return valid;

    }
    private void createUser(String email,String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserManagerMap userManagerMap = new UserManagerMap(user.getUid(),studentNumber.getText().toString());
                            Map<String,Object> userManagerVal = userManagerMap.toMap();
                            Map<String,Object> childUpdate = new HashMap<>();
                            childUpdate.put(user.getUid(),userManagerVal);
                            FirebaseDatabase.getInstance().getReference().child("studentUsers").updateChildren(childUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent i = new Intent(SignUpActivity.this,LoginActivity.class);
                                    startActivity(i);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println("Firebase User Write: "+e);
                                }
                            });
                          /*  String key = mDatabase.push().getKey();
                            CategoryMapModel categoryMapModel = new CategoryMapModel(key,dialog.getInputEditText().getText().toString());
                            Map<String,Object> categoryVal = categoryMapModel.toMap();
                            Map<String,Object> childUpdate = new HashMap<>();
                            childUpdate.put(key,categoryVal);
                            mDatabase.child(Utils.storeItemCategory).child(mAuth.getCurrentUser().getUid()).updateChildren(childUpdate);*/

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
    private Boolean validateForm(EditText student_number, EditText email, EditText password,EditText confirmpassword){
        boolean valid = true;
        String name = student_number.getText().toString();
        if (TextUtils.isEmpty(name)) {
            student_number.setError("Required.");
            valid = false;
        } else {
            student_number.setError(null);
        }

        String address = email.getText().toString();
        if (TextUtils.isEmpty(address)) {
            email.setError("Required.");
            valid = false;
        } else {
            email.setError(null);
        }

        String contact = password.getText().toString();
        if (TextUtils.isEmpty(contact)) {
            password.setError("Required.");
            valid = false;
        } else {
            password.setError(null);
        }
        String cnfirmpass = confirmpassword.getText().toString();
        if (TextUtils.isEmpty(contact)) {
            confirmpassword.setError("Required.");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }


}
