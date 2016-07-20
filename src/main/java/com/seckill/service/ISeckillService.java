package com.seckill.service;

/**
 * Created by niemengquan on 2016/7/18.
 */

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;

import java.util.List;

/**
 * 业务接口的设置原则：站在“使用者”角度设计接口
 * 三个方面：方法定义力度，参数，返回类型(return 类型/异常)
 */
public interface ISeckillService {
    /**
     * 查询所有秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启时输出秒杀地址，
     * 否则输出系统时间和秒杀开始时间
     * @param seckillId
     * @return
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 开始秒杀
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution execuSeckill(long seckillId, long userPhone, String md5) throws RepeatKillException,SeckillCloseException,SeckillException;
}
