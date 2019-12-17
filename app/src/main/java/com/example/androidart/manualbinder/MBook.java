package com.example.androidart.manualbinder;

import android.os.Parcel;
import android.os.Parcelable;

public class MBook implements Parcelable {
    public int bookId;
    public String bookName;

    public MBook(int bookId, String bookName) {
        this.bookId = bookId;
        this.bookName = bookName;
    }

    protected MBook(Parcel in) {
        bookId = in.readInt();
        bookName = in.readString();
    }

    public static final Creator<MBook> CREATOR = new Creator<MBook>() {
        @Override
        public MBook createFromParcel(Parcel in) {
            return new MBook(in);
        }

        @Override
        public MBook[] newArray(int size) {
            return new MBook[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(bookName);
    }

    @Override
    public String toString() {
        return "MBook{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                '}';
    }
}
