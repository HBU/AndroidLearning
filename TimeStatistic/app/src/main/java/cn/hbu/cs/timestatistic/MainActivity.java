package cn.hbu.cs.timestatistic;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Ref:https://github.com/Uyouii/Statistics

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button button;
    private int style;
    private long totalTime;
    private int totalTimes;
    private RadioButton RbDay,RbWeek,RbMonth,RbYear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        textView =(TextView)findViewById(R.id.title);
        textView.setText("请设置权限！");
        button = (Button)findViewById(R.id.button);
        button.setText("设置");
        RbDay = (RadioButton)findViewById(R.id.RbDay);
        RbWeek = (RadioButton)findViewById(R.id.RbWeek);
        RbMonth = (RadioButton)findViewById(R.id.RbMonth);
        RbYear = (RadioButton)findViewById(R.id.RbYear);
        RbDay.setChecked(true);
        style = StatisticsInfo.DAY;//
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(!isStatAccessPermissionSet(MainActivity.this)) {
                        startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));   //查看是否为应用设置了权限
                    }
                    else {
//                        Toast.makeText(MainActivity.this,"Test",Toast.LENGTH_SHORT).show();
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        RbDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    style = StatisticsInfo.DAY;
                    onResume();
                }
            }
        });
        RbWeek.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    style = StatisticsInfo.WEEK;
                    onResume();
                }
            }
        });
        RbMonth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    style = StatisticsInfo.MONTH;
                    onResume();
                }
            }
        });
        RbYear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    style = StatisticsInfo.YEAR;
                    onResume();
                }
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        try {
            if((isStatAccessPermissionSet(this))){
                textView.setVisibility(View.INVISIBLE);
                button.setVisibility(View.INVISIBLE);
//                textView.setText("APP使用情况");
//                button.setText("更新");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        List<Map<String,Object>> datalist = null;
        StatisticsInfo statisticsInfo = new StatisticsInfo(this,this.style);// Get info
        totalTime = statisticsInfo.getTotalTime();
        totalTimes = statisticsInfo.getTotalTimes();

        datalist = getDataList(statisticsInfo.getShowList());

        ListView listView = (ListView)findViewById(R.id.AppStatisticsList);
        SimpleAdapter adapter = new SimpleAdapter(this,datalist,R.layout.inner_list,
                new String[]{"label","info","times","icon"},
                new int[]{R.id.label,R.id.info,R.id.times,R.id.icon});
        listView.setAdapter(adapter);

        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object o, String s) {
                if(view instanceof ImageView && o instanceof Drawable){
                    ImageView iv=(ImageView)view;
                    iv.setImageDrawable((Drawable)o);
                    return true;
                }
                else return false;
            }
        });
    }
    private List<Map<String,Object>> getDataList(ArrayList<AppInformation> ShowList) {
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("label","全部应用");
        map.put("info","运行时间: " + DateUtils.formatElapsedTime(totalTime / 1000));
        map.put("times","本次开机操作次数: " + totalTimes);
        map.put("icon",R.drawable.cat_normal);
        dataList.add(map);

        for(AppInformation appInformation : ShowList) {
            map = new HashMap<String,Object>();
            map.put("label",appInformation.getLabel());
            map.put("info","运行时间: " + DateUtils.formatElapsedTime(appInformation.getUsedTimebyDay() / 1000));
            map.put("times","本次开机操作次数: " + appInformation.getTimes());
            map.put("icon",appInformation.getIcon());
            dataList.add(map);
        }

        return dataList;
    }
    private boolean isStatAccessPermissionSet(Context c) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = c.getPackageManager();
        ApplicationInfo applicationInfo = packageManager.getApplicationInfo(c.getPackageName(),0);
        AppOpsManager appOpsManager = (AppOpsManager) c.getSystemService(Context.APP_OPS_SERVICE);
        appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,applicationInfo.uid,applicationInfo.packageName);
        return appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,applicationInfo.uid,applicationInfo.packageName)== AppOpsManager.MODE_ALLOWED;
    }
}
