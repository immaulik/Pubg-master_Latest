package com.example.pubgtournament.Models;

/**
 * Created by Darshan Soni on 13-Jun-19.
 */
public class Tournament {

    String match_id,match_fee,first_prize,match_number,Total;

    public Tournament(String match_id, String match_fee, String first_prize, String match_number,String Total) {
        this.match_id = match_id;
        this.match_fee = match_fee;
        this.first_prize = first_prize;
        this.match_number = match_number;
        this.Total=Total;
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
        this.Total = total;
    }
}
