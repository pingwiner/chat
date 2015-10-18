package com.night.chat;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.night.chat.model.Chat;
import com.night.chat.storage.ChatListQuery;
import com.night.chat.storage.MessageListQuery;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nightrain on 10/12/15.
 */
public class ChatAdapter extends CursorAdapter {
    SimpleDateFormat sdf;

    class Holder {
        public long id;
        public TextView titleView;
        public TextView unread;
        public TextView lastTime;
        public TextView lastMsg;
    }

    public ChatAdapter(Context context) {
        super(context, ChatListQuery.getCursor(), 0);
        sdf = new SimpleDateFormat("HH:mm");
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View result = LayoutInflater.from(context).inflate(R.layout.chat_list_item, parent, false);
        Holder holder = new Holder();
        holder.titleView = (TextView) result.findViewById(R.id.title);
        holder.unread = (TextView) result.findViewById(R.id.unread);
        holder.lastTime = (TextView) result.findViewById(R.id.lastTime);
        holder.lastMsg = (TextView) result.findViewById(R.id.lastMsg);
        result.setTag(holder);
        return result;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Holder holder = (Holder) view.getTag();
        ChatListQuery.Row row = new ChatListQuery.Row(cursor);
        holder.titleView.setText(row.chatTitle);
        holder.lastMsg.setText(row.lastMsgText);
        holder.lastTime.setText(sdf.format(new Date(row.lastMsgTime)));
        holder.unread.setText(Long.toString(row.unreadMessagesCount));
        holder.unread.setVisibility((row.unreadMessagesCount > 0) ? View.VISIBLE : View.GONE);
        holder.id = row.chatId;
    }

    public void update() {
        changeCursor(ChatListQuery.getCursor());
        notifyDataSetChanged();
    }

}
