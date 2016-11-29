package com.example.comic_book;

public class Chapter {
    private String name;
    private String id;
	public Chapter() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Chapter(String name, String id) {
		super();
		this.name = name;
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Chapter [name=" + name + ", id=" + id + "]";
	}
    
}
