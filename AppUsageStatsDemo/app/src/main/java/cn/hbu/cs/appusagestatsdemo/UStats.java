package cn.hbu.cs.appusagestatsdemo;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by User on 3/2/15.
 * Edit by David on 1/31/18
 */
public class UStats {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("M-d-yyyy HH:mm:ss");
    public static final String TAG = UStats.class.getSimpleName();
//    @SuppressWarnings("ResourceType")
//    public static void getStats(Context context){
//        UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
//        int interval = UsageStatsManager.INTERVAL_YEARLY;
//        Calendar calendar = Calendar.getInstance();
//        long endTime = calendar.getTimeInMillis();
//        calendar.add(Calendar.YEAR, -1);
//        long startTime = calendar.getTimeInMillis();
//
//        Log.d(TAG, "Range start:" + dateFormat.format(startTime) );
//        Log.d(TAG, "Range end:" + dateFormat.format(endTime));
//
//        UsageEvents uEvents = usm.queryEvents(startTime,endTime);
//        while (uEvents.hasNextEvent()){
//            UsageEvents.Event e = new UsageEvents.Event();
//            uEvents.getNextEvent(e);
//
//            if (e != null){
//                Log.d(TAG, "Event: " + e.getPackageName() + "\t" +  e.getTimeStamp());
//            }
//        }
//    }

    public static List<UsageStats> getUsageStatsList(Context context){
        UsageStatsManager usm = getUsageStatsManager(context);
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        //calendar.add(Calendar.YEAR, -1);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        calendar.add(Calendar.SECOND, -1 * second);
        calendar.add(Calendar.MINUTE, -1 * minute);
        calendar.add(Calendar.HOUR, -1 * hour);

        long startTime = calendar.getTimeInMillis();
        Log.d(TAG,"###########");
        Log.d(TAG, "Time Start:" + dateFormat.format(startTime) );
        Log.d(TAG, "Time End:" + dateFormat.format(endTime));
        Log.d(TAG,"###########");
        List<UsageStats> usageStatsList = usm.queryUsageStats(UsageStatsManager.INTERVAL_BEST,startTime,endTime);
        return usageStatsList;
    }

    public static void printUsageStats(List<UsageStats> usageStatsList){

        for (UsageStats u : usageStatsList){

            Log.d(TAG,
                    "PackageName:" + u.getPackageName() +  "\n"
                            + "ForegroundTime:"  + dateFormat.format(u.getTotalTimeInForeground())+  "\n"
                            + "getLastTimeUsed:"+dateFormat.format(u.getLastTimeUsed())+  "\n"
                            + "getFirstTimeStamp:" + dateFormat.format(u.getFirstTimeStamp())+  "\n"
                            + "getLastTimeStamp:"+ dateFormat.format(u.getLastTimeStamp())
            ) ;
            Log.d(TAG,"###########");
        }

    }

    public static void printCurrentUsageStatus(Context context){
        printUsageStats(getUsageStatsList(context));
    }
    @SuppressWarnings("ResourceType")
    private static UsageStatsManager getUsageStatsManager(Context context){
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
        return usm;
    }
}
