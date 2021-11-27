package com.bigdata.store.quartz;

/**
 * @Author: duYang
 * @Date: 2021/7/22 9:14
 * @Version: 1.0
 */
public class Payment {
    private String oid;
    private String ordertime;

    public Payment() {
    }

    public Payment(String oid, String ordertime) {
        this.oid = oid;
        this.ordertime = ordertime;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    

	public String getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}

	@Override
	public String toString() {
		return "Payment [oid=" + oid + ", ordertime=" + ordertime + "]";
	}
    
   
}
