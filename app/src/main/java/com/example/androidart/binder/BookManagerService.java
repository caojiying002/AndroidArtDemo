package com.example.androidart.binder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.androidart.TAGs;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 运行于子进程, android:process=":remote"
 */
public class BookManagerService extends Service {

    CopyOnWriteArrayList<Book> mBooks = new CopyOnWriteArrayList<>();

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
                Log.d(TAGs.TAG_BINDER, "BookManagerService: getBookList(), threadName = " + Thread.currentThread().getName());
                return mBooks;
            }

            @Override
            public void addBook(Book book) throws RemoteException {
                Log.d(TAGs.TAG_BINDER, "BookManagerService: addBook(), threadName = " + Thread.currentThread().getName());
                mBooks.add(book);
            }
        };
    }
}
