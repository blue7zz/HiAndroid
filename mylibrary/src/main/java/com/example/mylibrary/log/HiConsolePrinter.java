package com.example.mylibrary.log;

import android.util.Log;

import androidx.annotation.NonNull;

import static com.example.mylibrary.log.HiLogConfig.MAX_LEN;

public class HiConsolePrinter implements HiLogPrinter {

    @Override
    public void print(@NonNull HiLogConfig config, int level, String tag, @NonNull String printString) {
        int len = printString.length();
        int countOfSub = len / MAX_LEN;
        if (countOfSub > 0) {
            int index = 0;
            for (int i = 0; i < countOfSub; i++) {
                Log.println(level,tag,printString.substring(index, index + MAX_LEN));
                index += MAX_LEN;
            }
            if (index != len) {
                Log.println(level,tag,printString.substring(index,len));
            }
        } else {
            Log.println(level, tag, printString);
        }

    }
}
