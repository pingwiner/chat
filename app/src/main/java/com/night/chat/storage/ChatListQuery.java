package com.night.chat.storage;

import android.database.Cursor;

import com.night.chat.model.Message;

/**
 * Created by nightrain on 10/14/15.
 */
public class ChatListQuery {

    public static Cursor getCursor() {
        return DBHelper.getDb().rawQuery("select chats._id, " +
                "chats.title as chat_title, " +
                "count(messages._id) as unread_cnt, " +
                "(select msg_text from messages where messages.chat_id = chats._id order by creation_time desc limit 1), " +
                "(select creation_time from messages where messages.chat_id = chats._id order by creation_time desc limit 1) " +
                "from chats " +
                "left join messages on messages.chat_id = chats._id and messages.state = ? " +
                "group by chats._id", new String[]{Integer.toString(Message.STATE_RECEIVED)});
    }

    public static class Row {
        public long chatId;
        public String chatTitle;
        public int unreadMessagesCount;
        public String lastMsgText;
        public long lastMsgTime;

        public Row(Cursor cursor) {
            chatId = cursor.getLong(0);
            chatTitle = cursor.getString(1);
            unreadMessagesCount = cursor.getInt(2);
            lastMsgText = cursor.getString(3);
            lastMsgTime = cursor.getLong(4);
        }

    }

}
