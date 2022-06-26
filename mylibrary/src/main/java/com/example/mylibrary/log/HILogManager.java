package com.example.mylibrary.log;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * 初始化管理配置信息
 */
public class HILogManager {
    private HiLogConfig config;
    private static HILogManager instance;
    private List<HiLogPrinter> printers = new ArrayList<>();


    private HILogManager(HiLogConfig config) {
        this.config = config;
    }

    public static HILogManager getInstance() {
        return instance;
    }

    public static void init(@NonNull HiLogConfig config) {
        instance = new HILogManager(config);
    }

    public HiLogConfig getConfig() {
        return config;
    }

    public List<HiLogPrinter> getPrinters() {
        return printers;
    }

    public void addPrinter(HiLogPrinter printer) {
        printers.add(printer);
    }

    public void removePrinter(HiLogPrinter printer) {
        printers.remove(printer);
    }

}
