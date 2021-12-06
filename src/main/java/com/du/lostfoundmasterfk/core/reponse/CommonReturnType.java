package com.du.lostfoundmasterfk.core.reponse;

import lombok.Data;

@Data
public class CommonReturnType {
    private String status;

    private Object data;

    private String message;

    public static CommonReturnType success(Object result, String message){
        return CommonReturnType.create(result,"success",message);
    }

    public static CommonReturnType success(Object result) {
        return CommonReturnType.create(result,"success","");
    }

    /**
     * 失败的返回结果
     * @param result
     * @return
     */
    public static CommonReturnType fail(Object result,String message) {
        return CommonReturnType.create(result,"fail", message);
    }
    /**
     * 没有描述信息的失败返回结果
     * @param result
     * @return
     */
    public static CommonReturnType fail(Object result) {
        return CommonReturnType.create(result,"fail", "");
    }

    /**
     * 兼容之前的代码
     * @param result
     * @return
     */
    public static CommonReturnType create(Object result,String message) {
        return CommonReturnType.create(result,"success", message);
    }

    public static CommonReturnType create(Object result, String status, String message){
        CommonReturnType type = new CommonReturnType();
        type.setData(result);
        type.setMessage(message);
        type.setStatus(status);

        return type;
    }


}
