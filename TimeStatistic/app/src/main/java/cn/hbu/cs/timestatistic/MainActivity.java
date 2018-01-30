package cn.hbu.cs.timestatistic;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            if (!isStatAccessPermissionSet(MainActivity.this)) {
//                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));   //查看是否为应用设置了权限
                Toast.makeText(getApplicationContext(), "Please Open: Get info about installed apps", Toast.LENGTH_SHORT).show();    //显示toast信息
            } else {
                Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    private boolean isStatAccessPermissionSet(Context c) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = c.getPackageManager();
        ApplicationInfo applicationInfo = packageManager.getApplicationInfo(c.getPackageName(),0);
        AppOpsManager appOpsManager = (AppOpsManager) c.getSystemService(Context.APP_OPS_SERVICE);
        appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,applicationInfo.uid,applicationInfo.packageName);
        return appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,applicationInfo.uid,applicationInfo.packageName)== AppOpsManager.MODE_ALLOWED;
    }
}
