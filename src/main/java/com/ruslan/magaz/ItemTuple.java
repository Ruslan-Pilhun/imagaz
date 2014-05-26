package com.ruslan.magaz;

public class ItemTuple{
	public final Item item;
	public final int count;
	public ItemTuple(Item item, int count) {
		this.item = item;
		this.count = count;
	}
	public String toString(){
		return "("+item+", "+count+")";
	}
}
