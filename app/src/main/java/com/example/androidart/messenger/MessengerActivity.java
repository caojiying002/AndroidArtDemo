package com.example.androidart.messenger;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.BinderThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidart.BaseAppCompatActivity;
import com.example.androidart.R;

import static com.example.androidart.TAGs.TAG_MESSENGER;
import static com.example.androidart.messenger.Shared.KEY_TEXT;
import static com.example.androidart.messenger.Shared.MSG_WHAT;

public class MessengerActivity extends BaseAppCompatActivity {

    @Nullable
    private Messenger sendMessenger;

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

                    Toast.makeText(MessengerActivity.this, "Received message: " + textMessage, Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    };
    private Messenger receiveMessenger = new Messenger(mHandler);

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @BinderThread
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG_MESSENGER, "onServiceConnected, name = " + name + ", service = " + service);
            sendMessenger = new Messenger(service);
        }

        @BinderThread
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.w(TAG_MESSENGER, "onServiceDisconnected, name = " + name);
            sendMessenger = null;
        }
    };

    private EditText editText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textMessage = editText.getText().toString().trim();
                send(textMessage);
            }
        });

        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    void send(String textMessage) {
        if (sendMessenger == null)
            return;
        if (TextUtils.isEmpty(textMessage))
            return;

        Message msg = Message.obtain();
        msg.what = MSG_WHAT;
        msg.getData().putString(KEY_TEXT, textMessage);
        msg.replyTo = receiveMessenger;

        try {
            sendMessenger.send(msg);
        } catch (RemoteException e) {
            Log.w(TAG_MESSENGER, Log.getStackTraceString(e));
        }
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        unbindService(mServiceConnection);
        super.onDestroy();
    }
}
