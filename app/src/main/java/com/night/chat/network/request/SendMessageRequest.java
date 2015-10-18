package com.night.chat.network.request;

import android.util.Log;

import com.night.chat.model.Id;
import com.night.chat.model.Message;
import com.night.chat.network.Api;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

/**
 * Created by nightrain on 10/18/15.
 */
public class SendMessageRequest extends RetrofitSpiceRequest<Long, Api> {
    private long chatId;
    private Message message;

    public SendMessageRequest(long chatId, Message message) {
        super(Long.class, Api.class);
        this.chatId = chatId;
        this.message = message;
    }

    @Override
    public Long loadDataFromNetwork() throws Exception {
        try {
            Id id = getService().sendMessage(chatId, message);
            if (id == null) return null;
            return id.id;
        } catch (retrofit.RetrofitError e) {
            e.printStackTrace();
            return null;
        }
    }
}
