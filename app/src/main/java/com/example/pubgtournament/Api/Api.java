package com.example.pubgtournament.Api;

import com.example.pubgtournament.Models.Tournament;
import com.example.pubgtournament.Models.Transaction;
import com.example.pubgtournament.Models.TransactionTwo;
import com.example.pubgtournament.Models.UsersData;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Darshan Soni on 13-Jun-19.
 */
public interface Api {

    @FormUrlEncoded
    @POST("createUser.php")
    Call<ResponseBody> createUser(
            @Field("pubg_name") String pubg_name,
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("email_id") String email_id,
            @Field("mobile_number") String mobile_number,
            @Field("password") String password,
            @Field("refer_code") String refer_code,
            @Field("user_balance") int user_balance,
            @Field("bonus") int bonus,
            @Field("withdraw_balance") int withdraw_balance
    );

    @FormUrlEncoded
    @POST("user_transaction.php")
    Call<ResponseBody> userTransaction(
            @Field("pubg_name") String pubg_name,
            @Field("history") String history,
            @Field("date") String date,
            @Field("timing") String time,
            @Field("first_prize") int first_prize,
            @Field("entry_fee")int entry_fee,
            @Field("match_number") int match_number,
            @Field("result") String result,
            @Field("usersKill") int usersKill,
            @Field("chickenStatus") String chickenStatus,
            @Field("killPayment") int killPayment,
            @Field("userPrize") int userPrize,
            @Field("Joined_Status") String Joined_Status
    );

    @GET("userJson.php?")
    Call<List<UsersData>> getUsers(
            @Query("pubg_name") String pubg_name
    );

    @GET("useremail.php?")
    Call<List<UsersData>> getEmail(
            @Query("email_id") String email_id
    );

    @GET("userTransaction.php?")
    Call<List<Transaction>> getUserById(
            @Query("result") String result
    );

    @GET("userHistory.php?")
    Call<List<Transaction>> getUserByName(
            @Query("pubg_name") String pubg_name
    );


    @GET("joinedUser.php?")
    Call<List<Transaction>> getJoinedUsers(
            @Query("match_number") String match_number
    );
    @GET("joinedUser.php?")
    Call<List<Transaction>> getJoinedUsers2(
            @Query("match_number") int match_number
    );
//    @GET("myContests.php?")
//    Call<List<Transaction>> getMyContest(
//            @Query("date") String date &
//            @Query("match_type") String match_type
//    );

    @GET("updateProfile.php")
    Call<ResponseBody> updateProfile(
            @Query("firstname") String firstname,
            @Query("lastname") String lastname,
            @Query("mobile") String mobile,
            @Query("user_id") int id
    );

    @GET("updatePassword.php")
    Call<ResponseBody> updatePassword(
            @Query("password") String firstname,
            @Query("user_id") int id
    );

    @GET("updateBalance.php")
    Call<ResponseBody> updateBalance(
            @Query("user_balance") int user_balance,
            @Query("user_id") int id
    );

    @GET("resultContests.php")
    Call<List<Transaction>> getResultContest(
            @Query("match_number") String match_number,
            @Query("result") String result
    );

    @GET("getKills.php")
    Call<List<Transaction>> getKills(
            @Query("match_number") String match_number,
            @Query("result") String result,
            @Query("chickenStatus") String chickenStatus
    );

    @FormUrlEncoded
    @POST("withdraw_req.php")
    Call<ResponseBody> withdrawRequest(
            @Field("pubg_name") String pubg_name,
            @Field("withdraw_balance") int withdraw_balance
    );

    @GET("soloJsonData.php")
    Call<List<Tournament>> getMatches();

    @GET("updateTotal.php")
    Call<ResponseBody> updateTotal(
            @Query("user_balance") float user_balance,
            @Query("bonus") float bonus,
            @Query("user_id") int id
    );

    @GET("getData.php")
    Call<List<UsersData>> getData(
            @Query("user_id") int user_id
    );

    @GET("updateBalance.php")
    Call<ResponseBody> updateBalanceFloat(
            @Query("user_balance") float user_balance,
            @Query("user_id") int id
    );

    @GET("getTotal.php")
    Call<List<TransactionTwo>> getTotal(
            @Query("match_number") int match_number
    );

    @GET("updateTotalUser.php")
    Call<ResponseBody> updateUser(
            @Query("match_number") int match_number,
            @Query("Total") int Total
    );
}
