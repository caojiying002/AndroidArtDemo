package com.example.androidart.bindermanager;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.androidart.BaseAppCompatActivity;
import com.example.androidart.R;

import static com.example.androidart.TAGs.TAG_BINDER_MANAGER;
import static com.example.androidart.bindermanager.BinderManager.BINDER_COMPUTE;
import static com.example.androidart.bindermanager.BinderManager.BINDER_SECURITY_CENTER;

public class BinderManagerActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_manager);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IBinder securityBinder = BinderManager.getInstance(getContext()).queryBinder(BINDER_SECURITY_CENTER);
                ISecurityCenter securityCenter = SecurityCenterImpl.asInterface(securityBinder);
                Log.d(TAG_BINDER_MANAGER, "visit ISecurityCenter");
                String msg = "helloworld-安卓";
                System.out.println("content:" + msg);
                try {
                    String password = securityCenter.encrypt(msg);
                    System.out.println("encrypt:" + password);
                    System.out.println("decrypt:" + securityCenter.decrypt(password));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG_BINDER_MANAGER, "visit ICompute");
                IBinder computeBinder = BinderManager.getInstance(getContext()).queryBinder(BINDER_COMPUTE);
                ICompute mCompute = ComputeImpl.asInterface(computeBinder);
                try {
                    System.out.println("3+5=" + mCompute.add(3, 5));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        // TODO 待优化：这里调用单例是为了初始化，将BinderManagerService绑定到applicationContext
        BinderManager.getInstance(this);
    }
}
