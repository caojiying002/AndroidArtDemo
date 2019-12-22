package com.example.androidart.bindermanager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

public class BinderManager {

    public static final int BINDER_COMPUTE = 0;
    public static final int BINDER_SECURITY_CENTER = 1;

    private static volatile BinderManager sInstance;
    public static BinderManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (BinderManager.class) {
                if (sInstance == null) {
                    sInstance = new BinderManager(context);
                }
            }
        }
        return sInstance;
    }


    @Nullable
    private IBinderManager iBinderManager;
    private Context mContext;


    private BinderManager(Context context) {
        mContext = context.getApplicationContext();
        connectBinderPoolService();
    }

    private synchronized void connectBinderPoolService() {
        Intent intent = new Intent(mContext, BinderManagerService.class);
        mContext.bindService(intent, new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        iBinderManager = IBinderManager.Stub.asInterface(service);
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {

                    }
                },
                Context.BIND_AUTO_CREATE);
    }

    /**
     * query binder by binderCode from binder manager
     *
     * @param binderCode
     *            the unique token of binder
     * @return binder who's token is binderCode<br>
     *         return null when not found or BinderManagerService died.
     */
    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;
        try {
            if (iBinderManager != null) {
                binder = iBinderManager.queryBinder(binderCode);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return binder;
    }
}
