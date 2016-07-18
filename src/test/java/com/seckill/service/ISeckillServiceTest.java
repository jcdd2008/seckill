package com.seckill.service;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by niemengquan on 2016/7/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
/**
 * 测试应该不改变实际的数据，这里配置使用的事务管理，设置默认执行回滚操作
 */
@TransactionConfiguration(transactionManager = "txManager",defaultRollback = true)
@Transactional
public class ISeckillServiceTest {
    private final Logger LOG= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ISeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> seckillList = this.seckillService.getSeckillList();
        for(Seckill seckill:seckillList){
            System.out.println(seckill);
        }
    }

    @Test
    public void getById() throws Exception {
        long id=1001;
        Seckill seckill = this.seckillService.getById(id);
        System.out.println(seckill);;
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long id=1000;
        Exposer exposer = this.seckillService.exportSeckillUrl(id);
        System.out.println(exposer);
    }

    @Test
    public void execuSeckill() throws Exception {
        long id=1000;
        String md5="e7c62550067a604533f76ed8c99d3713";
        long userPhone=18792218343l;
        try {
            SeckillExecution seckillExecution = this.seckillService.execuSeckill(id, userPhone, md5);
            System.out.println(seckillExecution);
        }catch (RepeatKillException e){
            LOG.error(e.getMessage());
        }catch (SeckillCloseException e){
            LOG.error(e.getMessage());
        }

    }

    /**
     * 暴露秒杀地址和执行秒杀整合测试
     */
    @Test
    public void uniteExecuteSeckill(){
        long id=1000;
        Exposer exposer = this.seckillService.exportSeckillUrl(id);
        System.out.println(exposer);
        if (exposer.isExposed()){
            long userPhone=18592218343l;
            try {
                SeckillExecution seckillExecution = this.seckillService.execuSeckill(id, userPhone, exposer.getMd5());
                System.out.println(seckillExecution);
            }catch (RepeatKillException e){
                LOG.error(e.getMessage());
            }catch (SeckillCloseException e){
                LOG.error(e.getMessage());
            }
        }else {
            System.out.println(exposer);
        }
    }

}