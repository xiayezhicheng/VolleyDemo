package com.wanghao.volleydemo.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.wanghao.volleydemo.R;
import com.wanghao.volleydemo.data.GsonRequest;
import com.wanghao.volleydemo.data.RequestManager;
import com.wanghao.volleydemo.model.Provinces;
import com.wanghao.volleydemo.model.Weather;
import com.wanghao.volleydemo.vendor.VolleyApi;

public class JsonRequestActivity extends BaseActivity implements OnClickListener{


	private TextView txt_objectrequest,txt_gsonrequest,txt_arrayrequest;
	private PopupWindow popupWindow;  
	private ArrayAdapter<String> adapter;
	private List<String> data = new ArrayList<String>();
	private View popupView;
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gsonrequest);
		
		txt_objectrequest = (TextView)findViewById(R.id.txt_objectrequest);
		txt_gsonrequest = (TextView)findViewById(R.id.txt_gsonrequest);
		txt_arrayrequest = (TextView)findViewById(R.id.txt_arrayrequest);
		
		txt_objectrequest.setOnClickListener(this);
		txt_gsonrequest.setOnClickListener(this);
		txt_arrayrequest.setOnClickListener(this);
		
		initPopupWindow();
	}

	private void initPopupWindow() {
		popupView = LayoutInflater.from(this).inflate(R.layout.popupwindow, null);
		adapter = new ArrayAdapter<String>(this,R.layout.popupwindow_item,R.id.popupwindow_list_item, data);
		
		listView = (ListView)popupView.findViewById(R.id.popupwindow_list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				popupWindow.dismiss();	
				txt_arrayrequest.setText(data.get(position).toString());
			}
		});
		
		//自适配长、框设置   
        popupWindow = new PopupWindow(popupView,600,LayoutParams.WRAP_CONTENT); 
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);  
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.txt_objectrequest:
				objectJsonRequest();
				break;
			case R.id.txt_gsonrequest:
				objectGsonRequest();
				break;
			case R.id.txt_arrayrequest:
				arrayJsonRequest();
				break;
		}
	}

	//ObjectJson的解析
	//{"weatherinfo":{"SD":"72%","isRadar":"1","time":"19:15","WSE":"1","WS":"1级","WD":"东南风","njd":"暂无实况",
	//"qy":"1011","Radar":"JC_RADAR_AZ9010_JB","temp":"22","cityid":"101010100","city":"北京"}} 
	private void objectJsonRequest() {
		JsonObjectRequest request = new JsonObjectRequest(VolleyApi.weather_url, null, 
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.print(response.toString());
//						Gson gson = new Gson();
//						Weather weather = gson.fromJson(response.toString(), Weather.class);
//						txt_objectrequest.setText(weather.getWeatherinfo().getCity()
//								+"-"+weather.getWeatherinfo().getTemp());
						try {
							JSONObject jo = response.getJSONObject("weatherinfo");
							txt_objectrequest.setText(jo.get("city")
									+"-"+jo.get("temp"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, 
				//可以自己写出错时执行的操作，也可以直接用baseactivity定义好的
				new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						 VolleyLog.e("Error: ", error.getMessage());
					}
				});
		executeRequest(request);
	}
	
	//ObjectJson的解析，用GsonRequest
	private void objectGsonRequest() {
		GsonRequest<Weather> request = new GsonRequest<Weather>(VolleyApi.weather_url, Weather.class,
				new Response.Listener<Weather>() {

					@Override
					public void onResponse(Weather response) {
						txt_gsonrequest.setText(response.getWeatherinfo().getCity()
								+"-"+response.getWeatherinfo().getTemp());
					}
				},errorListener());
		executeRequest(request);
	}

	//ArrayJson的解析
	//[{"proId":20,"proName":"安徽"},{"proId":1,"proName":"北京"},{"proId":4,"proName":"重庆"},
	//{"proId":26,"proName":"福建"},{"proId":12,"proName":"甘肃"},{"proId":24,"proName":"广东"},
	//{"proId":25,"proName":"广西"},{"proId":0,"proName":"海外省份"},{"proId":14,"proName":"河北"},
	//{"proId":15,"proName":"河南"},{"proId":5,"proName":"黑龙江"},{"proId":18,"proName":"湖北"},
	//{"proId":19,"proName":"湖南"},{"proId":6,"proName":"吉林"},{"proId":21,"proName":"江苏"},
	//{"proId":23,"proName":"江西"},{"proId":7,"proName":"辽宁"},{"proId":8,"proName":"内蒙古"},
	//{"proId":9,"proName":"宁夏"},{"proId":11,"proName":"青海"},{"proId":16,"proName":"山东"},
	//{"proId":17,"proName":"山西"},{"proId":13,"proName":"陕西"},{"proId":2,"proName":"上海"},
	//{"proId":27,"proName":"四川"},{"proId":3,"proName":"天津"},{"proId":10,"proName":"新疆"},
	//{"proId":28,"proName":"云南"},{"proId":22,"proName":"浙江"}]
	private void arrayJsonRequest() {
		StringRequest request = new StringRequest(Request.Method.POST,VolleyApi.province_url, 
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Gson gson = new Gson();
						List<Provinces> provinces = gson.fromJson(response,new TypeToken<List<Provinces>>(){}.getType());
//						JsonParser parser = new JsonParser();
//						JsonArray jarray = parser.parse(response).getAsJsonArray();
//						for(JsonElement obj : jarray){
//							Provinces provinces = gson.fromJson(obj, Provinces.class);
//							data.add(provinces.getProName());
//						}
						for (Iterator<Provinces> pIterator = provinces.iterator(); pIterator.hasNext();) {
							data.add(pIterator.next().getProName());
						}
						
						adapter.notifyDataSetChanged();
						popupWindow.showAsDropDown(txt_arrayrequest); 
					}
			
				},errorListener()){

					@Override
					public Map<String, String> getParams()
							throws AuthFailureError {
						Map<String,String> provinceParams = new HashMap<String, String>();
						provinceParams.put("flag", "phone_C2t0I0@9_getProvince");
						provinceParams.put("gjid", "1");
						provinceParams.put("kmid", "828635");
						return provinceParams;
					}
			
		};
		executeRequest(request);
	}


	

}
