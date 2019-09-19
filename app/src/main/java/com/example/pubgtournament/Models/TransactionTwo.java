package com.example.pubgtournament.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionTwo {

        @SerializedName("match_id")
        @Expose
        private String match_id;
        @SerializedName("match_fee")
        @Expose
        private String match_fee;
        @SerializedName("first_prize")
        @Expose
        private String first_prize;
        @SerializedName("match_number")
        @Expose
        private String match_number;
        @SerializedName("Total")
        @Expose
        private String Total;

    public TransactionTwo(String match_id, String match_fee, String first_prize, String match_number, String total) {
        this.match_id = match_id;
        this.match_fee = match_fee;
        this.first_prize = first_prize;
        this.match_number = match_number;
        Total = total;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getMatch_fee() {
        return match_fee;
    }

    public void setMatch_fee(String match_fee) {
        this.match_fee = match_fee;
    }

    public String getFirst_prize() {
        return first_prize;
    }

    public void setFirst_prize(String first_prize) {
        this.first_prize = first_prize;
    }

    public String getMatch_number() {
        return match_number;
    }

    public void setMatch_number(String match_number) {
        this.match_number = match_number;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }
}
