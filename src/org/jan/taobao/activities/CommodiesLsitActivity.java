package org.jan.taobao.activities;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class CommodiesLsitActivity extends Activity {

	ListView itemList;
	SimpleAdapter myAdaptor;
	private Button moreBtn;
	private ArrayList<HashMap<String, Object>> mList;
	private View moreView;
	private Handler mHandler;
	private int maxNum = 5;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.items_list_layout);
		itemList = (ListView) findViewById(R.id.item_list_2);
		moreView = getLayoutInflater().inflate(R.layout.moredata, null);
		moreBtn = (Button) moreView.findViewById(R.id.more);
		mList = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("item_image", R.drawable.ic_launcher);
			map.put("item_name", i);
			map.put("item_price", i * 100);
			mList.add(map);
		}
		myAdaptor = new SimpleAdapter(CommodiesLsitActivity.this, mList,
				R.layout.simple_item, new String[] { "item_image", "item_name",
						"item_price" }, new int[] { R.id.item_image,
						R.id.item_name, R.id.item_price });
		itemList.addFooterView(moreView);
		itemList.setAdapter(myAdaptor);
		itemList.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
