package com.ruslan.magaz;

import java.util.Date;

public class OrderStatus {

	Date date;
	String status;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public OrderStatus(Date date, String status) {
		this.date = date;
		this.status = status;
	}
	
}
