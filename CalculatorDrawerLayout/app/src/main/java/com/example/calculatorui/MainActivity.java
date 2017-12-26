package com.example.calculatorui;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public Toolbar toolbar;
    public DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,R.string.app_name, R.string.app_name){
//            public void onDrawerClosed(View view) {//抽屉关闭后
//                //getActionBar().setTitle(mDrawerTitle);
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//
//            public void onDrawerOpened(View drawerView) {//抽屉打开后
//                //getActionBar().setTitle(mTitle);
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//
//            @Override
//            public boolean onOptionsItemSelected(MenuItem item) {
//                if (item != null && item.getItemId() == android.R.id.home) {//actionbar上的home icon
//                    //END即gravity.right 从右向左显示   START即left  从左向右弹出显示
//                    if (mDrawerLayout.isDrawerVisible(GravityCompat.END)) {
//                        mDrawerLayout.closeDrawer(GravityCompat.END);//关闭抽屉
//                    } else {
//                        mDrawerLayout.openDrawer(GravityCompat.END);//打开抽屉
//                    }
//                    return true;
//                }
//                return false;
//            }
//        };
////        mDrawerLayout.setDrawerListener(mDrawerToggle);//设置抽屉监听
//        mDrawerLayout.addDrawerListener(mDrawerToggle);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        mDrawerToggle.syncState();

        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
//        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {//抽屉菜单监听 2017.12.27
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                 switch (item.getItemId()) {
                    case R.id.nav_call:
                        Toast.makeText(MainActivity.this,"Call me",Toast.LENGTH_SHORT).show();
                        break;
                     case R.id.nav_friends:
                         Toast.makeText(MainActivity.this,"nav_friends",Toast.LENGTH_SHORT).show();
                         break;
                     case R.id.nav_location:
                         Toast.makeText(MainActivity.this,"nav_location",Toast.LENGTH_SHORT).show();
                         break;
                     case R.id.nav_mail:
                         Toast.makeText(MainActivity.this,"nav_mail",Toast.LENGTH_SHORT).show();
                         break;
                     case R.id.nav_task:
                         Toast.makeText(MainActivity.this,"nav_task",Toast.LENGTH_SHORT).show();
                         break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
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

            default:
        }
        return true;
    }

}
