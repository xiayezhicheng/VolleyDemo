package com.wanghao.volleydemo.ui;

import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wanghao.volleydemo.R;
import com.wanghao.volleydemo.model.ActivityInfo;

public class MainActivity extends BaseActivity {

	private ListView activity_list;
	private List<ActivityInfo> mData = Arrays.asList(
				new ActivityInfo("ImageRequest", ImageRequestActivity.class),
				new ActivityInfo("JsonRequest", JsonRequestActivity.class),
				new ActivityInfo("ParamsPostRequest", ParamsPostActivity.class)
			);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		activity_list = (ListView)findViewById(R.id.list_activity);
		activity_list.setAdapter(new ArrayAdapter<ActivityInfo>(this, 
				android.R.layout.simple_list_item_1, mData));
		activity_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(activity,mData.get(position).getName());
				startActivity(intent);
			}
			
		});
	}


}
