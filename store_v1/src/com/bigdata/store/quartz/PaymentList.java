package com.bigdata.store.quartz;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bigdata.store.dao.OrderDao;
import com.bigdata.store.dao.impl.OrderDaoImpl;

/**
 * @Author: duYang
 * @Date: 2021/7/22 9:17
 * @Version: 1.0
 */
public class PaymentList {

    private static List<Payment> paymentList = null;

    public PaymentList(List<Payment> paymentList) {
        PaymentList.paymentList = paymentList;
    }

    public static void addPayment(Payment payment) {
        paymentList.add(payment);
    }
 
    public static synchronized void flushPayment() throws SQLException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, -30);
        String beforeTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
        for (Payment payment : paymentList) {
        	boolean query = AliPayProvider.query(payment.getOid());
            if (query) {
            	OrderDao orderdao=new OrderDaoImpl();
            	orderdao.updateStatu(payment.getOid());
            	paymentList.remove(payment);
			}
            String date = payment.getOrdertime();
            if (beforeTime.compareTo(date) > 0) {
                paymentList.remove(payment);
            }
        }
    }
}
