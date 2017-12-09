package com.example.lbstest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {
    public BDLocationListener BaiDuListener = new MylocationListener();
    public LocationClient mLocationClient = null;
    private TextView positionText;
    private RadioButton RadioButtonGPS;
    private RadioButton RadioButtonNet;

    private Button btnRefresh;
    private MapView mapView;
    private BaiduMap baiduMap;
    private boolean isFirstLocate = true;
    private LocationClientOption option = new LocationClientOption();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());//声明LocationClient类
        mLocationClient.registerLocationListener(BaiDuListener);//注册监听函数
        SDKInitializer.initialize(getApplicationContext());//SDKInitializer调用initialize方法，传入全局的上下文进行初始化操作，地图就会显示出来
        setContentView(R.layout.activity_main);

        mapView = (MapView)findViewById(R.id.bmapView);
        positionText  =(TextView)findViewById(R.id.position_text_view);
        RadioButtonGPS = (RadioButton)findViewById(R.id.radioGPS);
        RadioButtonNet = (RadioButton)findViewById(R.id.radioNET);

        btnRefresh = (Button)findViewById(R.id.btn_refresh);
        RadioButtonNet.setClickable(false);//Forbidden RadioButton can be clicked
        RadioButtonGPS.setClickable(false);//Forbidden RadioButton can be clicked

        baiduMap = mapView.getMap();//获取地图的总控制器 BaiDuMap 类的实例
        // 有了BiaDuMap类的实例，就可以对地图进行各种各样的操作，比如设置地图的缩放级别以及将地图移动到某一个经纬度上。
        baiduMap.setMyLocationEnabled(true);//取消百度地图让”我”显示在地图上的限制
        btnRefresh.setOnClickListener(lisenter);
        List<String> permissionList = new ArrayList<>();// Permission array list , request permissions in one array.
        if(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED){
            permissionList.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(android.Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this,permissions,1);//request all permissions
        }else {
            requestLocation();// Request Location information
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);//程序退出的时候，关闭我的位置显示功能
    }

    protected void onRefresh(){
        finish();
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void initLocation() {
        option.setCoorType("bd09ll");        //可选，默认gcj02，设置返回的定位结果坐标系
        //option.setScanSpan(5000);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
//        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        option.setOpenGps(true);        //可选，默认false,设置是否使用gps
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        mLocationClient.setLocOption(option);
    }
    private void  requestLocation(){
        initLocation();
        Log.d("map requestLocation:","Run requestLocation ...");
        mLocationClient.start();
    }
    private void navigateTo(BDLocation location) {
        if (isFirstLocate) {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);//借助LatLng类实现地图移动到某一个经纬度上
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);//百度地图可以将缩放级别的取值范围定在3到19之间，值越大，地图越精细。
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();//借助MyLocationData.Builder类封装经纬度信息
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationdata = locationBuilder.build();
        baiduMap.setMyLocationData(locationdata);//借助MyLocationData.Builder类的build方法，让设备的位置显示在地图上
    }
    @Override
    public  void  onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length >0 ){
                    for (int result : grantResults){
                        if(result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"all  permission  !!!",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else {
                    Toast.makeText(this,"Unkonw error.",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    public class MylocationListener implements BDLocationListener{
        @Override
        public  void onReceiveLocation(BDLocation location){
            final StringBuilder currentPosition = new StringBuilder();
            Log.d("map Listener",location.getLocType()+"");
            if(location.getLocType() == BDLocation.TypeGpsLocation){   // GPS定位结果
                Log.d("map test","GPS");
                RadioButtonGPS.setChecked(true);
                RadioButtonNet.setChecked(false);
                currentPosition.append("[卫星数量]");
                currentPosition.append(location.getSatelliteNumber());    //获取卫星数
                currentPosition.append("\n[速度]");
                currentPosition.append(location.getSpeed());        // 单位：公里每小时
                currentPosition.append("\n[海拔]");
                currentPosition.append(location.getAltitude());    //获取海拔高度信息，单位米
                currentPosition.append("\n[方向]");
                currentPosition.append(location.getDirection());    //获取方向信息，单位度
                currentPosition.append("\n[地址]");
                currentPosition.append(location.getAddrStr());    //获取地址信息
                currentPosition.append("\nGPS定位成功");
                currentPosition.append("\n");
                navigateTo(location);
            }else if (location.getLocType() == BDLocation.TypeNetWorkLocation){  // Net定位结果
                Log.d("map test","net");
                RadioButtonNet.setChecked(true);
                RadioButtonGPS.setChecked(false);
                currentPosition.append("[地址]");
                currentPosition.append(location.getAddrStr());    //获取地址信息
                currentPosition.append("\n[运营商]");
                currentPosition.append(location.getOperators());    //获取运营商信息
                currentPosition.append("\n网络定位成功！");
                currentPosition.append("\n");
                navigateTo(location);
            }else if (location.getLocType() == BDLocation.TypeOffLineLocation) {                // 离线定位结果
                Log.d("map test","Offline");
                currentPosition.append("\ndescribe : ");
                currentPosition.append("离线定位成功，离线定位结果也是有效的");
                navigateTo(location);
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                currentPosition.append("\ndescribe : ");
                currentPosition.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                currentPosition.append("\ndescribe : ");
                currentPosition.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                currentPosition.append("\ndescribe : ");
                currentPosition.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }else {
                currentPosition.append("定位失败。错误码：");
                currentPosition.append(location.getLocType()).append("\n");
            }
            currentPosition.append("[经度]").append(location.getLongitude()+"").append("\n");
            currentPosition.append("[维度]").append(location.getLatitude()+"").append("\n");
            currentPosition.append("[国家]").append(location.getCountry()).append("\n");
            currentPosition.append("[省份]").append(location.getProvince()).append("\n");
            currentPosition.append("[城市]").append(location.getCity()).append("\n");
            currentPosition.append("[区县]").append(location.getDistrict()).append("\n");
            currentPosition.append("[街道]").append(location.getStreet());

            runOnUiThread(new Runnable() {
//                利用Activity.runOnUiThread(Runnable)把更新ui的代码创建在Runnable中，
//                然后在需要更新ui时，把这个Runnable对象传给Activity.runOnUiThread(Runnable)。
//                Runnable对像就能在ui程序中被调用。如果当前线程是UI线程，那么行动是立即执行。
//                如果当前线程不是UI线程,操作是发布到事件队列的UI线程。
                @Override
                public void run() {
                    positionText.setText(currentPosition);
                }
            });
        }
        @Override
        public void onConnectHotSpotMessage(String s, int i) {        }
    }

    private View.OnClickListener lisenter = new View.OnClickListener() {//侦听器
        @Override
        public void onClick(View view) {
            Button button = (Button) view;//把点击获得的参数传递给button
            try{
                switch (button.getId()) {//根据按钮id，判断点击了那个按钮，进一步执行相关代码
                    case R.id.btn_refresh:{                        //
                        Toast toast = Toast.makeText(MainActivity.this,"刷新定位数据", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP , 0, 250);
                        toast.show();
                        onRefresh();
                        break;
                    }
                    default:
                        break;
                }
            }
            catch(Exception e)
            {
            }
        }
    };
}
