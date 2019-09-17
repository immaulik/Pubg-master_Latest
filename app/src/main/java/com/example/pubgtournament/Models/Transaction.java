package com.example.pubgtournament.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transaction {

    @SerializedName("t_id")
    @Expose
    private String tId;
    @SerializedName("pubg_name")
    @Expose
    private String pubgName;
    @SerializedName("history")
    @Expose
    private String history;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("timing")
    @Expose
    private String timing;
    @SerializedName("first_prize")
    @Expose
    private String firstPrize;
    @SerializedName("per_kill")
    @Expose
    private String entryFee;
    @SerializedName("match_version")
    @Expose
    private String matchNumber;
    @SerializedName("result")
    @Expose
    private String results;
    @SerializedName("usersKill")
    @Expose
    private String usersKill;
    @SerializedName("killPayment")
    @Expose
    private String killPayment;
    @SerializedName("chickenStatus")
    @Expose
    private String chickenStatus;
    @SerializedName("userPrize")
    @Expose
    private String userPrize;
    @SerializedName("Joined_Status")
    @Expose
    private String Joined_Status;

    public Transaction(String tId, String pubgName, String history, String date, String timing, String firstPrize, String entryFee, String matchNumber, String results, String usersKill, String killPayment, String chickenStatus, String userPrize, String joined_Status) {
        this.tId = tId;
        this.pubgName = pubgName;
        this.history = history;
        this.date = date;
        this.timing = timing;
        this.firstPrize = firstPrize;
        this.entryFee = entryFee;
        this.matchNumber = matchNumber;
        this.results = results;
        this.usersKill = usersKill;
        this.killPayment = killPayment;
        this.chickenStatus = chickenStatus;
        this.userPrize = userPrize;
        Joined_Status = joined_Status;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public String getPubgName() {
        return pubgName;
    }

    public void setPubgName(String pubgName) {
        this.pubgName = pubgName;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getFirstPrize() {
        return firstPrize;
    }

    public void setFirstPrize(String firstPrize) {
        this.firstPrize = firstPrize;
    }

    public String getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(String entryFee) {
        this.entryFee = entryFee;
    }

    public String getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(String matchNumber) {
        this.matchNumber = matchNumber;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getUsersKill() {
        return usersKill;
    }

    public void setUsersKill(String usersKill) {
        this.usersKill = usersKill;
    }

    public String getKillPayment() {
        return killPayment;
    }

    public void setKillPayment(String killPayment) {
        this.killPayment = killPayment;
    }

    public String getChickenStatus() {
        return chickenStatus;
    }

    public void setChickenStatus(String chickenStatus) {
        this.chickenStatus = chickenStatus;
    }

    public String getUserPrize() {
        return userPrize;
    }

    public void setUserPrize(String userPrize) {
        this.userPrize = userPrize;
    }

    public String getJoined_Status() {
        return Joined_Status;
    }

    public void setJoined_Status(String joined_Status) {
        Joined_Status = joined_Status;
    }
}
