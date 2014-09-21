package com.wanghao.volleydemo.model;

import java.util.HashMap;

import com.google.gson.Gson;

public class Provinces extends BaseType{
	private static final HashMap<String, Provinces> CACHE = new HashMap<String, Provinces>();
	private String proId;
	private String proName;
    private static void addToCache(Provinces provinces) {
        CACHE.put(provinces.getProId(), provinces);
    }

    private static Provinces getFromCache(String id) {
        return CACHE.get(id);
    }

    public static Provinces fromJson(String json){
    	return new Gson().fromJson(json, Provinces.class);
    }
	public String getProId() {
		return proId;
	}
	public void setProId(String proId) {
		this.proId = proId;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
}
