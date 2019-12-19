// IOnNewBookArrivedListener.aidl
package com.example.androidart.binder;

// Declare any non-default types here with import statements
import com.example.androidart.binder.Book;

interface IOnNewBookArrivedCallback {
    void onNewBookArrived(in Book newBook);
}
