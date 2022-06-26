package com.example.mylibrary.log;

public abstract class HiLogConfig {
    static int MAX_LEN = 512;

    static HiThreadFormatter HI_THREAD_FORMATTER = new HiThreadFormatter();
    static HiStackTraceFormatter HI_STACK_TRACE_FORMATTER = new HiStackTraceFormatter();

    public String getGlobalTag() {
        return "HiLog";
    }

    public boolean enable() {
        return true;
    }

    public int stackTraceDepth() {
        return 5;
    }

    public HiLogPrinter[] printers() {
        return null;
    }

    public boolean includeThread() {
        return false;
    }

    public JsonParser injectJsonParser(){
        return null;
    };

    public interface JsonParser {
        String toJson(Object src);
    }
}
