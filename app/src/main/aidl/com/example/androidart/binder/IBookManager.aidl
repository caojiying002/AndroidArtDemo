// IBookManager.aidl
package com.example.androidart.binder;

// Declare any non-default types here with import statements
import com.example.androidart.binder.Book;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
