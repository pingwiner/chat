package com.night.chat;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.night.chat.network.NetworkManager;
import com.night.chat.network.Notifier;
import com.night.chat.storage.DBHelper;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener, View.OnClickListener {
    private static final String TAG = "MainActivity";
    public static Activity ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_chats).setOnClickListener(this);
        findViewById(R.id.btn_contatcs).setOnClickListener(this);
        DBHelper.init(this);
        openChatList();
        Notifier.init(this);
        Notifier.subscribe(1);
        NetworkManager.getInstance(this).getMessages(1);
    }


    @Override
    public void openChatList() {
        ChatsFragment fragment = ChatsFragment.newInstance();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, "ChatList")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openUserList() {
        UsersFragment fragment = UsersFragment.newInstance();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, "UserList")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openChat(long chatId) {
        ChatFragment fragment = ChatFragment.newInstance(chatId);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, "MessageList")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected " + item.getItemId());
        switch (item.getItemId()) {
            case R.id.contacts:
                openUserList();
                return true;
            case R.id.chats:
                openChatList();
                break;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_chats:
                openChatList();
                break;
            case R.id.btn_contatcs:
                openUserList();
                break;
        }
    }
}
