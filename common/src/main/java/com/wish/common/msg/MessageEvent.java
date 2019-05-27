package com.wish.common.msg;


public class MessageEvent {

    public static final int FLAG_GLOBAL = 0x001;//全局接收

    public static final int FLAG_VISIBLE = 0x010;//仅可见的页面接收

    public static final int FLAG_JUST_TAG = 0x100;//指定某个消息接受者接收

    private int code;

    private Object data;

    private int flag = FLAG_GLOBAL;

    private String tag;


    public static MessageEvent newMsg() {
        return new MessageEvent();
    }

    public String getTag() {
        return tag;
    }

    public MessageEvent setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public int getCode() {
        return code;
    }

    public MessageEvent setCode(int code) {
        this.code = code;
        return this;
    }

    public Object getData() {
        return data;
    }

    public MessageEvent setData(Object data) {
        this.data = data;
        return this;
    }

    public int getFlag() {
        return flag;
    }

    public MessageEvent setFlag(int flag) {
        this.flag = flag;
        return this;
    }

    boolean testFlag(int test) {
        return (flag & test) > 0;
    }

    public void post() {
        MessageUtils.send(this);
    }

    public void postSticky() {
        MessageUtils.sendSticky(this);
    }

    @Override
    public String toString() {
        return "MessageEvent{" +
                "code=" + code +
                ", data=" + data +
                ", flag=" + flag +
                ", tag='" + tag + '\'' +
                '}';
    }
}
