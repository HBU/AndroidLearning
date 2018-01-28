package com.android.leezp.test2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.leezp.test2.Adapter.ContactsListAdapter;
import com.android.leezp.test2.Entity.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener{

    //页面上的子控件
    private TextView personNum;
    private TextView groupId;
    private ListView contacts;

    //ListView上的子项的显示
    private List<Person> personList;
    private ContactsListAdapter adapter;

    //上一次groudId控件上的元素值
    private String lastGroudId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();

        initEvent();
    }

    /**
     * 初始化页面上的控件
     */
    private void initView() {
        personNum = (TextView) findViewById(R.id.activity_main_personNum);
        groupId = (TextView) findViewById(R.id.activity_main_groupId);
        contacts = (ListView) findViewById(R.id.activity_main_contacts);
    }

    /**
     * 将控件上的事件确定
     */
    private void initEvent() {
        //判断用户是否赋予了权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            setData();
        }
        adapter = new ContactsListAdapter(this, personList);
        contacts.setAdapter(adapter);
        setListViewonScroll();

        personNum.setText(""+Person.personNum);

        contacts.setOnItemClickListener(this);
    }

    /**
     * 设置数据源
     */
    private void setData() {
        personList = new ArrayList<>();

        Cursor cursor = null;
        try {
            //查询联系人的数据
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                int personNumber = 0;
                while (cursor.moveToNext()) {
                    //用户名
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    //电话号码
                    String telePhone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    //将读取的数据添加到联系人表中
                    Person person = new Person(name, telePhone);
                    personNumber++;
                    personList.add(person);
                }
                if (Person.personNum != personNumber) {
                    Person.personNum = personNumber;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        Collections.sort(personList);
    }

    /**
     * 给ListView设置滑动事件，让其顶上的字母显示正在滑动组的字母
     */
    private void setListViewonScroll() {
        contacts.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (personList.size() > 0) {
                    Person person = personList.get(firstVisibleItem);
                    if (!person.getFirstLetter().equals(lastGroudId)) {
                        groupId.setText(person.getFirstLetter());
                    }
                    lastGroudId = person.getFirstLetter();
                }
            }
        });
    }

    /**
     * ListView的点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ItemActivity.class);
        Person person = personList.get(position);
        intent.putExtra("person", person);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                    setData();
                } else {
                    Toast.makeText(this, "你没有允许应用权限！", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }
}
