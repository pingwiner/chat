package com.night.chat.network.request;

import com.night.chat.model.Message;
import com.night.chat.network.Api;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

/**
 * Created by nightrain on 10/18/15.
 */
public class MessagesRequest extends RetrofitSpiceRequest<Message.List, Api> {
    private long chatId;
    private long lastMsgId;

    public MessagesRequest(long chatId, long lastMsgId) {
        super(Message.List.class, Api.class);
        this.chatId = chatId;
        this.lastMsgId = lastMsgId;
    }

    @Override
    public Message.List loadDataFromNetwork() throws Exception {
        try {
            return getService().getMessages(chatId, lastMsgId);
        } catch (retrofit.RetrofitError e) {
            return null;
        }
    }
}
