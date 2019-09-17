package com.example.pubgtournament.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersData {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("pubg_name")
    @Expose
    private String pubgName;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email_id")
    @Expose
    private String emailId;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("refer_code")
    @Expose
    private String referCode;
    @SerializedName("user_balance")
    @Expose
    private String userBalance;
    @SerializedName("bonus")
    @Expose
    private String bonus;
    @SerializedName("withdraw_balance")
    @Expose
    private String withdrawBalance;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPubgName() {
        return pubgName;
    }

    public void setPubgName(String pubgName) {
        this.pubgName = pubgName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReferCode() {
        return referCode;
    }

    public void setReferCode(String referCode) {
        this.referCode = referCode;
    }

    public String getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(String userBalance) {
        this.userBalance = userBalance;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getWithdrawBalance() {
        return withdrawBalance;
    }

    public void setWithdrawBalance(String withdrawBalance) {
        this.withdrawBalance = withdrawBalance;
    }

}
