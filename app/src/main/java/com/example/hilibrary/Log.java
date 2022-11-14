package com.example.hilibrary;

public class Log {

    public static void info(String ...x){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<x.length;i++){
            sb.append(x[i]);
        }
        System.out.println(sb.toString());
    }
}
