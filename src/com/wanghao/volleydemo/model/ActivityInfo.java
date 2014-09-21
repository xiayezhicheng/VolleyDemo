package com.wanghao.volleydemo.model;

public class ActivityInfo {

	private String title;
	private Class<?> name;
	
	public Class<?> getName() {
		return name;
	}
	
	//adapter中的显示数据将为title，不然是包名和类名
	@Override
	public String toString() {
		return title;
	}

	public ActivityInfo(String title, Class<?> name) {
		this.title = title;
		this.name = name;
	}
	
	
	
}
