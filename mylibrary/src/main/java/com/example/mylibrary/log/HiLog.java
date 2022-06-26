package com.example.mylibrary.log;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * 需求
 * 1：能够打印堆栈信息
 * 2：支持任何数据类型的打印
 * 3：支持实现日子可视化
 * 4：能够实现文件打印模块
 * 5：支持不同打印器的插拔
 * <p>
 * -------------------------------------------
 * 日志收集  》》 日志加工 》》 日志打印
 */
public class HiLog {

    private static final String HI_LOG_PACKAGE;


    static {
        String className = HiLog.class.getName();
        HI_LOG_PACKAGE = className.substring(0, className.lastIndexOf('.') + 1);
        //className : com.example.mylibrary.log
        v("tag", className);
        //HI_LOG_PACKAGE:com.example.mylibrary.
        v("tag", HI_LOG_PACKAGE);

    }

    public static void v(String tag, Object... contents) {
        log(HiLogType.V, tag, contents);
    }

    public static void d(String tag, Object... contents) {
        log(HiLogType.D, tag, contents);
    }

    public static void i(String tag, Object... contents) {
        log(HiLogType.I, tag, contents);
    }

    public static void w(String tag, Object... contents) {
        log(HiLogType.W, tag, contents);

    }

    public static void e(String tag, Object... contents) {
        log(HiLogType.E, tag, contents);

    }

    public static void a(String tag, Object... contents) {
        log(HiLogType.A, tag, contents);
    }

    public static void log(@HiLogType.TYPE int type, Object... contents) {
        log(type, HILogManager.getInstance().getConfig().getGlobalTag(), contents);
    }

    public static void log(@HiLogType.TYPE int type, String tag, Object... contents) {
        log(HILogManager.getInstance().getConfig(), type, tag, contents);
    }

    /**
     * 打印日志
     *
     * @param config
     * @param type
     * @param tag
     * @param contents
     */
    public static void log(@NonNull HiLogConfig config, @HiLogType.TYPE int type, String tag, Object... contents) {
        if (!config.enable()) {
            return; //开关
        }
        StringBuilder sb = new StringBuilder();
        //打印线程信息
        if (!config.includeThread()) {
            sb.append(HiLogConfig.HI_THREAD_FORMATTER.format(Thread.currentThread())).append("\n");
        }
        //对堆栈信息进行处理
        if (config.stackTraceDepth() > 0) {
            sb.append(HiLogConfig.HI_STACK_TRACE_FORMATTER.format(HiStackTraceUtil.getCroppedRealStackTrack(new Throwable().getStackTrace(), HI_LOG_PACKAGE, config.stackTraceDepth()))).append("\n");
        }

        //打印堆栈信息
        String body = parseBody(contents,config);
        sb.append(body);

        //打印
        List<HiLogPrinter> printerList = config.printers() != null ? Arrays.asList(config.printers()) : HILogManager.getInstance().getPrinters();
        for (HiLogPrinter printer : printerList) {
            printer.print(config, type, tag, sb.toString());
        }
    }

    private static String parseBody(@NonNull Object[] contents, @NonNull HiLogConfig config) {
        //类解析
        if (config.injectJsonParser() != null) {
            //只有一个数据，并且这个数据是String类型，直接返回？？为什么要这样弄？不序列化省？
            if (contents.length == 1 && contents[0] instanceof String) {
                return (String) contents[0];
            }
            return config.injectJsonParser().toJson(contents);
        }

        StringBuilder sb = new StringBuilder();
        for (Object o : contents) {
            sb.append(o.toString()).append(";");
        }
        //去掉最后一个分隔符
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }


}
