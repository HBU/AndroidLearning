package com.example.musicplayersimple;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyMusicService extends Service {
    public MyMusicService() {
    }
    private MediaPlayer mediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mediaPlayer = MediaPlayer.create( this, R.raw.despacio);//音乐文件资源路径
        this.mediaPlayer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mediaPlayer.stop();
    }
}
