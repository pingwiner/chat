package com.night.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;

import com.night.chat.model.Chat;
import com.night.chat.storage.DBHelper;

/**
 * Created by nightrain on 10/17/15.
 */
public class UsersFragment extends BaseListFragment {
    private static final String TAG = "UsersFragment";

    public static UsersFragment newInstance() {
        UsersFragment fragment = new UsersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public UsersFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
        }

        setListAdapter(new UsersAdapter(getActivity()));
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        long userId = ((UsersAdapter.Holder)v.getTag()).id;
        Log.d(TAG, "selected user: " + userId);

        if (null != mListener) {
            long chatId = DBHelper.getChatWithUser(userId);
            if (chatId > 0) {
                mListener.openChat(chatId);
            } else {
                chatId = Chat.createChatWith(userId);
                if (chatId > 0) {
                    mListener.openChat(chatId);
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.users, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}
