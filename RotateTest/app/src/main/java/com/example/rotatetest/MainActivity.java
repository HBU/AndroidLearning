package com.example.rotatetest;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_port);
        Button bt1 = (Button)findViewById(R.id.button1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"you have clicked Button1",Toast.LENGTH_SHORT).show();
            }
        });
    }
    //布局为不按照layout-land和layout-port目录，而自定义名字时, xml文件放到layout文件夹也可以
    @Override
    public void onConfigurationChanged (Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        int mCurrentOrientation = getResources().getConfiguration().orientation;
        if ( mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT ) {
            // If current screen is portrait
            setContentView(R.layout.activity_main_port);
            this.onDestroy();
            this.onCreate(new Bundle() );

        } else if ( mCurrentOrientation == Configuration.ORIENTATION_LANDSCAPE ) {
            //If current screen is landscape
            setContentView(R.layout.activity_main_landscape);
            this.onDestroy();
            this.onCreate(new Bundle() );
        }
    }


}
