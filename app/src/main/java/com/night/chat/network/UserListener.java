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
public class UserListener implements ValueEventListener {
    private static final String TAG = "UserListener";

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Log.d(TAG, "something happened !");

    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {
        Log.d(TAG, "Error !");
    }
}
