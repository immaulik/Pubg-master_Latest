package com.example.pubgtournament.Models;

/**
 * Created by Darshan Soni on 25-Jun-19.
 */
public class UpdateAmount {

    private int user_id,user_balance;

    public UpdateAmount(int user_id, int user_balance) {
        this.user_id = user_id;
        this.user_balance = user_balance;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_balance() {
        return user_balance;
    }

    public void setUser_balance(int user_balance) {
        this.user_balance = user_balance;
    }
}
