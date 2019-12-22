package com.example.androidart.bindermanager;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import static com.example.androidart.bindermanager.BinderManager.BINDER_COMPUTE;
import static com.example.androidart.bindermanager.BinderManager.BINDER_SECURITY_CENTER;

public class BinderManagerService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new BinderManagerImpl();
    }


    static class BinderManagerImpl extends IBinderManager.Stub {
        private Binder compute;
        private Binder securityCenter;

        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            switch (binderCode) {
                case BINDER_SECURITY_CENTER: {
                    return getSecurityCenter();
                }
                case BINDER_COMPUTE: {
                    return getCompute();
                }
                default: {
                    return null;
                }
            }
        }

        private Binder getCompute() {
            if (compute == null)
                compute = new ComputeImpl();
            return compute;
        }

        private Binder getSecurityCenter() {
            if (securityCenter == null)
                securityCenter = new SecurityCenterImpl();
            return securityCenter;
        }
    }
}
