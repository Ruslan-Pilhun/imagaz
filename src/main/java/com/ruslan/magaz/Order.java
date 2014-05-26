package com.ruslan.magaz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Order {

	int id;
	User user;
	Cart cart;
	List<OrderStatus> statusList = new ArrayList<OrderStatus>();
	Date orderDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public List<OrderStatus> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<OrderStatus> statusList) {
		this.statusList = statusList;
	}
	public void addToStatusList(OrderStatus status)	{
		statusList.add(status);
	}
	
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	
	
}
