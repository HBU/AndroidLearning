package com.example.calculatorui;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

//Ref:http://blog.csdn.net/a553181867/article/details/51336899

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

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
                    case  R.id.action_search:
                        new AlertDialog.Builder(MainActivity.this)
                                .setIcon(R.mipmap.logo)
                                .setTitle("计算器")
                                .setMessage("上传到服务器?")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Toast.makeText(MainActivity.this,"已上传到服务器",Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Toast.makeText(MainActivity.this,"继续使用计算器",Toast.LENGTH_SHORT).show();
                                    }
                                }).create().show();
                        break;
                    case  R.id.action_notifications:
                        new AlertDialog.Builder(MainActivity.this)
                                .setIcon(R.mipmap.logo)
                                .setTitle("计算器")
                                .setMessage("配置计算器?")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Toast.makeText(MainActivity.this,"配置完毕",Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Toast.makeText(MainActivity.this,"继续使用计算器",Toast.LENGTH_SHORT).show();
                                    }
                                }).create().show();
                        break;
                    case  R.id.action_settings:
                        new AlertDialog.Builder(MainActivity.this)
                                .setIcon(R.mipmap.logo)
                                .setTitle("计算器")
                                .setMessage("确定要退出么?")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        finish();//Exit Activity
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Toast.makeText(MainActivity.this,"继续使用计算器",Toast.LENGTH_SHORT).show();
                                    }
                                }).create().show();
                        break;
                    case R.id.action_settings1:
                        new AlertDialog.Builder(MainActivity.this)
                                .setIcon(R.mipmap.logo)
                                .setTitle("计算器")
                                .setMessage("计算器 1.0 \n河北大学 \n网络空间安全与计算机学院 \nDavid \n2017.12.24")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                    }
                                })
                                .create().show();
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
