package com.example.mylibrary.log;

public interface HiLogFormatter<T> {
    String format(T data);
}
