package com.night.chat.storage;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.util.Log;

import com.night.chat.MainActivity;
import com.night.chat.model.Message;

import java.util.Date;

/**
 * Created by nightrain on 10/17/15.
 */
public class TestDataCreator {
    public static void run(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put("nickname", "James Bond");
        cv.put("_id", 1);
        db.insert("users", null, cv);

        cv.put("nickname", "John Smith");
        cv.put("_id", 2);
        db.insert("users", null, cv);

        cv.put("nickname", "Fox Mulder");
        cv.put("_id", 3);
        db.insert("users", null, cv);

        cv = new ContentValues();
        cv.put("_id", 1);
        cv.put("title", "John Smith");
        cv.put("owner", 1);
        db.insert("chats", null, cv);

        cv.put("_id", 2);
        cv.put("title", "групповой чат");
        cv.put("owner", 2);
        cv.put("is_group", 1);
        db.insert("chats", null, cv);
/*
        cv = new ContentValues();
        cv.put("chat_id", 1);
        cv.put("msg_text", "Привет");
        cv.put("creation_time", new Date().getTime());
        cv.put("state", Message.STATE_SENT);
        cv.put("creator", 2);
        db.insert("messages", null, cv);

        cv.put("chat_id", 1);
        cv.put("msg_text", "Хай");
        cv.put("creation_time", new Date().getTime() + 1);
        cv.put("state", Message.STATE_READ);
        cv.put("creator", 1);
        db.insert("messages", null, cv);

        cv.put("chat_id", 1);
        cv.put("msg_text", "Как дела ?");
        cv.put("creation_time", new Date().getTime() + 2);
        cv.put("state", Message.STATE_RECEIVED);
        cv.put("creator", 2);
        db.insert("messages", null, cv);
*/
        cv.put("chat_id", 2);
        cv.put("msg_text", "а тут никто не пишет");
        cv.put("creation_time", new Date().getTime());
        cv.put("state", Message.STATE_SENT);
        cv.put("creator", 2);
        db.insert("messages", null, cv);

        cv.put("chat_id", 2);
        cv.put("msg_text", "я пишу )");
        cv.put("creation_time", new Date().getTime());
        cv.put("state", Message.STATE_SENT);
        cv.put("creator", 3);
        db.insert("messages", null, cv);

        cv = new ContentValues();
        cv.put("chat_id", 1);
        cv.put("user_id", 1);
        db.insert("participants", null, cv);

        cv = new ContentValues();
        cv.put("chat_id", 1);
        cv.put("user_id", 2);
        db.insert("participants", null, cv);

        cv = new ContentValues();
        cv.put("chat_id", 2);
        cv.put("user_id", 1);
        db.insert("participants", null, cv);

        cv = new ContentValues();
        cv.put("chat_id", 2);
        cv.put("user_id", 2);
        db.insert("participants", null, cv);

        cv = new ContentValues();
        cv.put("chat_id", 2);
        cv.put("user_id", 3);
        db.insert("participants", null, cv);

        cv = new ContentValues();
        String android_id = Settings.Secure.getString(
                MainActivity.ctx.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.d("android_id", android_id);
        if (android_id.equals("ce99aa6ce41d6213")) {
            cv.put("my_user_id", 2);
        } else if (android_id.equals("c978d94b66d7171d")) {
            cv.put("my_user_id", 1);
        } else {
            cv.put("my_user_id", 3);
        }

        db.insert("preferences", null, cv);
    }
}
