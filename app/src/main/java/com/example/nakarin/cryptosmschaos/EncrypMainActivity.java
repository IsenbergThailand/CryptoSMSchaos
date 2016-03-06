package com.example.nakarin.cryptosmschaos;

import android.os.Bundle;
import android.app.Activity;

import android.view.Menu;
import android.telephony.SmsManager;  // sms-manager
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;

public class EncrypMainActivity extends Activity {
	/* pull up user interface data */
	EditText recNum;
	EditText msgContent;
	EditText passwd;
	Button send;
	Button cancel;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sender);

/* initial parameter */
		recNum = (EditText) findViewById(R.id.editText1);
		passwd = (EditText) findViewById(R.id.editText2);
		msgContent = (EditText) findViewById(R.id.editText3);
		send = (Button) findViewById(R.id.button1);
		cancel = (Button) findViewById(R.id.button2);

/*  Action of button */
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		}); 		// end cancel action.

		send.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String recNumString = recNum.getText().toString();  // phone-number
				String msgContentString = msgContent.getText().toString();  // sms-text
				String key_serect =passwd.getText().toString();	   // password
	//			EncrypMainActivity str = new EncrypMainActivity();
				try {
					newLibCore sysKey = new newLibCore();
					int size_sms= msgContent.length();

					String hexString =  sysKey.encrypted(msgContentString, key_serect);

					/*String strKey = sysKey.genKeyMain(key_serect, size_sms);
					String inBin=sysNewClass.AsciiToBinary(msgContentString);    // conv Bin
					int[] binInArray=new int[strKey.length()];
					int[] binKEYArray=new int[strKey.length()];
					int[] binOutputArray=new int[strKey.length()];

					//// XOR bin vs bin  ////
					for (int i=0;i<inBin.length();i++){
						binInArray[i] = Integer.parseInt(inBin.substring(i,i+1));
						binKEYArray[i] = Integer.parseInt(strKey.substring(i,i+1));
						binOutputArray[i]=binInArray[i] ^binKEYArray[i] ;
					}

					StringBuilder sb = new StringBuilder(binOutputArray.length);
					for (int i : binOutputArray) {
						sb.append(i);
					}
					String s = sb.toString();
					String hexString = new BigInteger(s, 2).toString(16);*/

					sendSMS(recNumString,hexString);

					finish();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}



				// sendSMS(recNumString,msgContentString);

				finish();
			}
		});


	} // end onCreate
	public static void sendSMS(String recNumString, String encryptedMsg) {
		try {

			// get a SmsManager
			SmsManager smsManager = SmsManager.getDefault();

			// Message may exceed 160 characters
			// need to divide the message into multiples
			ArrayList<String> parts = smsManager.divideMessage(encryptedMsg);
			smsManager.sendMultipartTextMessage(recNumString, null, parts,null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
} // end class
