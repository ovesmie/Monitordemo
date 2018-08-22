package com.ovesmie.monitordemo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class MainActivity extends AppCompatActivity {

    //private PackageManager mPackageManager;
    //private String[] mPackages;

    private List<String> list = new ArrayList<String>();

    public static MainActivity mactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BaseAccessibilityService.getInstance().init(this);

        mactivity = this;

        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final PackageManager packageManager = getPackageManager();
        List<ResolveInfo> apps = packageManager.queryIntentActivities(mainIntent, 0);
        for (int i = 0; i < apps.size(); i++) {
            ResolveInfo info = apps.get(i);
            if(info.activityInfo.applicationInfo.packageName.contains("android") ||
                    info.activityInfo.applicationInfo.packageName.contains("huawei")||
                    info.activityInfo.applicationInfo.packageName.contains("home")||
                    info.activityInfo.applicationInfo.packageName.contains("input")||
                    info.activityInfo.applicationInfo.packageName.contains("system")){
                list.add(info.activityInfo.applicationInfo.packageName);
            }
            /*Log.i("TAG", info.activityInfo.loadLabel(packageManager) + " pkgName "
                    + info.activityInfo.applicationInfo.packageName + " className " + info.activityInfo.name);*/
        }
        list.add("com.ovesmie.monitordemo");
        list.add("com.android.systemui");
        list.add("com.miui.home");
        list.remove("com.android.settings");
    }

    public void goAccess(View view) {
        BaseAccessibilityService.getInstance().goAccess();
    }

    public void cleanProcess(String data) {
        Intent intent = new Intent(MainActivity.this, LockActivity.class);
        if (!list.contains(data)) {
            try {
                Log.d("debug",data+" killed");
                ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                assert activityManager != null;
                activityManager.killBackgroundProcesses(data);
            } catch (NullPointerException e) {
                Log.e("debug", e.toString());
            }
            startActivity(intent);
        }
    }
}