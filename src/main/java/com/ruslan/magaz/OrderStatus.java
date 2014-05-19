package com.ruslan.magaz;

import java.util.Date;

public class OrderStatus {

	Date date;
	String place;
	String status;
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
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
	public OrderStatus(Date date, String place, String status) {
		this.date = date;
		this.status = status;
		this.place = place;
	}
	
}
