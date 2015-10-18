package com.night.chat.network;

import com.night.chat.model.Id;
import com.night.chat.model.Message;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by nightrain on 10/18/15.
 */
public interface Api {

    @FormUrlEncoded
    @POST("/send/{chatId}")
    Id sendMessage(@Path("chatId") long chatId, @Field("message") Message message);

    @GET("/messages/{chatId}/{lastId}")
    Message.List getMessages(@Path("chatId") long chatId, @Path("lastId") long lastId);
}
