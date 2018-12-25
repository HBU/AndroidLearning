package cn.hbu.cs.sqlservertest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity
{
    private View btnTest;
    private View btnClean;
    private TextView tvTestResult;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTest=findViewById(R.id.btnTestSql);
        btnClean=findViewById(R.id.btnClean);
        tvTestResult = (TextView)findViewById(R.id.tvTestResult);

        btnTest.setOnClickListener(getClickEvent());
        btnClean.setOnClickListener(getClickEvent());
    }

    private View.OnClickListener getClickEvent(){
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tvTestResult.setText("...");
                if(v==btnTest){
                    test();
                }
                if(v==btnClean){
                    tvTestResult.setText("代码未编写");
                }
            }
        };
    }
    private void test()
    {
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                String ret = DBUtil.QuerySQL();
                Message msg = new Message();
                msg.what=1001;
                Bundle data = new Bundle();
                data.putString("result", ret);
                msg.setData(data);
                mHandler.sendMessage(msg);
            }
        };
        new Thread(run).start();

    }

    Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what)
            {
                case 1001:
                    String str = msg.getData().getString("result");
                    tvTestResult.setText(str);
                    break;

                default:
                    //tvTestResult.setText("Error");
                    break;
            }
        };
    };

}