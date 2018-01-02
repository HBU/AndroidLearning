package com.example.broadcastofsystem;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
//新技能：2018.1.2
//  logt 自动生成TAG
//  logd 相当于log.d
//  logm 直接输出上面要打的log信息
//  ctrl + shift + enter 自动补全语句
//  按住alt，用鼠标竖着选择，看看奇迹 ：）
//  写完 Toast，选择Create new toast，后面的不用写了，填参数就ok。
public class MainActivity extends AppCompatActivity {
    IntentFilter intentFilter;
    MyReceiver myReceiver;
    TextView textView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.MODIFY_AUDIO_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.MODIFY_AUDIO_SETTINGS},1);
            }else {
                Toast.makeText(this, "权限已申请", Toast.LENGTH_SHORT).show();
            }
        }
        textView = (TextView)findViewById(R.id.show) ;
        intentFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        myReceiver = new MyReceiver();
        registerReceiver(myReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    public class MyReceiver extends BroadcastReceiver {
        private static final String TAG = "MyReceiver";
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO: This method is called when the BroadcastReceiver is receiving
            // an Intent broadcast.
//            if (Intent.ACTION_HEADSET_PLUG.equals(intent.getAction())) {
//                Toast.makeText(context, "headset connected", Toast.LENGTH_LONG).show();
//            }
        if (intent.hasExtra("state")){
            if (intent.getIntExtra("state", 0) == 0){
                textView.setText("耳机未连接");
                textView.setTextColor(Color.parseColor("#FF0000"));
//                Toast.makeText(context, "耳机未连接", Toast.LENGTH_LONG).show();
            }
            else if (intent.getIntExtra("state", 0) == 1){
//                Toast.makeText(context, "耳机已连接", Toast.LENGTH_LONG).show();
                textView.setText("耳机已连接");
                textView.setTextColor(Color.parseColor("#B3EE3A"));
            }
        }

        }
    }
}
