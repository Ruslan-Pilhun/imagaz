package com.ruslan.magaz;

public class Item {
    private int id;
    private String name;
    private String category;
    private String description;
    private double price;
    private int count;
    
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    @Override
    public String toString(){
        return " id: "+id+" name: "+name+" description: "+description+" category: "+category+" price: "+price+" count: "+count;
    }
    
}

class ItemTuple{
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
