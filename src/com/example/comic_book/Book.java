package com.example.comic_book;

public class Book {
     private String name;
     private String area;
     private String des;
     private String lastUpdate;
     private String coverImg;
	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Book(String name, String area, String des, String lastUpdate,
			String coverImg) {
		super();
		this.name = name;
		this.area = area;
		this.des = des;
		this.lastUpdate = lastUpdate;
		this.coverImg = coverImg;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getCoverImg() {
		return coverImg;
	}
	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}
	@Override
	public String toString() {
		return "Book [name=" + name + ", area=" + area + ", des=" + des
				+ ", lastUpdate=" + lastUpdate + ", coverImg=" + coverImg + "]";
	}
      
} 
