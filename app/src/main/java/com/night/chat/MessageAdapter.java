package com.night.chat;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.night.chat.storage.DBHelper;
import com.night.chat.storage.MessageListQuery;
import com.night.chat.storage.UserListQuery;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nightrain on 10/17/15.
 */
public class MessageAdapter extends CursorAdapter {
    SimpleDateFormat sdf;
    private long myId;
    private long lastViewedMsgTime;
    private long chatId;

    public class Holder {
        public long id;
        public TextView username;
        public TextView text;
        public TextView time;
        View baloon;
    }

    public MessageAdapter(Context context, long chatId) {
        super(context, MessageListQuery.getCursor(chatId), 0);
        this.chatId = chatId;
        sdf = new SimpleDateFormat("HH:mm");
        myId = DBHelper.getMyId();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View result = LayoutInflater.from(context).inflate(R.layout.message_list_item, parent, false);
        Holder holder = new Holder();
        holder.text = (TextView) result.findViewById(R.id.text);
        holder.username = (TextView) result.findViewById(R.id.username);
        holder.time = (TextView) result.findViewById(R.id.time);
        holder.baloon = result.findViewById(R.id.baloon);
        result.setTag(holder);
        return result;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Holder holder = (Holder) view.getTag();
        MessageListQuery.Row row = new MessageListQuery.Row(cursor);
        holder.id = row.id;
        holder.text.setText(row.text);
        holder.username.setText(row.nickname);
        holder.time.setText(sdf.format(new Date(row.time)));
        if (lastViewedMsgTime < row.time) {
            lastViewedMsgTime = row.time;
            DBHelper.setMessagesViewed(chatId, lastViewedMsgTime, myId);
        }
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)holder.baloon.getLayoutParams();
        if (row.userId == myId) {
            lp.setMargins(48, 16, 16, 0);
        } else {
            lp.setMargins(16, 16, 48, 0);
        }
        holder.baloon.setLayoutParams(lp);
    }


    public void update() {
        changeCursor(MessageListQuery.getCursor(chatId));
        notifyDataSetChanged();
    }

}
