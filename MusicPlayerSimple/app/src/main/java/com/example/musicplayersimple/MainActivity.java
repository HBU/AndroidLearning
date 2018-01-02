package com.example.musicplayersimple;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    int Flag = 1;
    Button playButton;
    Button stopButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playButton = ( Button )findViewById( R.id.play );
        stopButton = ( Button )findViewById( R.id.stop );
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
