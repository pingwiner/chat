package com.night.chat.network;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.night.chat.BroadcastActions;
import com.night.chat.MainActivity;
import com.night.chat.model.Message;
import com.night.chat.network.request.MessagesRequest;
import com.night.chat.network.request.SendMessageRequest;
import com.night.chat.storage.DBHelper;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.okhttp.OkHttpClient;

import retrofit.client.OkClient;

/**
 * Created by nightrain on 10/18/15.
 */
public class NetworkManager {
    private static final String TAG = "NetworkManager";
    static NetworkManager instance;
    final SpiceManager spiceManager = new CustomSpiceManager(CustomSpiceService.class);
    private OkClient okClient = generateClient();

    public NetworkManager(Context context) {
        spiceManager.start(context.getApplicationContext());
    }

    public static NetworkManager getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkManager(context);
        }
        return instance;
    }

    private OkClient generateClient() {
        OkHttpClient client = new OkHttpClient();
        return new OkClient(client);
    }

    public OkClient getClient() {
        return okClient;
    }

    public void sendMessage(long chatId, final Message message) {
        SendMessageRequest request = new SendMessageRequest(chatId, message);
        spiceManager.execute(request, new RequestListener<Long>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                Log.e(TAG, "onRequestFailure");
            }

            @Override
            public void onRequestSuccess(Long messageId) {
                if (messageId == null) return;
                DBHelper.messageSent(message.chat_id, message.creation_time, messageId);
                Notifier.notify(message.chat_id);
            }
        });
    }

    public void getMessages(long chatId) {
        long lastMsgId = DBHelper.getLastMessageId(chatId);
        MessagesRequest request = new MessagesRequest(chatId, lastMsgId);
        spiceManager.execute(request, new RequestListener<Message.List>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                Log.e(TAG, "onRequestFailure");
            }

            @Override
            public void onRequestSuccess(Message.List messages) {
                if (messages == null) return;
                for (final Message message: messages) {
                    MainActivity.ctx.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DBHelper.saveIncomingMessage(message);
                        }
                    });

                }
                Intent intent = new Intent(BroadcastActions.ACTION_REFRESH_MESSAGES);
                MainActivity.ctx.sendBroadcast(intent);
            }
        });
    }

}
