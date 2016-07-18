package com.seckill.service.impl;

import com.seckill.dao.SeckillDao;
import com.seckill.dao.SuccessKilledDao;
import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.SuccessKilled;
import com.seckill.enums.SeckillStatEnum;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatKillException;
import com.seckill.service.ISeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by niemengquan on 2016/7/18.
 */
@Service
public class SeckillServiceImpl implements ISeckillService {
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;
    //MD5盐值字符串，用户混淆MD5
    private final String slat="salfdjouiwerlkjfo23480923ql;@!PO!@";

    public List<Seckill> getSeckillList() {
        return this.seckillDao.querySeckillList(0,10);
    }

    public Seckill getById(long seckillId) {
        return this.seckillDao.queryByid(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = this.seckillDao.queryByid(seckillId);
        if(seckill==null){
            //返回错误，不存在该秒杀商品id
            return new Exposer(false,seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime=new Date();
        if (nowTime.getTime()<startTime.getTime()||nowTime.getTime()>endTime.getTime()){
            //当前时间小于秒杀开启时间或者当前时间大于秒杀结束时间
            return  new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }
        //暴露秒杀地址
        String md5 = getMD5(seckillId);
        return new Exposer(true,md5,seckillId);
    }
    private String getMD5(long seckillId){
        String base=seckillId+"/"+slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    /**
     * 使用注解控制事务方法的有点
     *  1：开发团队达成一致的约定，明确标注事务方法的变成风格
     *  2.保证事务方法的执行时间尽可能的短，不要穿插其他网络请求，RPC/HTTP请求或者玻璃到书屋方法外
     *  3：不是所有的方法都需要事务，如只有一条修改操作，只读操作不需要事务控制
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    @Transactional
    public SeckillExecution execuSeckill(long seckillId, long userPhone, String md5) {
        //因为spring只对运行时异常进行事务的回滚这里需要我们自定义一些业务需要的运行时异常
        if(md5==null ||!md5.equals(getMD5(seckillId))){
            throw new SeckillException("seckill data rewrite!");
        }
        //执行秒杀逻辑：减库存+记录购买行为
        //首先记录购买行为：判断是否有重复秒杀的情况
        int insertCount = this.successKilledDao.insertSuccessKilled(seckillId, userPhone);
        if(insertCount==0){
            //重复秒杀
            throw new RepeatKillException("seckill repteaded!");
        }
        //减库存
        Date nowTime=new Date();
        int updateCount = this.seckillDao.reduceNumber(seckillId, nowTime);
        if(updateCount<=0){
            //秒杀已经结束，或者已经卖完了，没有更新到记录
            throw new SeckillCloseException("seckill is closed!");
        }
        //秒杀成功
        SuccessKilled successKilled = this.successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
        return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS,successKilled);
    }
}
