package com.example.rotatetest;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_port);
    }
    //布局为不按照layout-land和layout-port目录，而自定义名字时, xml文件放到layout文件夹也可以
    @Override

    public void onConfigurationChanged (Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        int mCurrentOrientation = getResources().getConfiguration().orientation;
        if ( mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT ) {
            // If current screen is portrait
            setContentView(R.layout.activity_main_port);
        } else if ( mCurrentOrientation == Configuration.ORIENTATION_LANDSCAPE ) {
            //If current screen is landscape
            setContentView(R.layout.activity_main_landscape);
        }

    }

}
