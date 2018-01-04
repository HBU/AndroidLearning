package com.example.json;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public TextView responseText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        responseText = (TextView) findViewById(R.id.response_text);
        Button buildJson =(Button)findViewById(R.id.build_json);
        Button parseJson = (Button)findViewById(R.id.parse_json) ;
        Button sendRequest = (Button) findViewById(R.id.send_request);
        Button parseRequest = (Button)findViewById(R.id.parse_request);
        sendRequest.setOnClickListener(this);
        buildJson.setOnClickListener(this);
        parseJson.setOnClickListener(this);
        parseRequest.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_request) {
            sendRequest();
        }
        if (v.getId() == R.id.build_json) {
            buildJson();
        }
        if (v.getId() == R.id.parse_json) {
            parseJson();
        }
        if (v.getId() == R.id.parse_request) {
            parseRequest();
        }
    }

    private void buildJson(){//数组-对象 嵌套的复杂JSON
               showResponse("{\"student\":\n" +
                       "          [\n" +
                       "           {\"id\":1,\"name\":\"小明\",\"sex\":\"男\",\"age\":18,\"height\":175,\"date\":[2013,8,11]},\n" +
                       "           {\"id\":2,\"name\":\"小红\",\"sex\":\"女\",\"age\":19,\"height\":165,\"date\":[2013,8,23]},\n" +
                       "           {\"id\":3,\"name\":\"小强\",\"sex\":\"男\",\"age\":20,\"height\":185,\"date\":[2013,9,1]}\n" +
                       "          ],\n" +
                       "  \"grade\":\"2\"\n" +
                       "}");

    }

    private void parseJson(){
        try {
            JSONObject object = new JSONObject(responseText.getText().toString());
            Log.d("David", "parseJson: "+responseText.getText().toString());
            JSONArray array = object.getJSONArray("student");
            String parsedJson = "";
            List<HashMap<String,Object>> list = new ArrayList<HashMap<String ,Object>>();
            for(int i=0; i<array.length();i++){
                Log.d("David", "array.length(): "+array.length());
                JSONObject object2 = array.getJSONObject(i);
                String id = object2.getString("id");
                String name = object2.getString("name");
                Log.d("David", "id: "+id);
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("id",id);
                map.put("name",name);
                list.add(map);
            }
            String grade = object.getString("grade");
            showResponse(list.toString()+grade);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendRequest() {
        // 开启线程来发起网络请求（下章再讲这部分内容）
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    //获取到HttpConnection的实例，new出一个URL对象，并传入目标的网址，
                    // 然后调用一下openConnection（）方法
                    URL url = new URL("https://e0d980a0-9406-4d96-b8af-8a3375049db0.coding.io/conn.php");
                    connection = (HttpURLConnection) url.openConnection();
                    //得到了HttpConnection的实例后，设置请求所用的方法
                    // （GET：从服务器获取数据，POST：提交数据给服务器）
                    connection.setRequestMethod("GET");
                    //设置连接超时，读取的毫秒数
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    //利用getInputStream（）方法获取服务器的返回的输入流，然后读取
                    InputStream in = connection.getInputStream();
                    // 对获取到的输入流进行读取
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    showResponse(response.toString());//在模拟器显示返回值
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        //调用disconnect（）方法将HTTP连接关闭掉
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void parseRequest() {
        try {
            JSONArray jsonArray = new JSONArray(responseText.getText().toString());
            String result="";
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id =         jsonObject.getString("id");
                String name =       jsonObject.getString("psw");

                result += "\n【id】" + id +"\n【name】" + name+"\n" ;
                result += "\n====================";
                showResponse(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行UI操作，将结果显示到界面上
                responseText.setText(response);
            }
        });
    }


}