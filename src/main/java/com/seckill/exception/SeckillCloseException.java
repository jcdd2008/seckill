package com.seckill.exception;

/**
 * 重复秒杀异常（运行期异常）
 * Created by niemengquan on 2016/7/18.
 */
public class SeckillCloseException extends SeckillException {
    public SeckillCloseException(String message) {
        super(message);
    }
    public SeckillCloseException(String message, Throwable cause){
        super(message,cause);
    }
}
