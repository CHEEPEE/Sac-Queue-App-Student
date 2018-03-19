package com.sacequeue.sace_queueapp;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

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
    public static String transactionSelectedList = "transactionSelectedList";
    public static void errorMessageDialog(Context context,String msg,String label){
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(true);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.message_dialog);
        TextView messageLabel = (TextView) dialog.findViewById(R.id.messageLabel);
        TextView lblDone = (TextView) dialog.findViewById(R.id.lblDone);
        TextView message = (TextView) dialog.findViewById(R.id.messgae);
        messageLabel.setText(label);
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
}
