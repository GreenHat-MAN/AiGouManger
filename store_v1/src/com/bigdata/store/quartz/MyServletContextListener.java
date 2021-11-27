package com.bigdata.store.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import com.bigdata.store.dao.OrderDao;
import com.bigdata.store.dao.impl.OrderDaoImpl;
import com.bigdata.store.domain.Order;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @Author: duYang
 * @Date: 2021/7/21 22:55
 * @Version: 1.0
 */
public class MyServletContextListener implements ServletContextListener {
		
	
    public static Scheduler scheduler = null;

    public MyServletContextListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
        	OrderDao orderDao=new OrderDaoImpl();
        	List<Payment> list = orderDao.selectTimeOrder();
        	PaymentList paymentList=new PaymentList(list);
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobDetail jb = JobBuilder.newJob(PrintWordsJob.class)
                    .withIdentity("ramJob", "ramGroup")
                    .build();

            Trigger t = TriggerBuilder.newTrigger()
                    .withIdentity("ramTrigger", "ramTriggerGroup")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
                    .startNow()
                    .build();
            scheduler.scheduleJob(jb, t);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
        	if (scheduler!=null) {
        		scheduler.shutdown();
			}
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
