// IBookManager.aidl
package com.example.androidart.binder;

// Declare any non-default types here with import statements
import com.example.androidart.binder.Book;
import com.example.androidart.binder.IOnNewBookArrivedCallback;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerCallback(IOnNewBookArrivedCallback callback);
    void unregisterCallback(IOnNewBookArrivedCallback callback);
}
