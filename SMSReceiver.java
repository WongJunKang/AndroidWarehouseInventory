package com.example.warehouseinventoryapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver{
    public static final String SMS_FILTER = "SMS_FILTER";
    public static final String SMS_MSG_KEY = "SMS_MSG_KEY";

    /*
     * This method 'onReceive' will be invoked with each new incoming SMS
     * */

    @Override
    public void onReceive(Context context, Intent intent) {

        /*
        * Use the Telephony class to extract the incoming messages from the intent
        Receive sms intents and store sms messages into an Java array of type SmsMessage.
        Because SmsMessage can only store a limited amount of characters, hence any messages
        that surpassed the maximum capacity of SmsMessage will be stored as the next array element.
         */
        SmsMessage[]messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);

        // loop through the SmsMessage message
        for(int i=0; i < messages.length; i++){
            SmsMessage currentMessage = messages[i];
            // get message texts.
            String message = currentMessage.getDisplayMessageBody();

            /*
             Now, for each new message, send a broadcast contains the new message to MainActivity
             The MainActivity has to tokenize the new message and update the UI
             */
            Intent msgIntent = new Intent();
            msgIntent.setAction(SMS_FILTER);
            msgIntent.putExtra(SMS_MSG_KEY, message);
            context.sendBroadcast(msgIntent);

        }

    }
}
