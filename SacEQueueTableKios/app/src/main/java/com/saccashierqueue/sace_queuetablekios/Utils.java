package com.saccashierqueue.sace_queuetablekios;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import org.w3c.dom.Text;

/**
 * Created by Keji's Lab on 27/02/2018.
 */

public class Utils {
    public static String dirQueueNumber = "queue-number";
    public static String transactionOptions = "transactionOptions";
    public static String kiosPinCode  = "kiosPin";
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
}
