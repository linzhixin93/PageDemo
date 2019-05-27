package com.wish.common.msg;

import android.text.TextUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MessageObserver {

    private IMessageHandler messageHandler;

    public MessageObserver(IMessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    final public void onMsg(MessageEvent event) {
        try {
            if (event.testFlag(MessageEvent.FLAG_GLOBAL)) {
                messageHandler.onGlobalMsg(event);
            }
            if (event.testFlag(MessageEvent.FLAG_VISIBLE) && messageHandler.isVisible()) {
                messageHandler.onVisibleMsg(event);
            }
            if (event.testFlag(MessageEvent.FLAG_JUST_TAG) && TextUtils.equals(messageHandler.getTag(), event.getTag())) {
                messageHandler.onUniqueMsg(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
