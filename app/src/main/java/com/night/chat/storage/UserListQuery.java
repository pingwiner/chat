package com.night.chat.storage;

import android.database.Cursor;

/**
 * Created by nightrain on 10/17/15.
 */
public class UserListQuery {
    public static Cursor getCursor() {
        return DBHelper.getDb().rawQuery("select u._id, u.nickname "
                + "from users as u left join preferences as p "
                + "on u._id = p.my_user_id "
                + "where p.my_user_id is null" , new String[] {});
    }

    public static class Row {
        public long userId;
        public String title;

        public Row(Cursor cursor) {
            userId = cursor.getLong(0);
            title = cursor.getString(1);
        }
    }
}
