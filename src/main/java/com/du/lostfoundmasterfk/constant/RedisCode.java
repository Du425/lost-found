package com.du.lostfoundmasterfk.constant;

public enum RedisCode {
    IMAGE_CODE("REDIS_KEY_IMAGE_CODE"),
    MAIL_CODE("REDIS_KEY_MAIL_CODE");

    private String msg;

    RedisCode(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
