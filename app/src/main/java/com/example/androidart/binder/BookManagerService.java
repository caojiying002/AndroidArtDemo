package com.example.androidart.binder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

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
                return mBooks;
            }

            @Override
            public void addBook(Book book) throws RemoteException {
                mBooks.add(book);
            }
        };
    }
}
