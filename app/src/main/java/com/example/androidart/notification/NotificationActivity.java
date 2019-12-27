package com.example.androidart.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.androidart.BaseAppCompatActivity;
import com.example.androidart.R;
import com.example.androidart.singletask.STMainActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;
import static android.os.Build.VERSION_CODES.JELLY_BEAN;
import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;
import static android.os.Build.VERSION_CODES.O;

public class NotificationActivity extends BaseAppCompatActivity {

    @RequiresApi(O)
    private static final String NOTIFICATION_CHANNEL_ID = "this is my channel id";

    private static int sId = 0;
    private NotificationManager mNM;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mNM = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "this is my notification channel name", IMPORTANCE_DEFAULT);
            mNM.createNotificationChannel(channel);
        }

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sId ++;
                Notification notification = new Notification();
                notification.icon = R.drawable.ic_launcher; // TODO drawable vs mipmap
                notification.tickerText = "hello world";    // As of the L release, this text is no longer shown on screen
                notification.when = System.currentTimeMillis(); // Android N及以上不显示，除非设置Notification.Builder#setShowWhen(true)
                notification.flags = Notification.FLAG_AUTO_CANCEL;
                PendingIntent pendingIntent = getPendingIntent(getContext());
                //notification.setLatestEventInfo(getContext(), "chapter_5", "this is notification.", pendingIntent);
                try {
                    Method method = Notification.class.getMethod("setLatestEventInfo", Context.class, CharSequence.class, CharSequence.class, PendingIntent.class);
                    method.invoke(notification, getContext(), "chapter_5", "this is notification.", pendingIntent);

                    mNM.notify(sId, notification);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });


        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sId ++;
                Notification.Builder builder = Build.VERSION.SDK_INT < O ?
                        new Notification.Builder(getContext()) : new Notification.Builder(getContext(), NOTIFICATION_CHANNEL_ID);
                if (Build.VERSION.SDK_INT >= JELLY_BEAN_MR1)
                    builder.setShowWhen(true);
                builder.setSmallIcon(R.drawable.ic_launcher)
                        .setTicker("hello world")
                        .setContentTitle("chapter_5")
                        .setContentText("this is notification.")
                        .setWhen(System.currentTimeMillis())
                        .setAutoCancel(true)
                        .setContentIntent(getPendingIntent(getContext()));
                Notification notification = Build.VERSION.SDK_INT >= JELLY_BEAN ? builder.build() : builder.getNotification();
                mNM.notify(sId, notification);
            }
        });
    }

    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, STMainActivity.class);
        return PendingIntent.getActivity(context,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
