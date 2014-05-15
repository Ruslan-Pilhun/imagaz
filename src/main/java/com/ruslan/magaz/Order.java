package com.ruslan.magaz;

import java.util.Date;
import java.util.List;


public class Order {

	int id;
	User user;
	List<ItemTuple> itemList;
	OrderStatus status;
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
	public List<ItemTuple> getItemList() {
		return itemList;
	}
	public void setItemList(List<ItemTuple> itemList) {
		this.itemList = itemList;
	}
	public void addToItemList(Item item) {
		addToItemList(item, 1);
	}
	
	public void addToItemList(Item item, int count) {
		boolean added = false;
		for (ItemTuple tuple:itemList){
			if (item.equals(tuple.item)){
				itemList.remove(tuple);
				ItemTuple newTuple = new ItemTuple(item, tuple.count+count);
				itemList.add(newTuple);
				added = true;
				break;
			}
		}
		if (!added){
			ItemTuple newTuple = new ItemTuple(item, 1);
			itemList.add(newTuple);
		}
	}
	
	public boolean RemoveFromItemList(Item item){
		for (ItemTuple tuple:itemList){
			if (item.equals(tuple.item)){
				itemList.remove(tuple);
				return true;
			}
		}
		return false;
	}
	
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	
	
}
