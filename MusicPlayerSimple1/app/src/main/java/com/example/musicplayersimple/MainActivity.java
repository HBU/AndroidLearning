package com.example.musicplayersimple;

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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int Flag = 1;
    Button playButton;
    Button stopButton;
    TextView textView;
    IntentFilter intentFilter;
    MyReceiver myReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.PlugSet);
        playButton = ( Button )findViewById( R.id.play );
        stopButton = ( Button )findViewById( R.id.stop );
        playButton.setOnClickListener( clickListener );
        stopButton.setOnClickListener( clickListener );

        intentFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        myReceiver = new MyReceiver();
        registerReceiver(myReceiver, intentFilter);//注册监听“耳机拔插”系统广播
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intentBroadcat) {
            if (intentBroadcat.hasExtra("state")){
                if (intentBroadcat.getIntExtra("state", 0) == 0){
                    textView.setText("耳机未连接");
                    textView.setTextColor(Color.parseColor("#FF0000"));
                }
                else if (intentBroadcat.getIntExtra("state", 0) == 1){
                    textView.setText("耳机已连接");
                    textView.setTextColor(Color.parseColor("#B3993A"));
                }
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

     private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this,MyMusicService.class );//连接服务
            switch ( v.getId() ) {
                case R.id.play:
                    startService(intent);//开启服务
                    if (Flag == 1) {
                        playButton.setText("暂停");
                        Flag = 0;
                    }else {
                        playButton.setText("继续播放");
                        Flag = 1;
                    }
                    break;
                case R.id.stop:
                    stopService(intent);//关闭服务
                    playButton.setText("播放");
                    Flag = 1;
                    break;
                default:
                    break;
            }
        }
    };
}
