package com.saccashierqueue.sace_queuetablekios;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Keji's Lab on 27/02/2018.
 */

public class Utils {
    public static String dirQueueNumber = "queue-number";
    public static String transactionOptions = "transactionOptions";
    public static String transactionOptionsUnfix = "transactionOptionsUnfix";
    public static String kiosPinCode  = "kiosPin";
    public static String queueStudentNumber = "queueStudentNumber";
    public static String getLatestNumber = "getLatestNumber";
    public static String studentTransactions = "studentTransactions";
    public static String transactionDetails = "transactionDetails";
    public static String transactionSelectedList = "transactionSelectedList";
    public static String sendSMS = "sendSMS";
    public static void errorMessageDialog(Context context,String msg){
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(true);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.message_dailog);
        TextView lblDone = (TextView) dialog.findViewById(R.id.lblDone);
        TextView message = (TextView) dialog.findViewById(R.id.messgae);
        message.setText(msg);
        dialog.show();
        lblDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public static String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
    public static boolean validateForm(EditText transactionName, EditText transactionCost) {
        boolean valid = true;

        String name = transactionName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            transactionName.setError("Required.");
            valid = false;
        } else {
            transactionName.setError(null);
        }

        String address = transactionCost.getText().toString();
        if (TextUtils.isEmpty(address)) {
            transactionCost.setError("Required.");
            valid = false;
        } else {
            transactionCost.setError(null);
        }

        return valid;
    }
}
