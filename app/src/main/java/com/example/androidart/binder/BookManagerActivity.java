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

import androidx.annotation.BinderThread;
import androidx.annotation.MainThread;
import androidx.annotation.Nullable;

import com.example.androidart.BaseAppCompatActivity;
import com.example.androidart.R;

import java.util.List;

import static com.example.androidart.TAGs.TAG_BINDER;

public class BookManagerActivity extends BaseAppCompatActivity {

    @Nullable private IBookManager iBookManager;
    private IOnNewBookArrivedCallback iOnNewBookArrivedCallback = new IOnNewBookArrivedCallback.Stub() {

        @BinderThread
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            Log.d(TAG_BINDER, "onNewBookArrived: newBook = " + newBook +
                    ", threadName = " + Thread.currentThread().getName());
        }
    };

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @MainThread
        @Override
        public void onServiceConnected(ComponentName name, final IBinder service) {
            Log.d(TAG_BINDER, "onServiceConnected, threadName = " + Thread.currentThread().getName()
                    + ", IBinder service = " + service);
            //linkToDeath(service);
            iBookManager = IBookManager.Stub.asInterface(service);
            registerRemoteCallback();
        }

        @MainThread
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG_BINDER, "onServiceDisconnected, threadName = " + Thread.currentThread().getName());
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
                        Log.d(TAG_BINDER, "list = " + list);
                        Toast.makeText(BookManagerActivity.this, "size = " + list.size(), Toast.LENGTH_SHORT).show();
                    } catch (RemoteException e) {
                        Log.w(TAG_BINDER, Log.getStackTraceString(e));
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
                        Book b = new Book(temp, "Book " + temp);

                        // TODO 实际开发中可能阻塞主线程
                        iBookManager.addBook(b);
                        Toast.makeText(BookManagerActivity.this, "Book " + temp + " added.", Toast.LENGTH_SHORT).show();
                    } catch (RemoteException e) {
                        Log.w(TAG_BINDER, Log.getStackTraceString(e));
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        unregisterRemoteCallback();
        unbindService(mServiceConnection);
        super.onDestroy();
    }

    private void registerRemoteCallback() {
        if (iBookManager == null)
            return;
        try {
            iBookManager.registerCallback(iOnNewBookArrivedCallback);
        } catch (RemoteException e) {
            Log.w(TAG_BINDER, Log.getStackTraceString(e));
        }
    }

    private void unregisterRemoteCallback() {
        if (iBookManager == null)
            return;
        try {
            iBookManager.unregisterCallback(iOnNewBookArrivedCallback);
        } catch (RemoteException e) {
            Log.w(TAG_BINDER, Log.getStackTraceString(e));
        }
    }

    private void linkToDeath(final IBinder service) {
        try {
            service.linkToDeath(new IBinder.DeathRecipient() {

                @BinderThread
                @Override
                public void binderDied() {
                    Log.d(TAG_BINDER, "binderDied, threadName = " + Thread.currentThread().getName());
                    if (iBookManager == null)
                        return;
                    service.unlinkToDeath(this, 0);
                    iBookManager = null;

                }
            }, 0);
        } catch (RemoteException e) {
            Log.w(TAG_BINDER, Log.getStackTraceString(e));
        }
    }
}
