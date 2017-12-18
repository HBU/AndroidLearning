package com.example.calculatorui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

//Ref:http://blog.csdn.net/a553181867/article/details/51336899

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("Title");
        //toolbar.setSubtitle("SubTitle");
        toolbar.setLogo(R.mipmap.logo);
        //toolbar.setNavigationIcon(R.mipmap.ic_launcher);

        //设置导航图标、添加菜单点击事件要在setSupportActionBar方法之后
        setSupportActionBar(toolbar);


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
//                    case R.id.action_search:
//                        Toast.makeText(MainActivity.this, "Search !", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.action_notifications:
//                        Toast.makeText(MainActivity.this, "Notification !", Toast.LENGTH_SHORT).show();
//                        break;
                    case R.id.action_settings:
                        Toast.makeText(MainActivity.this, "Settings !", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }
    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //Toolbar 必须在onCreate()之后设置标题文本，否则默认标签将覆盖我们的设置
        if (toolbar != null) {//mActionBarToolbar就是android.support.v7.widget.Toolbar
            toolbar.setTitle("");//设置为空，可以自己定义一个居中的控件，当做标题控件使用
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
