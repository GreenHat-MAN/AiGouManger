package com.bigdata.store.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Author: duYang
 * @Date: 2021/7/21 16:28
 * @Version: 1.0
 */
public class PrintWordsJob implements Job {
	
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
			PaymentList.flushPayment();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
