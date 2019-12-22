// IBinderManager.aidl
package com.example.androidart.bindermanager;

interface IBinderManager {

    /**
     * @param binderCode, the unique token of specific Binder<br/>
     * @return specific Binder who's token is binderCode.
     */
    IBinder queryBinder(int binderCode);
}
