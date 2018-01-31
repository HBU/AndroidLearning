package cn.hbu.cs.timestatistic;

import android.annotation.TargetApi;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.format.DateUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class StatisticsInfo {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("M-d-yyyy HH:mm:ss");
    final public static int DAY = 0;
    final public static int WEEK = 1;
    final public static int MONTH = 2;
    final public static int YEAR = 3;

    private ArrayList<AppInformation> ShowList;
    private ArrayList<AppInformation> AppInfoList;
    private List<UsageStats> result;
    private long totalTime;
    private int totalTimes;
    private int style;

    public StatisticsInfo(Context context, int style) {
        try {
            this.style = style;
            setUsageStatsList(context);
            setShowList();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    //将次数和时间为0的应用信息过滤掉
    private void setShowList() {
        this.ShowList = new ArrayList<>();
        totalTime = 0;
        Log.d("David AppInfoList.size:",AppInfoList.size() + "");
        for(int i=0;i<AppInfoList.size();i++) {

            if(AppInfoList.get(i).getUsedTimebyDay() >= 0 ) { //&& AppInfoList.get(i).getTimes() > 0) {
                this.ShowList.add(AppInfoList.get(i));
                totalTime += AppInfoList.get(i).getUsedTimebyDay();
                totalTimes += AppInfoList.get(i).getTimes();
                Log.d("David getUsedTimebyDay",AppInfoList.get(i).getUsedTimebyDay() + "");
            }
        }
        Log.d("David totalTime:",totalTime + "");
        Log.d("David totalTimes:",totalTimes + "");
        //将显示列表中的应用按显示顺序排序
        for(int i = 0;i<this.ShowList.size() - 1;i++) {
            for(int j = 0; j< this.ShowList.size() - i - 1; j++) {
                if(this.ShowList.get(j).getUsedTimebyDay() < this.ShowList.get(j+1).getUsedTimebyDay()) {
                    AppInformation temp = this.ShowList.get(j);
                    this.ShowList.set(j,this.ShowList.get(j+1));
                    this.ShowList.set(j+1,temp);
                }
            }
        }
    }


    //统计当天的应用使用时间
    private void setUsageStatsList(Context context) throws NoSuchFieldException {
        Log.d("David","setUsageStatsList");
        Calendar calendar = Calendar.getInstance();
        long now = calendar.getTimeInMillis();
        setResultList(context);// Get Stats service infomation
        List<UsageStats> Mergeresult = MergeList(this.result);
        for(UsageStats usageStats:Mergeresult) {
            this.AppInfoList.add(new AppInformation(usageStats , context));
        }
        for(AppInformation appInformation : this.AppInfoList) {
            if(appInformation.getUsedTimebyDay() > 0) {
                Log.d("David packagename:",appInformation.getPackageName() + "");
                Log.d("David label:",appInformation.getLabel() + "");
                Log.d("David time:",DateUtils.formatElapsedTime(appInformation.getUsedTimebyDay() / 1000 ) + "");
                Log.d("David Times:",appInformation.getTimes() + "");
                Log.d("David firstTimeStmp:",DateUtils.formatElapsedTime((now - appInformation.getUsageStats().getFirstTimeStamp()) / 1000) + "");
                Log.d("David lastTimeStmp:",DateUtils.formatElapsedTime((now- appInformation.getUsageStats().getLastTimeStamp()) / 1000) + "");
                Log.d("David lastTimeUsed:",DateUtils.formatElapsedTime((now - appInformation.getUsageStats().getLastTimeUsed() )/ 1000) + "");
                //String info = appInformation.getLabel() +  " " + DateUtils.formatElapsedTime(appInformation.getUsedTimebyDay() / 1000 );
            }
        }
    }

    private void setResultList(Context context) {// Core code
        UsageStatsManager m = (UsageStatsManager)context.getSystemService(Context.USAGE_STATS_SERVICE);
        this.AppInfoList = new ArrayList<>();// Useful ?
        if(m != null) {
            Calendar calendar = Calendar.getInstance();
            long now = calendar.getTimeInMillis();
            long begintime = getBeginTime();

            Log.d("David style:",style + "");
            if(style == DAY){
                int interval = UsageStatsManager.INTERVAL_YEARLY;
                Calendar calendar1 = Calendar.getInstance();
                long endTime = calendar1.getTimeInMillis();
                calendar1.add(Calendar.YEAR, -1);
                long startTime = calendar1.getTimeInMillis();
                Log.d("David Begin Time:",dateFormat.format(startTime)  + "");
                Log.d("David Now   Time:",dateFormat.format(endTime) + "");
                this.result = m.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);
                Log.d("David Result:", "DAY");
            }
            else if(style == WEEK){
                this.result = m.queryUsageStats(UsageStatsManager.INTERVAL_WEEKLY,begintime, now);
                Log.d("David :", "WEEK");
            }
            else if(style == MONTH){
                this.result = m.queryUsageStats(UsageStatsManager.INTERVAL_MONTHLY, begintime, now);
                Log.d("David :", "Month");
            }
            else if(style == YEAR){
                this.result = m.queryUsageStats(UsageStatsManager.INTERVAL_YEARLY, begintime, now);
                Log.d("David :", "Year");
            }
            else {
                this.result = m.queryUsageStats(UsageStatsManager.INTERVAL_BEST, begintime, now);
                Log.d("David :", "DAY 2");
            }
            Log.d("David result:",result + "");
        }
    }

    private long getBeginTime() {
        Calendar calendar = Calendar.getInstance();
        long begintime;
        if(style == WEEK) {
            //int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            calendar.add(Calendar.DATE,-7);
            begintime = calendar.getTimeInMillis();
        }
        else if(style == MONTH) {
            //int mounthDay = calendar.get(Calendar.DAY_OF_MONTH);
            calendar.add(Calendar.DATE,-30);
            begintime = calendar.getTimeInMillis();
        }
        else if(style == YEAR) {
            calendar.add(Calendar.YEAR,-1);
            begintime = calendar.getTimeInMillis();
        }
        else{
            //剩下的输入均显示当天的数据
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);

            calendar.add(Calendar.SECOND, -1 * second);
            calendar.add(Calendar.MINUTE, -1 * minute);
            calendar.add(Calendar.HOUR, -1 * hour);

            begintime = calendar.getTimeInMillis();
        }
        return begintime;
    }

    private List<UsageStats> MergeList(List<UsageStats> result) {
        List<UsageStats> Mergeresult = new ArrayList<>();
        for(int i=0;i<result.size();i++) {
            long begintime;
            begintime = getBeginTime();
            if(result.get(i).getFirstTimeStamp() > begintime) {
                int num = FoundUsageStats(Mergeresult, result.get(i));
                if (num >= 0) {
                    UsageStats u = Mergeresult.get(num);
                    u.add(result.get(i));
                    Mergeresult.set(num, u);
                } else Mergeresult.add(result.get(i));
            }
        }
        return Mergeresult;
    }

    private int FoundUsageStats(List<UsageStats> Mergeresult, UsageStats usageStats) {
        for(int i=0;i<Mergeresult.size();i++) {
            if(Mergeresult.get(i).getPackageName().equals(usageStats.getPackageName())) {
                return i;
            }
        }
        return -1;
    }


    public long getTotalTime() {
        return totalTime;
    }

    public int getTotalTimes() {
        return totalTimes;
    }

    public ArrayList<AppInformation> getShowList() {
        return ShowList;
    }
}

