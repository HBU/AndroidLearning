package cn.hbu.cs.mycalculatorx;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by David on 2017/12/30.
 */

public class MyWebView extends AppCompatActivity {
    WebView mWebview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        Bundle bundle = getIntent().getExtras();
        String webAddress = bundle.getString("WebAddress");
        mWebview = (WebView) findViewById(R.id.webView1);
        final TextView textView = (TextView)findViewById(R.id.title);
        WebSettings webSettings = mWebview.getSettings();

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);//设置webview推荐使用的窗口
        webSettings.setLoadWithOverviewMode(true);//设置webview加载的页面的模式
        webSettings.setDisplayZoomControls(false);//隐藏webview缩放按钮
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        webSettings.setSupportZoom(true); // 支持缩放
        //设置WebChromeClient类
        mWebview.setWebChromeClient(new WebChromeClient() {//获取网站标题
            @Override
            public void onProgressChanged(WebView view, int newProgress) { //获取加载进度
                if (newProgress < 100) {
                    String progress = newProgress + "%";
//                    textView.setText("加载进度:"+progress);
                } else if (newProgress == 100) {
                    String progress = newProgress + "%";
//                    textView.setText("加载进度:"+progress);
                }
            }
        });

        mWebview.setWebViewClient(new WebViewClient() {//设置WebViewClient类
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {//设置加载前的函数
//                System.out.println("开始加载!!!");
//                beginLoading.setText("开始加载!!!");
            }
            @Override
            public void onPageFinished(WebView view, String url) {//设置结束加载函数
//                endLoading.setText("结束加载!!!");
            }
        });

        try{
            mWebview.loadUrl(webAddress);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
