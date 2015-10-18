package com.night.chat;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.night.chat.storage.ChatListQuery;
import com.night.chat.storage.UserListQuery;

/**
 * Created by nightrain on 10/17/15.
 */
public class UsersAdapter extends CursorAdapter {

    public class Holder {
        public long id;
        public TextView title;
    }

    public UsersAdapter(Context context) {
        super(context, UserListQuery.getCursor(), 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View result = LayoutInflater.from(context).inflate(R.layout.contact_list_item, parent, false);
        Holder holder = new Holder();
        holder.title = (TextView) result.findViewById(R.id.title);
        result.setTag(holder);
        return result;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Holder holder = (Holder) view.getTag();
        UserListQuery.Row row = new UserListQuery.Row(cursor);
        holder.id =  row.userId;
        holder.title.setText(row.title);
    }
}
