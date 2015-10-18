package com.night.chat.storage;

import android.database.Cursor;

/**
 * Created by nightrain on 10/17/15.
 */
public class MessageListQuery {
    public static Cursor getCursor(long chatId) {
        return DBHelper.getDb().rawQuery("select m._id, m.msg_text, m.creation_time, u.nickname, u._id from messages as m "
                + "left join users as u on u._id = m.creator "
                + "where m.chat_id = ? "
                + "order by m._id" , new String[]{Long.toString(chatId)});
    }

    public static class Row {
        public long id;
        public String text;
        public long time;
        public String nickname;
        public long userId;

        public Row(Cursor cursor) {
            id = cursor.getLong(0);
            text = cursor.getString(1);
            time = cursor.getLong(2);
            nickname = cursor.getString(3);
            userId = cursor.getLong(4);
        }
    }
}