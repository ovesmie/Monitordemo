package com.ovesmie.monitordemo;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


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

        //mPackageManager = this.getPackageManager();
        list.add("com.ovesmie.monitordemo");
        list.add("com.android.systemui");
        list.add("com.huawei.android.launcher");
        list.add("com.android.settings");
        list.add("android");
        list.add("com.baidu.input");
        list.add("com.miui.home");
    }

    public void goAccess(View view) {
        BaseAccessibilityService.getInstance().goAccess();
    }

    public void cleanProcess(String data) {
        Intent intent = new Intent(MainActivity.this, LockActivity.class);
        if (!list.contains(data)) {
            try {
                ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                assert activityManager != null;
                activityManager.killBackgroundProcesses(data);
            } catch (NullPointerException e) {
                Log.v("debug", e.toString());
            }
            startActivity(intent);
        }
    }

}