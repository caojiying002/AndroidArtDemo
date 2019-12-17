package com.example.androidart.manualbinder;

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
public class MBookManagerService extends Service {

    CopyOnWriteArrayList<MBook> mBooks = new CopyOnWriteArrayList<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MBookManager.Stub() {

            @Override
            public List<MBook> getBookList() throws RemoteException {
                /*try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                return mBooks;
            }

            @Override
            public void addBook(MBook book) throws RemoteException {
                mBooks.add(book);
            }
        };
    }
}
