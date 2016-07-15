package com.seckill.dao;

import com.seckill.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by niemengquan on 2016/7/15.
 */
public interface SeckillDao {
    /**
     * 减库存操作
     * @param seckillId
     * @param killTime
     * @return
     */
   int  reduceNumber(@Param("seckillId") Long seckillId, @Param("killTime") Date killTime);

    /**
     * 查询所有的秒杀商品列表
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> querySeckillList(@Param("offset") int offset,@Param("limit") int limit);

    /**
     * 根据id查询秒杀对象
     * @param seckillId
     * @return
     */
    Seckill queryByid(Long seckillId);
}
