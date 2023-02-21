package com.xuecheng.base.exception;

/**
 * @author liuqi
 * @version 1.0
 * @description 统一处理异常的类
 * @date 2023-02-20 22:17
 */
public class XueChengPlusException extends RuntimeException{

    //接收异常信息
    private String errMessage;


    public XueChengPlusException() {
        super();
    }

    public XueChengPlusException(String message) {
        super(message);
        this.errMessage = message;
    }

    public String getErrMessage(){
        return errMessage;
    }

    //抛出通用异常信息
    public static void cast(CommonError commonError){
        throw new XueChengPlusException(commonError.getErrMessage());
    }
    //抛出异常信息
    public static void cast(String errMessage){
        throw new XueChengPlusException(errMessage);
    }


}

