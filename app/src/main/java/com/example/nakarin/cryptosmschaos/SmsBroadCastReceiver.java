package com.example.nakarin.cryptosmschaos;


import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsBroadCastReceiver extends BroadcastReceiver {
	public static final String SMS_EXTRA_NAME = "pdus";
	public static final String SMS_URI = "content://sms";
	
	public static final String ADDRESS = "address";
    public static final String PERSON = "person";
    public static final String DATE = "date";
    public static final String READ = "read";
    public static final String STATUS = "status";
    public static final String TYPE = "type";
    public static final String BODY = "body";
    public static final String SEEN = "seen";
    
    public static final int MESSAGE_TYPE_INBOX = 1;
    public static final int MESSAGE_TYPE_SENT = 2;
    
    public static final int MESSAGE_IS_NOT_READ = 0;
    public static final int MESSAGE_IS_READ = 1;
    
    public static final int MESSAGE_IS_NOT_SEEN = 0;
    public static final int MESSAGE_IS_SEEN = 1;
 @Override
 public void onReceive(Context context, Intent intent) {

 Bundle bundle = intent.getExtras();

 // Specify the bundle to get object based on SMS protocol "pdus"
 Object[] object = (Object[]) bundle.get("pdus");
 SmsMessage sms[] = new SmsMessage[object.length]; 
 Intent in=new Intent(context,DecMainActivity.class);
 in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
 in.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
 String msgContent = "";
 String originNum = "";
 StringBuffer sb = new StringBuffer();
 ContentResolver contentResolver = context.getContentResolver();
 Bundle extras = intent.getExtras();
 // Get received SMS array
 Object[] smsExtra = (Object[]) extras.get( SMS_EXTRA_NAME );
 
 // Get ContentResolver object for pushing encrypted SMS to incoming folder
 
 for (int i = 0; i < object.length; i++) {

 sms[i] = SmsMessage.createFromPdu((byte[]) object[i]);
 SmsMessage smds = SmsMessage.createFromPdu((byte[])smsExtra[i]);
 // get the received SMS content
 msgContent = sms[i].getDisplayMessageBody();
 
 //get the sender phone number
 originNum = sms[i].getDisplayOriginatingAddress();
 
 //aggregate the messages together when long message are fragmented
 sb.append(msgContent);
 
 putSmsToDatabase2( contentResolver, smds );
 
 //abort broadcast to cellphone inbox
 abortBroadcast();

 }
 
 //fill the sender's phone number into Intent
 in.putExtra("originNum", originNum);
 
 //fill the entire message body into Intent
 in.putExtra("msgContent", new String(sb));
 
 //start the DisplaySMSActivity.java
 context.startActivity(in);
 
 }
 private void putSmsToDatabase2( ContentResolver contentResolver, SmsMessage sms )
	{
		// Create SMS row
     ContentValues values = new ContentValues();
     values.put( ADDRESS, sms.getOriginatingAddress() );
     values.put( DATE, sms.getTimestampMillis() );
     values.put( READ, MESSAGE_IS_NOT_READ );
     values.put( STATUS, sms.getStatus() );
     values.put( TYPE, MESSAGE_TYPE_INBOX );
     values.put( SEEN, MESSAGE_IS_NOT_SEEN );
     try
     {
    
     	String encrypted = sms.getMessageBody().toString(); 
     	

     	values.put( BODY, encrypted );
     	
     }
     catch ( Exception e ) 
     { 
     	e.printStackTrace(); 
 	}
     
     // Push row into the SMS table
     contentResolver.insert( Uri.parse( SMS_URI ), values );
	}
}