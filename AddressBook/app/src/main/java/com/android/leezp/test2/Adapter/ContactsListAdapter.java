package com.android.leezp.test2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.android.leezp.test2.Entity.Person;
import com.android.leezp.test2.R;

import java.util.List;

/**
 * Created by Leezp on 2017/4/27 0027.
 */

public class ContactsListAdapter extends BaseAdapter{

    private Context context;
    //用户数据
    private List<Person> personList;

    public ContactsListAdapter(Context context, List<Person> personList) {
        this.context = context;
        //将组头确定下来
        String head="";
        for (Person person:personList) {
            //不等于就说明它是组头
            if (!head.equals(person.getFirstLetter())) {
                head = person.getFirstLetter();
                person.setGroupHead(true);
            }
        }
        this.personList = personList;
    }

    @Override
    public int getCount() {
        return personList==null?0:personList.size();
    }

    @Override
    public Object getItem(int position) {
        return personList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_main_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.groupNum = (TextView) convertView.findViewById(R.id.activity_main_list_item_groupNum);
            viewHolder.firstName = (TextView) convertView.findViewById(R.id.activity_main_list_item_firstName);
            viewHolder.personName = (TextView) convertView.findViewById(R.id.activity_main_list_item_personName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Person person = personList.get(position);
        if (person.isGroupHead()) {
            Person.currentLetter = person.getFirstLetter();
            if (viewHolder.groupNum.getVisibility() == View.GONE) {
                viewHolder.groupNum.setVisibility(View.VISIBLE);
            }
            viewHolder.groupNum.setText(person.getFirstLetter());
            viewHolder.firstName.setText(person.getFirstWord());
            viewHolder.personName.setText(person.getName());
        } else {
            if (viewHolder.groupNum.getVisibility() == View.VISIBLE) {
                viewHolder.groupNum.setVisibility(View.GONE);
            }
            viewHolder.firstName.setText(person.getFirstWord());
            viewHolder.personName.setText(person.getName());
        }
        return convertView;
    }

    private class ViewHolder {
        public TextView groupNum;
        public TextView firstName;
        public TextView personName;
    }

}
