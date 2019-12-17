package com.example.androidart.manualbinder;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public interface MBookManager extends IInterface {

    List<MBook> getBookList() throws RemoteException;

    void addBook(MBook book) throws RemoteException;

    /** Local-side IPC implementation stub class. */
    public static abstract class Stub extends Binder implements MBookManager {

        private static String DESCRIPTOR = "com.example.androidart.manualbinder.MBookManager";

        static final int TRANSACTION_getBookList = FIRST_CALL_TRANSACTION;
        static final int TRANSACTION_addBook = FIRST_CALL_TRANSACTION + 1;

        /** Construct the stub at attach it to the interface. */
        public Stub() {
            super.attachInterface(this, DESCRIPTOR);
        }

        /**
         * Cast an IBinder object into an {@link com.example.androidart.manualbinder.MBookManager} interface,
         * generating a proxy if needed.
         */
        public static MBookManager asInterface(IBinder obj) {
            if (obj == null)
                return null;
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin instanceof MBookManager) {
                return (MBookManager) iin;
            }
            return new Proxy(obj);
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
            String descriptor = DESCRIPTOR;
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(descriptor);
                    return true;
                }
                case TRANSACTION_getBookList: {
                    data.enforceInterface(descriptor);
                    List<MBook> _result = this.getBookList();
                    reply.writeNoException();
                    reply.writeTypedList(_result);
                    return true;
                }
                case TRANSACTION_addBook: {
                    data.enforceInterface(descriptor);
                    MBook _arg0 = data.readInt() != 0 ? MBook.CREATOR.createFromParcel(data) : null;
                    this.addBook(_arg0);
                    reply.writeNoException();
                    return true;
                }
                default: {
                    return super.onTransact(code, data, reply, flags);
                }
            }
        }

        public static class Proxy implements MBookManager {

            private IBinder mRemote;

            public Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override
            public IBinder asBinder() {
                return mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override
            public List<MBook> getBookList() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                List<MBook> _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(TRANSACTION_getBookList, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.createTypedArrayList(MBook.CREATOR);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override
            public void addBook(MBook book) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    if (book != null) {
                        _data.writeInt(1);
                        book.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    mRemote.transact(TRANSACTION_addBook, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

    }
}
