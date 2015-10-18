package com.night.chat.network;

import android.content.Context;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.night.chat.storage.DBHelper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nightrain on 10/18/15.
 */
public class Notifier {
    private static final String TAG = "API";
    private static Firebase firebaseRef;
    public static Map<Long, ChatListerner> listeners = new HashMap<>();
    public static UserListener userListener;

    public static void init(Context context) {
        Firebase.setAndroidContext(context);
        firebaseRef = new Firebase("https://intense-inferno-8423.firebaseio.com/");
        userListener = new UserListener();
        firebaseRef.child("user" + DBHelper.getMyId()).addValueEventListener(userListener);
    }

    public static void subscribe(long chatId) {
        if (!listeners.containsKey(chatId)) {
            ChatListerner listener = new ChatListerner(chatId);
            firebaseRef.child("chat" + chatId).addValueEventListener(listener);
            listeners.put(chatId, listener);
        }

    }

    public static void unSubscribe(long chatId) {
        if (!listeners.containsKey(chatId)) return;
        firebaseRef.child("chat" + chatId).removeEventListener(listeners.get(chatId));
        listeners.remove(chatId);
    }

    public static void notify(long chatId) {
        firebaseRef.child("chat" + chatId).setValue(new Date().getTime());
    }
}
