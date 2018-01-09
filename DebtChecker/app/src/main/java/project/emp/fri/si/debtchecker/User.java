package project.emp.fri.si.debtchecker;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Jan on 29. 12. 2017.
 */

public class User {

    private int id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String nickname;
    private String password;
    private ArrayList<Payment> payments;

    User(int id) {

        this.id = id;
        if (id >= 0) {
            String[] attributes = DBInterface.queryUserAll(this.id).split(" ");

            this.name = attributes[1];
            this.surname = attributes[2];
            this.email = attributes[3];
            this.nickname = attributes[4];
            this.username = attributes[5];
            this.password = attributes[6];
            this.payments = new ArrayList<>();

            String[] payment_dataStrs = DBInterface.queryPayments(this.id);
            for (String s : payment_dataStrs)
                this.payments.add(new Payment(s));
        }
    }

    User(int id, String name, String surname, String username, String email, String nickname, String password) {

        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Payment[] getPayments() {
        return (Payment[]) this.payments.toArray();
    }

    public String toString() {
        return id + " " + name + " " + surname + " " + email + " " + username + " " + nickname;
    }
}
