package com.example.androidart.binder;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.androidart.BaseAppCompatActivity;
import com.example.androidart.R;
import com.example.androidart.TAGs;

import java.util.List;

public class BookManagerActivity extends BaseAppCompatActivity {

    private IBookManager iBookManager;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAGs.TAG_BINDER, "onServiceConnected, threadName = " + Thread.currentThread().getName()
                    + ", IBinder service = " + service);
            iBookManager = IBookManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAGs.TAG_BINDER, "onServiceDisconnected, threadName = " + Thread.currentThread().getName());
            iBookManager = null;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);

        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);

        findViewById(R.id.button_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iBookManager != null) {
                    try {
                        // TODO 实际开发中可能阻塞主线程
                        List<Book> list = iBookManager.getBookList();
                        Log.d(TAGs.TAG_BINDER, "list = " + list);
                        Toast.makeText(BookManagerActivity.this, "size = " + list.size(), Toast.LENGTH_SHORT).show();
                    } catch (RemoteException e) {
                        Log.w(TAGs.TAG_BINDER, Log.getStackTraceString(e));
                    }
                }
            }
        });

        findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            private int temp = 0;

            @Override
            public void onClick(View v) {
                if (iBookManager != null) {
                    try {
                        temp++;
                        Book b = new Book(temp, "Local book " + temp);

                        // TODO 实际开发中可能阻塞主线程
                        iBookManager.addBook(b);
                        Toast.makeText(BookManagerActivity.this, "Local book " + temp + " added.", Toast.LENGTH_SHORT).show();
                    } catch (RemoteException e) {
                        Log.w(TAGs.TAG_BINDER, Log.getStackTraceString(e));
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        super.onDestroy();
    }
}
