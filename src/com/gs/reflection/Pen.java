package com.gs.reflection;

public class Pen implements Comparable<Pen> {

	private long id;
	private String name;

	public int price;

	public static int numOfColor;

	public Pen() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Pen(String name) {
		super();
		this.name = name;
	}

	public Pen(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int checkPrice() {
		return 10 * price;
	}

	@Override
	public String toString() {
		return "Pen [id=" + id + ", name=" + name + ", numOfColor=" + numOfColor + ", price=" + price + "]";
	}

	@Override
	public int compareTo(Pen o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
