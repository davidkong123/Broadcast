package com.example.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Bundle extras = intent.getExtras();
        Toast.makeText(context, "hello world", Toast.LENGTH_LONG).show();
        if (extras != null) {
            Toast.makeText(context, "hello world", Toast.LENGTH_LONG).show();
            String state = extras.getString(TelephonyManager.EXTRA_STATE);
            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                Toast.makeText(context, "hello world", Toast.LENGTH_LONG).show();
                String phoneNum = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                if (phoneNum != null) {
                    Toast.makeText(context, "incoming call: "+phoneNum, Toast.LENGTH_LONG).show();
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNum, null, "i'm busy right now leave message after the bip... bip", null, null);
                }
            }
        }
    }

}