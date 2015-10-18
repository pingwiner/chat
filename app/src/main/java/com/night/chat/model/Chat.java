package com.night.chat.model;

import android.database.Cursor;

import java.util.List;

/**
 * Created by nightrain on 10/12/15.
 */
public class Chat extends Model {
    public String title;
    public List<User> participants;
    public Message lastMessage;
    public User owner;

    public static long createChatWith(long userId) {
        return 0;
    }
}
