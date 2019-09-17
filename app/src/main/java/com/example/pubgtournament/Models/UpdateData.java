package com.example.pubgtournament.Models;

/**
 * Created by Darshan Soni on 16-Jun-19.
 */
public class UpdateData {

    String first_name;
    String last_name;
    String mobile_number;

    public UpdateData( String first_name, String last_name, String mobile_number) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.mobile_number = mobile_number;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }
}
