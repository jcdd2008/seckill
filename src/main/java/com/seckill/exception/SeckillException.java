package com.seckill.exception;

import com.seckill.entity.SuccessKilled;
import com.seckill.enums.SeckillStatEnum;

/**
 * 秒杀相关业务异常
 * Created by niemengquan on 2016/7/18.
 */
public class SeckillException extends RuntimeException{
    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeckillException(String message) {
        super(message);
    }


}

