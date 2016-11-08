package com.lighters.demos.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import com.lighters.demos.ITestAidlInterface;
import com.lighters.demos.R;
import com.lighters.demos.app.base.BaseActivity;
import java.util.List;

/**
 * Created by david on 16/11/8.
 * Email: huangdiv5@gmail.com
 * GitHub: https://github.com/alighters
 */

public class LocalClientActivity extends BaseActivity {

    private static final String ACTION_BIND_SERVICE = "com.lighters.demos.RemoteService";

    @BindView(R.id.et_message) EditText mEtMessage;
    @BindView(R.id.btn_send) Button mBtnSend;

    ITestAidlInterface mTestAidlInterface;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_aidl;
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTestAidlInterface != null) {
                    try {
                        Toast.makeText(LocalClientActivity.this, mTestAidlInterface.sayHello(mEtMessage.getText().toString()),
                            Toast.LENGTH_LONG).show();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        Intent intent = new Intent();
        intent.setAction(ACTION_BIND_SERVICE);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        bindService(createExplicitFromImplicitIntent(this, intent), mConnection, BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mTestAidlInterface = ITestAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mTestAidlInterface = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTestAidlInterface != null) {
            unbindService(mConnection);
        }
    }

    /***
     * Android L (lollipop, API 21) introduced a new problem when trying to invoke implicit intent,
     * "java.lang.IllegalArgumentException: Service Intent must be explicit"
     *
     * If you are using an implicit intent, and know only 1 target would answer this intent,
     * This method will help you turn the implicit intent into the explicit form.
     *
     * Inspired from SO answer: http://stackoverflow.com/a/26318757/1446466
     *
     * @param implicitIntent - The original implicit intent
     * @return Explicit Intent created from the implicit original intent
     */
    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }
}
