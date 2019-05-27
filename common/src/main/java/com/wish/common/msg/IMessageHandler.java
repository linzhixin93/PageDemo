package com.wish.common.msg;

/**
 * Created At 2019/5/26 by ZhiXin.Lin
 * Description :
 */
public interface IMessageHandler {

    void onGlobalMsg(MessageEvent event);

    void onVisibleMsg(MessageEvent event);

    void onUniqueMsg(MessageEvent event);

    boolean isVisible();

    String getTag();

}
