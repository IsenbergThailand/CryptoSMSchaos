package com.example.nakarin.cryptosmschaos;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

/**
 * Created by Nakarin on 3/6/2016.
 */

public class newLibCore {
    public static String decrypted(String cipherHEX, String pass)throws UnsupportedEncodingException {
        newLibCore str = new newLibCore();
        String key_serect =pass;
        String InputSMS = cipherHEX;
        int size_binCip =InputSMS.length()*4;
        String cipBIN = newLibCore.hexToBinary(InputSMS,size_binCip);
        int[] binInArray=new int[size_binCip];
        int[] binOutputArray1=new int[size_binCip];
        int[] binKP = new int[size_binCip];
        Double IV = makeInitialVAlue(key_serect);
        String hexString1 = null;
        String[] aX = new String[8];
        String[] aY = new String[8];
        aX=str.genXascii8(key_serect);   /// gen x [asjdba, idnv45, 687aas, ... idnv45]
        aY=str.genYascii8(key_serect);  // gen y
        double[] finP = new double[size_binCip];
        for (int i=0;i<8;i++){
            double C = Double.parseDouble(convBinToDec3(AsciiToBinary(aX[i])))-IV;
            double D = Double.parseDouble(convBinToDec3(AsciiToBinary(aY[i])))-IV;  // binInArray[i] = Integer.parseInt(inBin.substring(i,i+1));
            finP=str.KeyPlane(C,D,IV,size_binCip);    // gen Key [0.24175649366784313, 0.052027498168342845, -0.727368740026904....
            for (int j=0;j<size_binCip;j++){
                if (finP[j] < 0){
                    binKP[j]=0;
                    binInArray[j]=Integer.parseInt(cipBIN.substring(j,j+1));
                    binOutputArray1[j]=binInArray[j] ^binKP[j] ;
                }else{
                    binKP[j]=1;
                    binInArray[j]=Integer.parseInt(cipBIN.substring(j,j+1));
                    binOutputArray1[j]=binInArray[j] ^binKP[j] ;
                }
            }
            StringBuilder sb1 = new StringBuilder(binOutputArray1.length);
            for (int ie1 : binOutputArray1) {  sb1.append(ie1);}
            String se1 = sb1.toString();
            hexString1 = new BigInteger(se1, 2).toString(16);
        }
        return    hexString1;
    }

    public static String encrypted(String sms, String pass)throws UnsupportedEncodingException {
        newLibCore str = new newLibCore();
        String key_serect =pass;
        String InputSMS = sms;
        int sizeAllBits= InputSMS.length()*8;
        String inBin=AsciiToBinary(InputSMS);    // conv Bin
        int[] binInArray=new int[sizeAllBits];  //80
        int[] binOutputArray=new int[sizeAllBits];
        Double IV = makeInitialVAlue(key_serect);
        String hexString = null;
        String[] aX = new String[8];
        String[] aY = new String[8];
        aX=str.genXascii8(key_serect);   /// gen x [asjdba, idnv45, 687aas, ... idnv45]
        aY=str.genYascii8(key_serect);  // gen y
     /*   String[] cofX = new String[8];
        String[] cofY = new String[8];
            for(int i=0;i<8;i++){
                cofX[i]=convBinToDec3(AsciiToBinary(aX[i])); // gen cof [-1.0177658008442876, -11.00135594147757...
                cofY[i]=convBinToDec3(AsciiToBinary(aY[i])); // gen cof
            }      */
        double[] finP = new double[sizeAllBits];
        int[] binKP = new int[sizeAllBits];
        for (int i=0;i<8;i++){
            double C = Double.parseDouble(convBinToDec3(AsciiToBinary(aX[i])))-IV;
            double D = Double.parseDouble(convBinToDec3(AsciiToBinary(aY[i])))-IV;  // binInArray[i] = Integer.parseInt(inBin.substring(i,i+1));
            finP=str.KeyPlane(C,D,IV,sizeAllBits);    // gen Key [0.24175649366784313, 0.052027498168342845, -0.727368740026904....
            for (int j=0;j<sizeAllBits;j++){
                if (finP[j] < 0){
                    binKP[j]=0;
                    binInArray[j]=Integer.parseInt(inBin.substring(j,j+1));
                    binOutputArray[j]=binInArray[j] ^binKP[j] ;
                }else{
                    binKP[j]=1;
                    binInArray[j]=Integer.parseInt(inBin.substring(j,j+1));
                    binOutputArray[j]=binInArray[j] ^binKP[j] ;
                }
            }
            //      System.out.println("binKP : "+Arrays.toString(binOutputArray));
            StringBuilder sb = new StringBuilder(binOutputArray.length);
            for (int ie : binOutputArray) {  sb.append(ie);}
            String se = sb.toString();
            hexString = new BigInteger(se, 2).toString(16);

        }
        return    hexString;

    }


