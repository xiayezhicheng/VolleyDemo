package com.wanghao.volleydemo.model;

import com.google.gson.Gson;


public class BaseType {

	public String toJson(){
		return new Gson().toJson(this);
	}
}
