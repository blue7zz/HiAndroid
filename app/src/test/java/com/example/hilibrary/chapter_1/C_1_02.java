package com.example.hilibrary.chapter_1;


import org.junit.Test;

public class C_1_02 {


    @Test
    public void testReplace(){
        String str ="hello word !!";
        System.out.println("替换之前 :{}"+str);
        str = str.replace('l','d');   //替换字符
        System.out.println("替换所有字符 :{}"+str);
        str = str.replaceAll("d","l");  //替换字符串
        System.out.println("替换全部 :{}"+str);
        str = str.replaceFirst("l",""); //替换第一个字符串
        System.out.println("替换第一个 l :{}"+str);

    }

    @Test
    public void longTest(){
        Long l = new Long(3);
        String s = "2";

    }



    @Test
    public void stringDemo(){
        String s ="hello";      //"hello"="hello" value={char[5]@1635}[h,e,l,l,o] hash 0
        s ="world";             //value={char[5]@1638}[h,e,l,l,o] hash 0
        //String 被 final 修饰，说明 String 类绝不可能被继承了，也就是说任何对 String 的操作方法，都不会被继承覆写；
        //String 中保存数据的是一个 char 的数组 value。我们发现 value 也是被 final 修饰的，也就是说 value 一旦被赋值，内存地址是绝对无法修改的，而且 value 的权限是 private 的，外部绝对访问不到，String 也没有开放出可以对 value 进行赋值的方法，所以说 value 一旦产生，内存地址就根本无法被修改。
        //final修饰，一旦创建内存地址无法更改，
        s.replace("l","oo"); //无法更改内部数据，重新返回一个新的字符串。


        //字符串乱码 编码问题 str.getBytes("ISO-8859-1")      new String(bytes,"ISO-8859-1");
        //UTF - 8


        //1.3 spring的  先不看。

        String s1 = "hello";
        String s2 = new String("hello");
        System.out.println(s1.equals(s2));  //判断是否相等
        System.out.println(s1 == s2); //判断内存地址是否相同



    }

}
