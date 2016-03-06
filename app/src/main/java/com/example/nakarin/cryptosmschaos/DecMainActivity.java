package com.example.nakarin.cryptosmschaos;


import java.math.BigInteger;
import java.util.ArrayList;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;


public class DecMainActivity extends Activity {

 EditText secretKey;
 TextView senderNum;
 TextView encryptedMsg;
 TextView decryptedMsg;
 Button submit;
 Button cancel;
 String originNum = "";
 String msgContent = "";
 EditText c1,c2; 
 
 @Override
 public void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.rec);

 senderNum = (TextView) findViewById(R.id.senderNum);
 encryptedMsg = (TextView) findViewById(R.id.encryptedMsg);
 decryptedMsg = (TextView) findViewById(R.id.decryptedMsg);
 submit = (Button) findViewById(R.id.submit);
 cancel = (Button) findViewById(R.id.cancel);
 c1 = (EditText) findViewById(R.id.editText1); 

 Bundle extras = getIntent().getExtras();
 if (extras != null) {

 originNum = extras.getString("originNum");
 msgContent = extras.getString("msgContent");
 senderNum.setText(originNum);
 encryptedMsg.setText(msgContent);
 } else {


 Toast.makeText(getBaseContext(), "Error Occurs!",
 Toast.LENGTH_SHORT).show();
 finish();
 }


 cancel.setOnClickListener(new View.OnClickListener() {

 public void onClick(View v) {
 finish();

 }
 });


 submit.setOnClickListener(new View.OnClickListener() { 
	 public void onClick(View v) {
         String decryptedKey=c1.getText().toString();
         String Cipher = msgContent;
         newLibCore sysKey = new newLibCore();


	 try {

     //    int size_Cip = Cipher.length()/2;
     //    int size_binCip =Cipher.length()*4;
     //    String DstrKey = sysKey.genKeyMain(decryptedKey, size_Cip);

      //   String cipBIN = sysNewClass.hexToBinary(Cipher,size_binCip);
         String test3 =  sysKey.decrypted(Cipher, decryptedKey);
         String asciiPlain =sysKey.convertHexToString(test3);

     /*    int[] binCipArray=new int[DstrKey.length()];
         int[] binPlainArray=new int[DstrKey.length()];
         int[] binKEYArrayD=new int[DstrKey.length()];

         double[] DbeginIn=new double[DstrKey.length()];
         for ( int i=0;i<=(DstrKey.length())-1;i++){
             DbeginIn[i] = Integer.parseInt(DstrKey.substring(i,i+1));  /// convert to array
         }

         for (int i=0;i<cipBIN.length();i++){
             binCipArray[i] = Integer.parseInt(cipBIN.substring(i,i+1));
             binKEYArrayD[i] = Integer.parseInt(DstrKey.substring(i,i+1));
             binPlainArray[i]=binCipArray[i] ^binKEYArrayD[i] ;
         }

         StringBuilder tmpPlain = new StringBuilder(binPlainArray.length);
         for (int i2 : binPlainArray) {
             tmpPlain.append(i2);
         }
         String s2 = tmpPlain.toString();
         String hexPlain = new BigInteger(s2, 2).toString(16);

         String asciiPlain =sysSTR.convertHexToString(hexPlain);*/
       	 
        decryptedMsg.setText(asciiPlain);
		 
	//	 finish(); 
	 } catch (Exception e) {


		 decryptedMsg.setText("Message Cannot Be Decrypted!");
		 }

		 }
		 });
		 
		 }


} // end class
