package project.emp.fri.si.debtchecker;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jan on 9. 01. 2018.
 */

public class Payment implements Comparable<Payment>{

    private int paymentId;
    private double amount;
    private String date;
    private int payerId, recipientId;

    public Payment(String dataStr) {

        String[] dataArr = dataStr.split(" ");
        this.paymentId = Integer.parseInt(dataArr[0]);
        this.amount = Double.parseDouble(dataArr[1]);
        this.date = dataArr[2];
        this.payerId = Integer.parseInt(dataArr[3]);
        this.recipientId = Integer.parseInt(dataArr[4]);
    }

    public Payment(int paymentId, int payerId, int recipientId, double amount, String date) {

        this.paymentId = paymentId;
        this.amount = amount;
        this.date = date;
        this.payerId = payerId;
        this.recipientId = recipientId;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPayerId() {
        return payerId;
    }

    public void setPayerId(int payerId) {
        this.payerId = payerId;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public String toString() {

        return this.payerId + " " + this.recipientId + " " + this.amount + " " + this.date;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    @Override
    public int compareTo(@NonNull Payment o) {
        SimpleDateFormat datum_format = new SimpleDateFormat("dd.MM.yyyy");
        Date d1 = new Date();
        Date d2 = new Date();
        try{
            d1 = datum_format.parse(this.date);
            d2 = datum_format.parse(o.getDate());
        }catch(ParseException e){
            e.toString();
        }finally{
            return d1.compareTo(d2);
        }
    }
}
