package com.example.musicplayersimple;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();// 设置按钮和监听
    }

    private void initView(){
        Button playButton = ( Button )super.findViewById( R.id.play );
        Button stopButton = ( Button )super.findViewById( R.id.stop );
        playButton.setOnClickListener( clickListener );
        stopButton.setOnClickListener( clickListener );
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this,MyMusicService.class );//连接服务
            switch ( v.getId() ) {
                case R.id.play:
                    startService(intent);//开启服务
                    break;
                case R.id.stop:
                    stopService(intent);//关闭服务
                    break;
                default:
                    break;
            }
        }
    };
}
