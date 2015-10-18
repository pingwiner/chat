package com.night.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;

import com.night.chat.model.Message;
import com.night.chat.network.Notifier;
import com.night.chat.storage.DBHelper;
import com.night.chat.network.NetworkManager;

/**
 * Created by nightrain on 10/17/15.
 */
public class ChatFragment extends BaseListFragment {
    public static final String ARG_CHAT_ID = "ARG_CHAT_ID";
    private long chatId;
    private MessageAdapter adapter;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) return;
            switch(action) {
                case BroadcastActions.ACTION_REFRESH_MESSAGES:
                    adapter.update();
                    getListView().smoothScrollToPosition(adapter.getCount());
                    break;
            }
        }
    };

    public static ChatFragment newInstance(long chatId) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_CHAT_ID, chatId);
        fragment.setArguments(args);
        return fragment;
    }

    public ChatFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            chatId = getArguments().getLong(ARG_CHAT_ID);
        }
        adapter = new MessageAdapter(getActivity(), chatId);
        setListAdapter(adapter);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.chat, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setDivider(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_layout, null);
        final EditText msgInput = (EditText) view.findViewById(R.id.msg_input);
        view.findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = msgInput.getText().toString();
                if (TextUtils.isEmpty(msg)) return;
                msgInput.getText().clear();
                long creationTime = DBHelper.sendMessage(chatId, msg);
                adapter.update();
                getListView().smoothScrollToPosition(adapter.getCount());
                Message message = new Message();
                message.chat_id = chatId;
                message.creation_time = creationTime;
                message.creator = DBHelper.getMyId();
                message.msg_text = msg;
                message.state = Message.STATE_OUTGOING;
                NetworkManager.getInstance(getActivity()).sendMessage(chatId, message);
            }
        });
        return view;
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
