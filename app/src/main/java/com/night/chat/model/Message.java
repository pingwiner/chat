package com.night.chat.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

/**
 * Created by nightrain on 10/12/15.
 */
public class Message extends Model {
    public long id;
    public long chat_id;
    public String msg_text;
    public long creation_time;
    public long state;
    public long creator;

    public static final int STATE_OUTGOING  = 0;
    public static final int STATE_SENT      = 1;
    public static final int STATE_RECEIVED  = 2;
    public static final int STATE_READ      = 3;

    public static class List extends ArrayList<Message> {}

    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
