package com.wanghao.volleydemo.model;

public class ActivityInfo {

	private String title;
	private Class<?> name;
	
	public Class<?> getName() {
		return name;
	}
	
	//adapter�е���ʾ���ݽ�Ϊtitle����Ȼ�ǰ���������
	@Override
	public String toString() {
		return title;
	}

	public ActivityInfo(String title, Class<?> name) {
		this.title = title;
		this.name = name;
	}
	
	
	
}
