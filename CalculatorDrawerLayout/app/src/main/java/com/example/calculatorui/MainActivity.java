package com.example.calculatorui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    public Toolbar toolbar;             //顶部工具条
    public DrawerLayout mDrawerLayout;  //左侧抽屉
    private EditText editText;          //输入框：用于输入数字
    private String operator;            //操作符：记录 + - * / 符号
    private double n1 , n2 ,Result;     //操作数：操作符两端的数字，n1为左操作数，n2为右操作数。
    private TextView textView;          //文本框：显示计算过程和计算结果
    private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0;   //按钮：十个数字
    private Button btnPlus,btnMinus,btnMultiply,btnDivide;              //按钮：加减乘除
    private Button btnPoint,btnEqual,btnClear;                          //按钮：小数点，等号，清空
    private Button buttonPlusMinus;     //按钮：正负号
    private Button buttonPercent;       //按钮：百分号
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FindViewById();
        ButtonClickListener();
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
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
                Intent intent = new Intent( MainActivity.this,MyWebView.class);
                Uri uri;
                switch (item.getItemId()) {
                    case R.id.nav_call:
                        uri = Uri.parse("tel:03125073279");
                        intent = new Intent(Intent.ACTION_DIAL, uri);
                        startActivity(intent);
                        break;
                     case R.id.nav_index:
                         intent.putExtra("WebAddress","http://cs.hbu.cn/index.aspx");
                         startActivity(intent);
                         break;
                     case R.id.nav_introduce:
                         intent.putExtra("WebAddress","http://cs.hbu.cn/list.aspx?type=notice");
                         startActivity(intent);
                         break;
                     case R.id.nav_news:
                         intent.putExtra("WebAddress","http://cs.hbu.cn/list.aspx?type=news");
                         startActivity(intent);
                         break;
                     case R.id.nav_university:
                         uri = Uri.parse("http://www.hbu.cn");
                         intent = new Intent(Intent.ACTION_VIEW, uri);
                         startActivity(intent);
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
                final String[] items1={"Red","Blue","White"};
                AlertDialog dialog = new AlertDialog
                        .Builder(this)
                        .setTitle("选择背景色")
                        .setIcon(R.drawable.ic_settings)
                        .setSingleChoiceItems(items1, -1, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(MainActivity.this, items1[which], Toast.LENGTH_SHORT).show();
                                if(items1[which].equals("Red"))
                                {
                                    editText.setBackgroundColor(Color.rgb(123,0,0));
                                    textView.setBackgroundColor(Color.rgb(123,0,0));
                                }
                                if(items1[which].equals("Blue"))
                                {
                                    editText.setBackgroundColor(Color.rgb(0,0,123));
                                    textView.setBackgroundColor(Color.rgb(0,0,123));
                                }
                                if(items1[which].equals("White"))
                                {
                                    editText.setBackgroundColor(Color.rgb(255,255,255));
                                    textView.setBackgroundColor(Color.rgb(255,255,255));
                                }
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
                break;
            case  R.id.action_notifications:
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

    void FindViewById()//相同按钮，共用代码
    {
        editText = (EditText)findViewById(R.id.editviewdavid);//与XML中定义好的EditText控件绑定
        textView = (TextView)findViewById(R.id.textviewdavid);//与XML中定义好的TextView控件绑定
        editText.setCursorVisible(false);//隐藏输入框光标
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btn5 = (Button) findViewById(R.id.button5);
        btn6 = (Button) findViewById(R.id.button6);
        btn7 = (Button) findViewById(R.id.button7);
        btn8 = (Button) findViewById(R.id.button8);
        btn9 = (Button) findViewById(R.id.button9);
        btn0 = (Button) findViewById(R.id.button0);
        btnPlus = (Button) findViewById(R.id.buttonPlus);
        btnMinus = (Button) findViewById(R.id.buttonMinus);
        btnMultiply = (Button) findViewById(R.id.buttonMultiply);
        btnDivide = (Button) findViewById(R.id.buttonDivide);
        btnPoint = (Button) findViewById(R.id.buttonPoint);
        btnEqual = (Button) findViewById(R.id.buttonEqual);
        btnClear = (Button) findViewById(R.id.buttonClear);
        buttonPlusMinus = (Button)findViewById(R.id.PlusMinus);
        buttonPercent = (Button)findViewById(R.id.buttonPercent);
    }

    void ButtonClickListener(){
        btn1.setOnClickListener(lisenter);
        btn2.setOnClickListener(lisenter);
        btn3.setOnClickListener(lisenter);
        btn4.setOnClickListener(lisenter);
        btn5.setOnClickListener(lisenter);
        btn6.setOnClickListener(lisenter);
        btn7.setOnClickListener(lisenter);
        btn8.setOnClickListener(lisenter);
        btn9.setOnClickListener(lisenter);
        btn0.setOnClickListener(lisenter);
        btnPlus.setOnClickListener(lisenter);
        btnMinus.setOnClickListener(lisenter);
        btnMultiply.setOnClickListener(lisenter);
        btnDivide.setOnClickListener(lisenter);
        btnPoint.setOnClickListener(lisenter);
        btnEqual.setOnClickListener(lisenter);
        btnClear.setOnClickListener(lisenter);
        buttonPlusMinus.setOnClickListener(lisenter);
        buttonPercent.setOnClickListener(lisenter);
    }

    private View.OnClickListener lisenter = new View.OnClickListener() {//侦听器
        @Override
        public void onClick(View view) {//点击事件

            String str;
            Button button = (Button)view;   //把点击获得的id信息传递给button
            DecimalFormat MyFormat = new DecimalFormat("###.##");//控制Double转为String的格式

            try{
                switch(button.getId())//获取点击按钮的ID，通过ID选择对应的选项执行
                {
                    case R.id.button1://如果点击了按钮：“1”
                    {
                        editText.setText(editText.getText().toString() + 1);//输入框末尾，添加一个“1”
                        break;
                    }
                    case R.id.button2://2
                    {
                        editText.setText(editText.getText().toString() + 2);
                        break;
                    }
                    case R.id.button3://3
                    {
                        editText.setText(editText.getText().toString() + 3);
                        break;
                    }
                    case R.id.button4://4
                    {
                        editText.setText(editText.getText().toString() + + 4);
                        break;
                    }
                    case R.id.button5://5
                    {
                        editText.setText(editText.getText().toString() + 5);
                        break;
                    }
                    case R.id.button6://6
                    {
                        editText.setText(editText.getText().toString() +  6);
                        break;
                    }
                    case R.id.button7://7
                    {
                        editText.setText(editText.getText().toString() +  7);
                        break;
                    }
                    case R.id.button8://8
                    {
                        editText.setText(editText.getText().toString() +   8);
                        break;
                    }
                    case R.id.button9://9
                    {
                        editText.setText(editText.getText().toString() +  9);
                        break;
                    }
                    case R.id.button0://0
                    {
                        str = editText.getText().toString();
                        //此处可以考虑添加代码，限制用户输入00,000等 2017.6.15
                        editText.setText(str + 0);
                        break;
                    }
                    case R.id.buttonClear://Clear
                    {
                        editText.setText("");
                        textView.setText("");
                        Result = 0;
                        break;
                    }
                    case R.id.buttonPoint://.
                    {
                        str = editText.getText().toString();
                        if(str.indexOf(".") != -1) //判断字符串中是否已包含小数点，如果有，就什么也不做
                        {}
                        else //如果没有小数点
                        {
                            if(str.equals("0"))//如果开始为0
                                editText.setText(("0" + ".").toString());
                            else if(str.equals(""))//如果初时显示为空，就什么也不做
                            {}
                            else
                                editText.setText(str + ".");
                        }
                        break;
                    }
                    case R.id.buttonPlus://操作符+
                    {
                        str = editText.getText().toString();
                        n1 = Double.parseDouble(str);
                        operator = "+";
                        editText.setText("");
                        textView.setText(MyFormat.format(n1) + operator);
                        break;
                    }
                    case R.id.buttonMinus://操作符-
                    {
                        str = editText.getText().toString();
                        n1 = Double.parseDouble(str);
                        operator = "-";
                        editText.setText("");
                        textView.setText(MyFormat.format(n1) + operator);
                        break;
                    }
                    case R.id.buttonMultiply://操作符*
                    {
                        str = editText.getText().toString();
                        n1 = Double.parseDouble(str);
                        operator = "*";
                        editText.setText("");
                        textView.setText(MyFormat.format(n1) + operator);
                        break;
                    }

                    case R.id.buttonDivide://操作符 /
                    {
                        str = editText.getText().toString();
                        n1 = Double.parseDouble(str);
                        operator = "/";
                        editText.setText("");
                        textView.setText(MyFormat.format(n1) + operator);
                        break;
                    }

                    case R.id.PlusMinus://操作符 +-
                    {
                        str = editText.getText().toString();
                        n1 = Double.parseDouble(str);
                        n1 = 0 - n1;
//                        operator = "+-";
                        editText.setText(MyFormat.format(n1)+"");
//                        textView.setText(MyFormat.format(n1) + operator);
                        break;
                    }

                    case R.id.buttonPercent://操作符 %
                    {
                        str = editText.getText().toString();
                        n1 = Double.parseDouble(str);
                        n1 = n1 / 100;
//                        operator = "+-";
                        editText.setText(MyFormat.format(n1)+"");
//                        textView.setText(MyFormat.format(n1) + operator);
                        break;
                    }

                    case R.id.buttonEqual://操作符=
                    {
                        //Color Egg 2017.12.30 Edit by David
                        if(editText.getText().toString().equals("20171231"))
                        {
                            Log.d("David:",editText.getText().toString());
                            textView.setText("Happy New Year 2018");
//                            Toast toast=Toast.makeText(getApplicationContext(), "Happy New Year 2018 !", Toast.LENGTH_SHORT);
//                            toast.show();
                            break;
                        }

                        if(operator == "+")
                        {
                            str = editText.getText().toString();
                            n2 = Double.parseDouble(str);
                            Result = n1 + n2;
                            textView.setText(MyFormat.format(n1) + operator + MyFormat.format(n2) + "=" + MyFormat.format(Result) );
                            editText.setText(MyFormat.format(Result)+"");
                        }
                        else if(operator == "-")
                        {
                            str = editText.getText().toString();
                            n2 = Double.parseDouble(str);
                            Result = n1 - n2;
                            textView.setText(MyFormat.format(n1) + operator + MyFormat.format(n2) + "="+MyFormat.format(Result));
                            editText.setText(MyFormat.format(Result)+"");
                        }
                        else if(operator == "*")
                        {
                            str = editText.getText().toString();
                            n2 = Double.parseDouble(str);
                            Result = n1 * n2;
                            textView.setText(MyFormat.format(n1) + operator + MyFormat.format(n2) + "="+MyFormat.format(Result));
                            editText.setText(MyFormat.format(Result)+"");
                        }
                        else if(operator == "/")
                        {
                            str = editText.getText().toString();
                            n2 = Double.parseDouble(str);
                            if(n2 == 0)
                            {
                                editText.setText("");
                                textView.setText("除数不能为0");
                                break;
                            }
                            else
                            {
                                Result = n1 / n2;
                                textView.setText(MyFormat.format(n1) + operator + MyFormat.format(n2) + "="+MyFormat.format(Result));
                                editText.setText(MyFormat.format(Result)+"");
                            }
                        }
                        break;
                    }
                    default:
                        break;
                }
            }catch(Exception e){}
        }
    };
}
