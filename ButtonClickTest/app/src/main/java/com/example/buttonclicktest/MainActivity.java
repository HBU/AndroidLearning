package com.example.buttonclicktest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
//ref：https://www.cnblogs.com/releasing/p/5236806.html
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt1 = (Button)findViewById(R.id.button1);//对应方法二
        Button bt2 = (Button)findViewById(R.id.button2);//对应方法一

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"you have clicked Button1",Toast.LENGTH_SHORT).show();
            }
        });

        bt2.setOnClickListener(new MyListener());
    }

    class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this,"you have clicked Button2",Toast.LENGTH_SHORT).show();
        }
    }
    // 或者,这里是创建一个OnClickListener 的对象，与上面的直接复写接口有异曲同工之妙
//    private View.OnClickListener MyListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Toast.makeText(MainActivity.this,"you have clicked Button2",Toast.LENGTH_SHORT).show();
//        }
//    };

    // 方法三，注意需要public方法
    public void onButtonClick (View view){
        Toast.makeText(MainActivity.this,"you have clicked Button3",Toast.LENGTH_SHORT).show();
    }
}
