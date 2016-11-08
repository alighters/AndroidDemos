package com.lighters.demos.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import com.lighters.demos.ITestAidlInterface;

/**
 * Created by david on 16/11/8.
 * Email: huangdiv5@gmail.com
 * GitHub: https://github.com/alighters
 */

public class RemoteService extends Service {

    String mGreeter = "David.wei";

    IBinder mBinder = new ITestAidlInterface.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString)
            throws RemoteException {

        }

        @Override
        public String sayHello(String msg) throws RemoteException {
            return "Say Hello:" + msg + " from: " + mGreeter;
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            return super.onTransact(code, data, reply, flags);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
