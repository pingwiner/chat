package com.night.chat.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.night.chat.model.Message;

import java.util.Date;

/**
 * Created by nightrain on 10/12/15.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "chat_db";
    private static final int DB_VERSION = 1;
    private static DBHelper instance;
    private static SQLiteDatabase db;

    public static void init(Context context) {
        if (instance != null) return;
        instance = new DBHelper(context.getApplicationContext());
        db = instance.getWritableDatabase();
    }

    public static SQLiteDatabase getDb() {
        return db;
    }

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private void createDB(SQLiteDatabase db) {
        db.execSQL("create table users ("
                + "_id integer primary key, "
                + "nickname text" + ");");

        db.execSQL("create table chats ("
                + "_id integer primary key, "
                + "title text, "
                + "owner integer, "
                + "is_group integer default 0, "
                + "foreign key (owner) references users(_id)" + ");");

        db.execSQL("create table messages ("
                + "_id integer primary key, "
                + "chat_id integer, "
                + "msg_text text, "
                + "creation_time integer, "
                + "state integer, "
                + "creator integer, "
                + "foreign key (chat_id) references chats(_id), "
                + "foreign key (creator) references users(_id)" + ");");

        db.execSQL("create table participants ("
                + "chat_id integer, "
                + "user_id integer, "
                + "primary key (chat_id, user_id), "
                + "foreign key (chat_id) references chats(_id), "
                + "foreign key (user_id) references users(_id)" + ");");

        db.execSQL("create table preferences ("
                + "my_user_id integer "
                + ");");

        TestDataCreator.run(db);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion <= oldVersion) return;
        db.execSQL("drop table if exists messages;");
        db.execSQL("drop table if exists chats;");
        db.execSQL("drop table if exists users;");
        createDB(db);
    }

    public static long getChatWithUser(long userId) {
        Cursor cursor = db.rawQuery("select c._id from chats as c "
                + "inner join participants as p "
                + "on c._id = p.chat_id and p.user_id = ? "
                + "where is_group = 0", new String[]{Long.toString(userId)});

        if (cursor.moveToNext()) {
            long result = cursor.getLong(0);
            cursor.close();
            return result;
        }
        cursor.close();
        return 0;
    }

    public static long getMyId() {
        Cursor cursor = db.rawQuery("select my_user_id from preferences limit 1", null);
        cursor.moveToNext();
        long result = cursor.getLong(0);
        cursor.close();
        return result;
    }

    public static void setMessagesViewed(long chatId, long lastViewedTime, long myId) {
        ContentValues cv = new ContentValues();
        cv.put("state", Message.STATE_READ);
        db.update("messages", cv, "chat_id = ? and creation_time <= ? and creator != ?", new String[]{
                Long.toString(chatId),
                Long.toString(lastViewedTime),
                Long.toString(myId)
        });
    }

    public static long sendMessage(long chatId, String msg) {
        long creationTime = new Date().getTime();
        ContentValues cv = new ContentValues();
        cv.put("_id", -(creationTime << 8) - chatId);
        cv.put("chat_id", chatId);
        cv.put("msg_text", msg);
        cv.put("creation_time", creationTime);
        cv.put("state", Message.STATE_OUTGOING);
        cv.put("creator", getMyId());
        db.insert("messages", null, cv);
        return creationTime;
    }

    public static int getLastUnreadMessage(long chatId) {
        Cursor cursor = db.rawQuery("select count(*) from messages " +
                        "where creation_time < " +
                        "(select creation_time from messages " +
                        "where chat_id = ? and state = ? " +
                        "order by creation_time limit 1)",
                new String[]{Long.toString(chatId), Long.toString(Message.STATE_RECEIVED)});
        if (cursor.moveToNext()) {
            int result = cursor.getInt(0);
            cursor.close();
            return result;
        }
        cursor.close();
        return 0;
    }

    public static long getLastMessageId(long chatId) {
        Cursor cursor = db.rawQuery("select _id from messages " +
                "order by _id desc limit 1", null);
        if (cursor.moveToNext()) {
            long result = cursor.getLong(0);
            cursor.close();
            return result;
        }
        cursor.close();
        return 0;
    }

    public static void messageSent(long chatId, long creation_time, Long messageId) {
        ContentValues cv =  new ContentValues();
            cv.put("_id", messageId);
            db.update("messages", cv, "chat_id = ? and creation_time = ?",
                    new String[]{Long.toString(chatId), Long.toString(creation_time)});
    }

    public static void saveIncomingMessage(Message message) {
        ContentValues cv = new ContentValues();
        cv.put("_id", message.id);
        cv.put("chat_id", message.chat_id);
        cv.put("msg_text", message.msg_text);
        cv.put("creation_time", message.creation_time);
        cv.put("state", Message.STATE_RECEIVED);
        cv.put("creator", message.creator);
        db.insert("messages", null, cv);
    }
}
