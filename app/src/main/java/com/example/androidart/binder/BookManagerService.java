package com.example.androidart.binder;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.example.androidart.TAGs.TAG_BINDER;

/**
 * 运行于子进程, android:process=":remote"
 */
public class BookManagerService extends Service {

    private CopyOnWriteArrayList<Book> mBooks = new CopyOnWriteArrayList<>();

    private RemoteCallbackList<IOnNewBookArrivedCallback> mCallbackList = new RemoteCallbackList<>();

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IBookManager.Stub() {

            @Override
            public List<Book> getBookList() throws RemoteException {
                /*try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                Log.d(TAG_BINDER, "BookManagerService: getBookList(), threadName = " + Thread.currentThread().getName());
                return mBooks;
            }

            @Override
            public void addBook(Book book) throws RemoteException {
                Log.d(TAG_BINDER, "BookManagerService: addBook(), threadName = " + Thread.currentThread().getName());
                mBooks.add(book);
            }

            @Override
            public void registerCallback(IOnNewBookArrivedCallback callback) throws RemoteException {
                mCallbackList.register(callback);
            }

            @Override
            public void unregisterCallback(IOnNewBookArrivedCallback callback) throws RemoteException {
                mCallbackList.unregister(callback);
            }
        };
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler.postDelayed(new Runnable() {
            private int temp = 1000;

            @Override
            public void run() {
                temp++;
                Book b = new Book(temp, "Book " + temp);
                mBooks.add(b);

                Toast.makeText(BookManagerService.this, "Book " + temp + " added.", Toast.LENGTH_SHORT).show();
                notifyCallbacks(b);

                mHandler.postDelayed(this, 5000);

            }
        }, 5000);
    }

    @Override
    public void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        mCallbackList.kill();
        super.onDestroy();
    }

    private void notifyCallbacks(Book newBook) {
        int i = mCallbackList.beginBroadcast();
        while (i > 0) {
            i--;
            try {
                mCallbackList.getBroadcastItem(i).onNewBookArrived(newBook);
            } catch (RemoteException e) {
                // The RemoteCallbackList will take care of removing
                // the dead object for us.
            }
        }
        mCallbackList.finishBroadcast();
    }
}
