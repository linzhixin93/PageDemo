package com.wish.net.error;

import java.util.HashMap;
import java.util.Map;

/**
 * author：LinZhiXin
 * date：2019/3/14
 * description：网络异常类
 **/
public class NetException extends Exception {

    private static Map<Integer, String> ERROR_MAP;

    static {
        ERROR_MAP = new HashMap<>();
        ERROR_MAP.put(ErrorCode.NET_ERROR, "抱歉，网络开小差了");
        ERROR_MAP.put(ErrorCode.DATA_ERROR, "抱歉，数据出错了");
    }


    private int code;

    public NetException(int code) {
        super(getErrorMsg(code));
        this.code = code;
    }

    public NetException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    private static String getErrorMsg(int code) {
        if (!ERROR_MAP.containsKey(code)) {
            return "Unknown Exception";
        }
        return ERROR_MAP.get(code);
    }

    public static NetException netError() {
        return new NetException(ErrorCode.NET_ERROR);
    }

    public static NetException dataError() {
        return new NetException(ErrorCode.DATA_ERROR);
    }


}
