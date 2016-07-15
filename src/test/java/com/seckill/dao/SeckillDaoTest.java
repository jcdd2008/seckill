package com.seckill.dao;

import com.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 配置spring和junit整合，junit启动时加载spring容器
 * Created by niemengquan on 2016/7/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void reduceNumber() throws Exception {
        long id = 1000l;
       int result = seckillDao.reduceNumber(id,new Date());
        System.out.println(result);
    }

    @Test
    public void querySeckillList() throws Exception {
        List<Seckill> seckills = seckillDao.querySeckillList(0, 100);
        for (Seckill seckill:seckills){
            System.out.println(seckill);
        }
    }

    @Test
    public void queryByid() throws Exception {
        long id = 1000l;
        Seckill seckill = seckillDao.queryByid(id);
        System.out.println(seckill);
    }

}