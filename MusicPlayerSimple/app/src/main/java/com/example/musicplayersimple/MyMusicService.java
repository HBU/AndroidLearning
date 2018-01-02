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

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand( intent,  flags,  startId);
        if (mediaPlayer!=null && mediaPlayer.isPlaying()){//如果正在播放
            mediaPlayer.pause();
        } else if (mediaPlayer!=null){//如果处于暂停状态
                mediaPlayer.start();
            }else{//如果尚未开始播放
                mediaPlayer = MediaPlayer.create(this, R.raw.despacio);//音乐文件资源路径
                mediaPlayer.start();
            }
        return START_NOT_STICKY;
    }

}