    public static String AsciiToBinary(String asciiString){

        byte[] bytes = asciiString.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes)
        {
            int val = b;
            for (int i = 0; i < 8; i++)
            {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return binary.toString();
    }
    public static double sum(double...values) {
        double result = 0;
        for (double value:values)
            result += value;
        return result;
    }
    String[] genXascii8( String s ) throws UnsupportedEncodingException{
        String key_serect = s;
// System.out.println("key 16 Chars: " + key_serect);
        String[] tmp1 = new String[8];
        String[] tmp2 = new String[8];
        String[] New1 = new String[8];
        int size,new_size = 0;
        int index,IndexEnd = 0;
        int index2 = 0;
        int length = 6;
        for(index=0;index<8;index++){
            IndexEnd=index2+length;
            if(IndexEnd>16){IndexEnd=16;}
            tmp1[index]=key_serect.substring(index2,IndexEnd);
            size=tmp1[index].length();
            if(size<6){
                new_size = 6-size;
                tmp2[index]=key_serect.substring(0,new_size);
                New1[index]=tmp1[index]+tmp2[index];
                index2=new_size;
            }else{
                New1[index]=tmp1[index];
                index2=IndexEnd;
            }
        }
        return New1;
    }
    String[] genYascii8( String s ) throws UnsupportedEncodingException{
        String key_serect = s;
// System.out.println("key 16 Chars: " + key_serect);
        String[] tmp1 = new String[8];
        String[] tmp2 = new String[8];
        String[] New1 = new String[8];
        int size,new_size = 0;
        int index,IndexEnd = 0;
        int index2 = 0;
        int length = 6;
        for(index=0;index<8;index++){
            IndexEnd=index2+length;
            if(IndexEnd>16){IndexEnd=16;}
            tmp1[index]=key_serect.substring(index2,IndexEnd);
            size=tmp1[index].length();
            if(size<6){
                new_size = 6-size;
                tmp2[index]=key_serect.substring(0,new_size);
                New1[index]=tmp1[index]+tmp2[index];
                index2=new_size;
            }else{
                New1[index]=tmp1[index];
                index2=IndexEnd;
            }
        }
        return New1;
    }
    public static String convBinToDec3( String s ){
        String str  =new StringBuilder(s).reverse().toString();   // reverse
        int[] newIntInput=new int[s.length()];
        int indexOfbit=s.length();
        double[] valueOfBit=new double[s.length()];
        double[] decInt=new double[s.length()];

        for ( indexOfbit=0;indexOfbit<=47;indexOfbit++){
            newIntInput[indexOfbit] = Integer.parseInt(str.substring(indexOfbit,indexOfbit+1));
        }
        for ( indexOfbit=1;indexOfbit<=5;indexOfbit++){
            valueOfBit[indexOfbit] = Math.pow(2,5-indexOfbit);
            decInt[indexOfbit]=newIntInput[indexOfbit]*valueOfBit[indexOfbit];
        }

        for ( indexOfbit=6;indexOfbit<=47;indexOfbit++){
            valueOfBit[indexOfbit] = Math.pow(2,-indexOfbit);
            decInt[indexOfbit]=newIntInput[indexOfbit]*valueOfBit[indexOfbit];
        }
        double sum4 = sum(decInt);

        if(newIntInput[0]==0){    sum4=sum4*1;
        }else{ sum4=sum4*(-1); }
        String total2 = String.valueOf(sum4);

        return total2;
    }


    double[] KeyPlane( double C ,double D,double yIn,int binSizeSMS) throws UnsupportedEncodingException{
        double[] toReturn = new double[binSizeSMS];
        double[] yyy = new double[binSizeSMS+2];

        Double C_cof = C; Double D_cof = D;

//    System.out.println("Cof1 : " + C + " Cof2 "+D);
        yyy[0]=yIn;
        yyy[1]=yIn;

        int k,j;
        for( k= 2; k < binSizeSMS+2; k++){
            double lo=C_cof*yyy[k-2];
            double li=D_cof*yyy[k-1];
            double lp = li+lo;
            double r = ((lp+1) % 2);
            if(r<0){r +=2;}
            yyy[k]  = r-1;
            toReturn[k-2]=yyy[k];
        }
        return toReturn;
    }
    /*################################################
   ##### convert Dec to Bin  -1<x<1
   ##### example 10100001 <- 1.0100001 <- -0.256
   ################################################*/
    String[] convDec2ToBin( String[] s )
    {
        int len = s.length;
        String[] toReturn = new String[len];
        StringBuilder sb = new StringBuilder();
        String[] yyy = new String[len];
        double[] inum = new double[len];
        int[] temper = new int[9];
        double[] temp = new double[9];
        double superman;
        String s4;
        for(int kk = 0; kk <len; kk++){
            inum[kk]=Double.parseDouble(s[kk]);
            if(inum[kk]<0){temper[0]=1;superman=inum[kk]*-1;}else{temper[0]=0;superman=inum[kk];}
            sb.append(temper[0]);
            for(int hh=1;hh<9;hh++){
                temp[0]=superman;
                temp[hh]=temp[hh-1]*2;
                if(temp[hh]<1)    {temper[hh]=0;}    else {temper[hh]=1;temp[hh]=temp[hh]-1;}
                sb.append(temper[hh]);
            }
            String con = sb.toString().substring(0,8);
            String con2 = sb.toString().substring(8,9);
            int number0 = Integer.parseInt(con, 2)+Integer.parseInt(con2, 2);
            String s3 = String.format("%8s",Integer.toBinaryString(number0));
            s4 = s3.replace(" ","0");
            yyy[kk]  =s4 ;
            sb.delete(0, sb.length());
            toReturn[kk] = ""+yyy[kk];
        }

        return toReturn;
    }
    public String convertHexToString(String hex){

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for( int i=0; i<hex.length()-1; i+=2 ){

            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char)decimal);

            temp.append(decimal);
        }
        // System.out.println("Decimal : " + temp.toString());

        return sb.toString();
    }
    public static String hexToBinary(String hexString,int size) {
        String pad_size ="%"+Integer.toString(size)+"s";
        String tmp1 = new BigInteger(hexString, 16).toString(2);
        String padded = String.format(pad_size, tmp1).replace(' ', '0');
        return padded;
    }

    public static Double makeInitialVAlue(String key_serect){
        String Bin1 =AsciiToBinary(key_serect);   //// convert to binn
         /* Init value */
        double[] beginIn=new double[Bin1.length()];
        for ( int i=0;i<=(Bin1.length())-1;i++){
            beginIn[i] = Integer.parseInt(Bin1.substring(i,i+1));  /// convert to array
        }
        double sum5 = (sum(beginIn))/16;     /// initial value

        return sum5;
    }
}