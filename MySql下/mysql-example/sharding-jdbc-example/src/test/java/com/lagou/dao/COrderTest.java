package com.lagou.dao;

import com.lagou.RunApplication;
import com.lagou.entity.COrder;
import com.lagou.repository.COrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 23:19 2020/6/9 0009
 */
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RunApplication.class)
public class COrderTest {

    @Resource
    private COrderRepository cOrderRepository;

    @Test
    public void addTest(){
        COrder cOrder = new COrder();

        cOrder.setDel(false);
        cOrder.setPublishUserId(1);
        cOrder.setPositionId(1);
        cOrder.setResumeType(1);
        cOrder.setStatus("WAIT");
        cOrder.setCreateTime(new Date());
        cOrder.setUpdateTime(new Date());
        for (int i = 1 ; i < 10; i++) {
            cOrder.setUserId(i);
            cOrder.setCompanyId(i);
            cOrderRepository.save(cOrder);
        }
    }

    @Test
    @Repeat(5)
    public void testShardingCOrder(){
        Random random = new Random();
        int useryId = random.nextInt(10);
        COrder order = new COrder();
        order.setDel(false);
        order.setCompanyId(1);
        order.setPositionId(2);
        order.setUserId(useryId);
        order.setPublishUserId(3);
        order.setResumeType(1);
        order.setStatus("AUTO");
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        cOrderRepository.save(order);
    }

    @org.junit.Test
    public void testFind(){
        List<COrder> list = cOrderRepository.findAll();
        list.forEach(order->{
            System.out.println(order.toString());
        });
    }
}
