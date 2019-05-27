package com.wish.common.utils;

import android.util.Log;

import com.google.gson.Gson;

/**
 * Created by lzx on 2018/1/6.
 * Description :
 */

public class Ylog {

    public static String sTag = "wish app";

    public static void i(boolean b) {
        Log.i(sTag, buildLogString(String.valueOf(b)));
    }

    public static void i(String msg) {
        Log.i(sTag, buildLogString(msg));
    }

    public static void i(int num) {
        Log.i(sTag, buildLogString(String.valueOf(num)));
    }

    public static void i(long num) {
        Log.i(sTag, buildLogString(String.valueOf(num)));
    }

    public static void i(String tag, boolean b) {
        Log.i(tag, buildLogString(String.valueOf(b)));
    }

    public static void i(String tag, String msg) {
        Log.i(tag, buildLogString(msg));
    }

    public static void i(String tag, int num) {
        Log.i(tag, buildLogString(String.valueOf(num)));
    }

    public static void i(String tag, long num) {
        Log.i(tag, buildLogString(String.valueOf(num)));
    }


    public static void d(boolean b) {
        Log.d(sTag, buildLogString(String.valueOf(b)));
    }

    public static void d(String msg) {
        Log.d(sTag, buildLogString(msg));
    }

    public static void d(int num) {
        Log.d(sTag, buildLogString(String.valueOf(num)));
    }

    public static void d(long num) {
        Log.d(sTag, buildLogString(String.valueOf(num)));
    }

    public static void d(String tag, boolean b) {
        Log.d(tag, buildLogString(String.valueOf(b)));
    }

    public static void d(String tag, String msg) {
        Log.d(tag, buildLogString(msg));
    }

    public static void d(String tag, int num) {
        Log.d(tag, buildLogString(String.valueOf(num)));
    }

    public static void d(String tag, long num) {
        Log.d(tag, buildLogString(String.valueOf(num)));
    }



    public static void e(Throwable throwable, String text) {
        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("(")
                .append(caller.getFileName())
                .append(":")
                .append(caller.getLineNumber())
                .append(").")
                .append(caller.getMethodName())
                .append("():")
                .append(text)
                .append("->");
        Log.e(sTag, stringBuilder.toString(), throwable);
    }

    public static void e(Throwable throwable) {
        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("(")
                .append(caller.getFileName())
                .append(":")
                .append(caller.getLineNumber())
                .append(").")
                .append(caller.getMethodName())
                .append("():");
        Log.e(sTag, stringBuilder.toString(), throwable);
    }

    public static void e(String msg) {
        Log.e(sTag, buildLogString(msg));
    }

    public static void e(boolean b) {
        Log.e(sTag, buildLogString(String.valueOf(b)));
    }

    public static void e(int num) {
        Log.e(sTag, buildLogString(String.valueOf(num)));
    }

    public static void e(long num) {
        Log.e(sTag, buildLogString(String.valueOf(num)));
    }

    public static void e(String tag, Throwable throwable, String text) {
        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("(")
                .append(caller.getFileName())
                .append(":")
                .append(caller.getLineNumber())
                .append(").")
                .append(caller.getMethodName())
                .append("():")
                .append(text)
                .append("->");
        Log.e(tag, stringBuilder.toString(), throwable);
    }

    public static void e(String tag, Throwable throwable) {
        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("(")
                .append(caller.getFileName())
                .append(":")
                .append(caller.getLineNumber())
                .append(").")
                .append(caller.getMethodName())
                .append("():");
        Log.e(tag, stringBuilder.toString(), throwable);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, buildLogString(msg));
    }

    public static void e(String tag, boolean b) {
        Log.e(tag, buildLogString(String.valueOf(b)));
    }

    public static void e(String tag, int num) {
        Log.e(tag, buildLogString(String.valueOf(num)));
    }

    public static void e(String tag, long num) {
        Log.e(tag, buildLogString(String.valueOf(num)));
    }

    /**
     * Http的返回可能太长，加个长打印的方法
     * @param tag
     * @param msg
     */
    public static void longLog(String tag, String msg) {
        if (tag == null || tag.length() == 0
                || msg == null || msg.length() == 0)
            return;

        int segmentSize = 3 * 1024;
        long length = msg.length();
        if (length <= segmentSize ) {// 长度小于等于限制直接打印
            Log.i(tag, msg);
        }else {
            while (msg.length() > segmentSize ) {// 循环分段打印日志
                String logContent = msg.substring(0, segmentSize );
                msg = msg.replace(logContent, "");
                Log.i(tag, logContent);
            }
            Log.i(tag, msg);// 打印剩余日志
        }
    }


    private static String buildLogString(String str, Object... args) {
        if (args.length > 0) {
            str = String.format(str, args);
        }

        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("(")
                .append(caller.getFileName())
                .append(":")
                .append(caller.getLineNumber())
                .append(").")
                .append(caller.getMethodName())
                .append("():")
                .append(str);

        return stringBuilder.toString();
    }
}
