package com.ruslan.magaz;

import java.util.ArrayList;
import java.util.List;

public class Cart {
	List<ItemTuple> itemList = new ArrayList<ItemTuple>();
	
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
	
	public boolean removeFromItemList(Item item){
		for (ItemTuple tuple:itemList){
			if (item.equals(tuple.item)){
				itemList.remove(tuple);
				return true;
			}
		}
		return false;
	}
}
