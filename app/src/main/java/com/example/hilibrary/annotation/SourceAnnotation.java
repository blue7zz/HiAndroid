package com.example.hilibrary.annotation;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class SourceAnnotation {
    public static final int STATUS_OPEN = 1;
    public static final int STATUS_CLOSE = 2;
    public static int mStatus = STATUS_OPEN;

    public static int getStatus() {
        return mStatus;
    }

    public static void setStatus(@Status int status) {
        SourceAnnotation.mStatus = status;
    }

    public static String getStatusDesc() {
        return mStatus == STATUS_OPEN ? "打开状态" : "关闭状态";
    }

    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.PARAMETER)
    @IntDef({STATUS_OPEN, STATUS_CLOSE})
    public @interface Status {

    }
}
