package com.android.leezp.test2.Entity;

import com.android.leezp.test2.external.MyPinYinSpell;

import java.io.Serializable;


/**
 * Created by Leezp on 2017/4/27 0027.
 */

public class Person implements Comparable<Person>, Serializable{
    //姓名
    private String name;
    //电话号
    private String telephone;
    //姓名对应的拼音
    private String pinyin;
    //姓名首字母
    private String firstLetter;
    //姓名第一个字
    private String firstWord;
    //判断当前是否是这个组的组头
    private boolean groupHead = false;
    //所有的联系人人数
    public static int personNum = 0;
    //当前正在移动的组
    public static String currentLetter = "";

    public Person(String name, String telephone) {
        this.name = name;
        this.telephone = telephone;

        //下面是将中文名字的的拼音以及第一个拼音的大写提取出来
        pinyin = MyPinYinSpell.getPinYin(name);
        firstLetter = pinyin.substring(0, 1).toUpperCase();
        if (!firstLetter.matches("[A-Z]")) {
            firstLetter = "#";
        }

        //将姓名的第一个字提取出来
        firstWord = name.substring(0,1);
    }

    public String getName() {
        return name;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public String getPinyin() {
        return pinyin;
    }

    public String getFirstWord() {
        return firstWord;
    }

    public boolean isGroupHead() {
        return groupHead;
    }

    public void setGroupHead(boolean groupHead) {
        this.groupHead = groupHead;
    }

    /**
     * TreeSet中进行排序
     * @param another     传入的Person进行比较
     * @return      先根据首字母判断，首字母为“#”都放在最后，都为“#”或者都是字母时才根据拼音来比较排序
     */
    @Override
    public int compareTo(Person another) {
        if (firstLetter.equals("#") && !another.getFirstLetter().equals("#")) {
            return 1;
        } else if (!firstLetter.equals("#") && another.getFirstLetter().equals("#")) {
            return -1;
        } else {
            return pinyin.compareToIgnoreCase(another.getPinyin());
        }
    }

}
