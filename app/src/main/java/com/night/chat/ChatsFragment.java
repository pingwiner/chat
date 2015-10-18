package com.night.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;


public class ChatsFragment extends BaseListFragment {
    private static final String TAG = "ChatsFragment";
    private ChatAdapter adapter;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) return;
            switch(action) {
                case BroadcastActions.ACTION_REFRESH_MESSAGES:
                    adapter.update();
                    break;
            }
        }
    };

    public static ChatsFragment newInstance() {
        ChatsFragment fragment = new ChatsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ChatsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
        }
        adapter = new ChatAdapter(getActivity());
        setListAdapter(adapter);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        long chatId = ((ChatAdapter.Holder)v.getTag()).id;
        if (null != mListener) {
            mListener.openChat(chatId);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.chats, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void onStart() {
        super.onStart();
        //int lastViewedMsgIndex = DBHelper.getLastUnreadMessage(chatId);
        getListView().setSelection(adapter.getCount() - 1);
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastActions.ACTION_REFRESH_MESSAGES);
        getActivity().registerReceiver(receiver, filter);
    }

    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(receiver);
    }
}
