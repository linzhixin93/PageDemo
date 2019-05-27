package com.wish.common.msg;

import com.wish.common.utils.Ylog;

import org.greenrobot.eventbus.EventBus;


public class MessageUtils {

    private static final String TAG = "MessageUtils";

    public static void register(MessageObserver observer) {
        EventBus.getDefault().register(observer);
    }

    public static void unregister(MessageObserver observer) {
        EventBus.getDefault().unregister(observer);
    }

    static void send(MessageEvent messageEvent) {
        Ylog.i(TAG, messageEvent.toString());
        EventBus.getDefault().post(messageEvent);
    }

    static void sendSticky(MessageEvent messageEvent) {
        Ylog.i(TAG, messageEvent.toString());
        EventBus.getDefault().postSticky(messageEvent);
    }

}
