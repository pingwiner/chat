package com.night.chat.network;

import android.content.Intent;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.night.chat.BroadcastActions;
import com.night.chat.MainActivity;

/**
 * Created by nightrain on 10/18/15.
 */
public class ChatListerner implements ValueEventListener {
    private static final String TAG = "ChatListerner";
    private long chatId;

    public ChatListerner(long chatId) {
        this.chatId = chatId;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Log.d(TAG, "chat " + chatId + " has changed");
        NetworkManager.getInstance(MainActivity.ctx).getMessages(chatId);
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {
        Log.d(TAG, "error !");
    }
};
