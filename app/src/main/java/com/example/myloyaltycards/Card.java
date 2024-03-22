package com.example.myloyaltycards;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class Card {
    private String companyName;
    private Bitmap logo;
    private String clientCode;
    private boolean QRCode;
    private int backgroundColor;

    public Card(String companyName, Bitmap logo, String clientCode, boolean QRCode, int backgroundColor) {
        this.companyName = companyName;
        this.logo = logo;
        this.clientCode = clientCode;
        this.QRCode = QRCode;
        this.backgroundColor = backgroundColor;
    }

    public Card() {
        this.companyName = "";
        this.logo = null;
        this.clientCode = "";
        this.QRCode = false;
        this.backgroundColor = 0;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Bitmap getLogo() {
        return logo;
    }

    public void setLogo(Bitmap logo) {
        this.logo = logo;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public boolean isQRCode() {
        return QRCode;
    }

    public void setQRCode(boolean QRCode) {
        this.QRCode = QRCode;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Bitmap generateCode(int width, int heigth){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        if(QRCode){
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(clientCode, BarcodeFormat.QR_CODE, width, heigth);
                Bitmap bitmap = Bitmap.createBitmap(width, heigth, Bitmap.Config.RGB_565);
                for (int i = 0; i<width; i++){
                    for (int j = 0; j<heigth; j++){
                        bitmap.setPixel(i,j,bitMatrix.get(i,j)? Color.BLACK:Color.WHITE);
                    }
                }

                return bitmap;
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(clientCode, BarcodeFormat.CODE_128, width, heigth);
                Bitmap bitmap = Bitmap.createBitmap(width, heigth, Bitmap.Config.RGB_565);
                for (int i = 0; i<width; i++){
                    for (int j = 0; j<heigth; j++){
                        bitmap.setPixel(i,j,bitMatrix.get(i,j)? Color.BLACK:Color.WHITE);
                    }
                }

                return bitmap;
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "Card{" +
                "companyName='" + companyName + '\'' +
                ", logo='" + logo + '\'' +
                ", clientCode='" + clientCode + '\'' +
                ", QRCode=" + QRCode +
                ", backgroundColor='" + backgroundColor + '\'' +
                '}';
    }
}
