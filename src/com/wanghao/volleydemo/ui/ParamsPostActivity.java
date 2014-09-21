package com.wanghao.volleydemo.ui;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.wanghao.volleydemo.R;
import com.wanghao.volleydemo.data.RequestManager;
import com.wanghao.volleydemo.vendor.VolleyApi;

//volley不适合进行图片等大数据量的上传
public class ParamsPostActivity extends BaseActivity{

	private EditText email,password,birthday;
	private Button btn_register;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paramspost);
		
		email = (EditText)findViewById(R.id.email);
		password = (EditText)findViewById(R.id.password);
		birthday = (EditText)findViewById(R.id.birthday);
		btn_register = (Button)findViewById(R.id.btn_register);
		
		btn_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				StringRequest request = new StringRequest(Request.Method.POST, VolleyApi.postparams_url,
						new Response.Listener<String>() {

							@Override
							public void onResponse(String response) {
								//返回的response是类似于[2424]这种，需要处理
								String resutlt = response.substring(response.indexOf("[")+1, response.indexOf("]"));
								switch(Integer.valueOf(resutlt)){
									case -2:
										Toast.makeText(getApplicationContext(), "注册失败, 用户名为空", Toast.LENGTH_SHORT).show();
										break;
									case -1:
										Toast.makeText(getApplicationContext(), "注册失败, 用户名已存在", Toast.LENGTH_SHORT).show();
										break;
									case 0:
										Toast.makeText(getApplicationContext(), "注册失败, 出生日期格式不对", Toast.LENGTH_SHORT).show();
										break;
									default:
										Toast.makeText(getApplicationContext(), String.format("注册成功, 用户ID为%s", response), Toast.LENGTH_SHORT).show();
										break;
								}
							}
						}, 
						new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError error) {
								VolleyLog.e("Error", error.getMessage());
							}
						}){

							@Override
							protected Map<String, String> getParams()
									throws AuthFailureError {
								Map<String,String> stringParams = new HashMap<String, String>();
								stringParams.put("flag", "phone_C2t0I0@9_register");
								stringParams.put("userName", email.getText().toString());
								stringParams.put("passWord", password.getText().toString());
								stringParams.put("birthDay", birthday.getText().toString());
								return stringParams;
							}
					};
				executeRequest(request);
			 }
		});
	}

}
    