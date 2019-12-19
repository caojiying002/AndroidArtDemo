package com.example.androidart.messenger;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.example.androidart.TAGs.TAG_MESSENGER;
import static com.example.androidart.messenger.Shared.KEY_TEXT;
import static com.example.androidart.messenger.Shared.MSG_WHAT;

public class MessengerService extends Service {

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.d(TAG_MESSENGER, "handleMessage: msg = " + msg);
            switch (msg.what) {
                case MSG_WHAT: {
                    // msg.getData().setClassLoader(getClassLoader());
                    String textMessage = msg.getData().getString(KEY_TEXT);
                    Log.d(TAG_MESSENGER, "Received message: " + textMessage);

                    replyMessage(msg.replyTo, textMessage);
                    break;
                }
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Messenger messenger = new Messenger(mHandler);
        return messenger.getBinder();
    }

    void replyMessage(Messenger replyTo, String received) {
        if (replyTo == null)
            return;

        String replyText = "reply text \"" + received + "\"";

        Message msg = Message.obtain();
        msg.what = MSG_WHAT;
        msg.getData().putString(KEY_TEXT, replyText);
        //msg.replyTo = mMessenger;

        try {
            replyTo.send(msg);
        } catch (RemoteException e) {
            Log.w(TAG_MESSENGER, Log.getStackTraceString(e));
        }
    }

    @Override
    public void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
